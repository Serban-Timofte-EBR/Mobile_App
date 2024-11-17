package eu.ase.ro.triviachimieorganica;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

import java.util.ArrayList;
import java.util.List;

import eu.ase.ro.triviachimieorganica.databinding.ActivityMainBinding;
import eu.ase.ro.triviachimieorganica.fragments.HomeFragment;
import eu.ase.ro.triviachimieorganica.fragments.TriviaFragment;
import eu.ase.ro.triviachimieorganica.models.Result;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    private Fragment currentFragment;

    private List<Result> results = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initComponent();
        
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(getSelectItemEvent());

        if (savedInstanceState == null) {
            currentFragment = new HomeFragment();
            openFragment();
            navigationView.setCheckedItem(R.id.timofte_serban_nav_home);
        }
    }

    private void initComponent() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        actionBarDrawerToggle.syncState();
    }

    private NavigationView.OnNavigationItemSelectedListener getSelectItemEvent() {
        return new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.timofte_serban_nav_home) {
                    Toast.makeText(MainActivity.this, "Home", Toast.LENGTH_SHORT).show();
                    currentFragment = new HomeFragment();
                } else if (item.getItemId() == R.id.timofte_serban_nav_trivia) {
                    Toast.makeText(MainActivity.this, "Trivia", Toast.LENGTH_SHORT).show();
                    currentFragment = new TriviaFragment();
                } else if (item.getItemId() == R.id.timofte_serban_nav_history) {
                    Toast.makeText(MainActivity.this, "History", Toast.LENGTH_SHORT).show();
                }
                openFragment();
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        };
    }

    private void openFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.timofte_serban_main_fl, currentFragment)
                .commit();
    }

    public void addResult(Result value) {
        results.add(value);
        Log.i("MainActivity", results.toString());
    }

    public List<Result> getResults() {
        return this.results;
    }
}