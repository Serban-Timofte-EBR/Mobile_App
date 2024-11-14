package eu.ase.ro.petmanager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import eu.ase.ro.petmanager.databinding.ActivityMainBinding;
import eu.ase.ro.petmanager.fragments.AllPetsFragment;
import eu.ase.ro.petmanager.model.Pet;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    private List<Pet> pets = new ArrayList<>();

    private ActivityResultLauncher<Intent> launcher;
    private Intent intent;

    private Fragment currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        configNavigation();

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(getItemSelectedEvent());

        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), getAddPetCallback());

        FloatingActionButton btnAddPet = findViewById(R.id.main_fab_add_pet);
        btnAddPet.setOnClickListener(v -> {
            intent = new Intent(getApplicationContext(), AddPetActivity.class);
            launcher.launch(intent);
        });

        if (savedInstanceState == null) {
            currentFragment = AllPetsFragment.getInstance(pets);
            openFragment();
            navigationView.setCheckedItem(R.id.nav_all_pets);
        }
       }

    private void configNavigation() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        actionBarDrawerToggle.syncState();
    }

    private NavigationView.OnNavigationItemSelectedListener getItemSelectedEvent() {
        return new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.nav_all_pets) {
                    Toast.makeText(MainActivity.this, "All pets clicked!", Toast.LENGTH_SHORT).show();
                    currentFragment = AllPetsFragment.getInstance(pets);
                } else if (item.getItemId() == R.id.nav_gallery) {
                    Toast.makeText(MainActivity.this, "Gallery clicked!", Toast.LENGTH_SHORT).show();
                } else if (item.getItemId() == R.id.nav_slideshow) {
                    Toast.makeText(MainActivity.this, "Slideshow clicked!", Toast.LENGTH_SHORT).show();
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                openFragment();
                return true;
            }
        };
    }

    private void openFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_fl, currentFragment)
                .commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_add_pet) {
            Toast.makeText(this, "Add Pet called!", Toast.LENGTH_SHORT).show();
            intent = new Intent(getApplicationContext(), AddPetActivity.class);
            launcher.launch(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private ActivityResultCallback<ActivityResult> getAddPetCallback() {
        return result -> {
            if(result.getData() != null && result.getResultCode() == RESULT_OK) {
                Pet userPet = (Pet) result.getData().getParcelableExtra(AddPetActivity.PET_KEY);
                pets.add(userPet);
                if(currentFragment instanceof AllPetsFragment) {
                    ((AllPetsFragment) currentFragment).notifyAdapter();
                }
                Log.i("MainActivity", pets.toString());
            }
        };
    }
}