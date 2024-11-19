package eu.ase.ro.cars;

import android.content.Intent;
import android.os.Bundle;
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
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import eu.ase.ro.cars.databinding.ActivityMainBinding;
import eu.ase.ro.cars.fragment.HomeFragment;
import eu.ase.ro.cars.models.Car;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    private List<Car> cars = new ArrayList<>();

    private Intent intent;
    private ActivityResultLauncher<Intent> launcher;

    private FloatingActionButton fabAdd;

    private Fragment currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        configNav();

        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), getAddCarCallback());

        fabAdd = findViewById(R.id.timofte_serban_main_fabb_add);
        fabAdd.setOnClickListener(v -> {
            intent = new Intent(this, AddCarActivity.class);
            launcher.launch(intent);
        });

        if (savedInstanceState == null) {
            currentFragment = HomeFragment.getInstance(cars);
            openFragment();
        }
    }

    private ActivityResultCallback<ActivityResult> getAddCarCallback() {
        return result -> {
            if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                Car car = (Car) result.getData().getParcelableExtra(AddCarActivity.CAR_KEY);
                cars.add(car);
                if (currentFragment instanceof HomeFragment) {
                    ((HomeFragment) currentFragment).notifyData();
                }
            }
        };
    }

    private void configNav() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        drawerLayout = findViewById(R.id.drawer_layout);
//        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        actionBarDrawerToggle.syncState();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.timofte_serban_option_add_card) {
            intent = new Intent(this, AddCarActivity.class);
            launcher.launch(intent);
        } else if (item.getItemId() == R.id.timofte_serban_option_see_prices) {
            Toast.makeText(this, "List of prices will be displayed", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    private void openFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.timofte_serban_main_fl, currentFragment)
                .commit();
    }
}