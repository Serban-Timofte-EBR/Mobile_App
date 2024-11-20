package eu.ase.ro.eventsmanager;

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

import eu.ase.ro.eventsmanager.databinding.ActivityMainBinding;
import eu.ase.ro.eventsmanager.fragments.HomeFragment;
import eu.ase.ro.eventsmanager.model.Event;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    private FloatingActionButton fabAdd;

    private Intent intent;
    private ActivityResultLauncher<Intent> launcher;

    private List<Event> events = new ArrayList<>();

    private Fragment currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        configNavigation();

        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), getAddActivityCallback());

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(getNavItemClickedEvent());

        fabAdd = findViewById(R.id.timofte_serban_main_fab_add);
        fabAdd.setOnClickListener(v -> {
            intent = new Intent(getApplicationContext(), AddEventActivity.class);
            launcher.launch(intent);
        });

        if (savedInstanceState == null) {
            currentFragment = HomeFragment.getInstance(events);
            openFragment();
            navigationView.setCheckedItem(R.id.timofte_serban_nav_home);
        }
    }

    private ActivityResultCallback<ActivityResult> getAddActivityCallback() {
        return result -> {
            if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                Event event = (Event) result.getData().getParcelableExtra(AddEventActivity.EVENT_KEY);
                events.add(event);

                if (currentFragment instanceof HomeFragment) {
                    ((HomeFragment) currentFragment).notifyData();
                }
            }
        };
    }

    private NavigationView.OnNavigationItemSelectedListener getNavItemClickedEvent() {
        return new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.timofte_serban_nav_home) {
                    currentFragment = HomeFragment.getInstance(events);
                    openFragment();
                } else if (item.getItemId() == R.id.timofte_serban_nav_add) {
                    intent = new Intent(getApplicationContext(), AddEventActivity.class);
                    launcher.launch(intent);
                }
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

    private void configNavigation() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,
                drawerLayout,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        actionBarDrawerToggle.syncState();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}