package eu.ase.ro.dam_app;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

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

import eu.ase.ro.dam_app.databinding.ActivityMainBinding;
import eu.ase.ro.dam_app.fragments.AboutFragment;
import eu.ase.ro.dam_app.fragments.HomeFragment;
import eu.ase.ro.dam_app.fragments.ProfileFragment;

public class MainActivity extends AppCompatActivity {

   private DrawerLayout drawerLayout;
   private NavigationView navigationView;
   private Fragment currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        configNavigation();

        navigationView = findViewById(R.id.main_nav_view);
        navigationView.setNavigationItemSelectedListener(getItemSelected());
    }

    private NavigationView.OnNavigationItemSelectedListener getItemSelected() {
        return new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.nav_home) {
                    Toast.makeText(getApplicationContext(), "Home clicked", Toast.LENGTH_LONG).show();
                    currentFragment = new HomeFragment();
                } else if (item.getItemId() == R.id.nav_profile) {
                    Toast.makeText(getApplicationContext(), "Profile clicked", Toast.LENGTH_SHORT).show();
                    currentFragment = new ProfileFragment();
                } else {
                    Toast.makeText(getApplicationContext(), "Profile clicked", Toast.LENGTH_SHORT).show();
                    currentFragment = new AboutFragment();
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
                .replace(R.id.main_dl_container, currentFragment)
                .commit();
    }

    private void configNavigation() {
        Toolbar toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.main_drawer);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
        );
        toggle.syncState(); // toggle isi ataseaza un setOnClickListener
    }


}