package eu.ase.ro.subiect1_task_manager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;

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
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import eu.ase.ro.subiect1_task_manager.databinding.ActivityMainBinding;
import eu.ase.ro.subiect1_task_manager.model.Task;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton button_addTask;

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    private ActivityResultLauncher<Intent> launcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        configNavigation();

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(getSelectedItemEvent());

        initComponent();
        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), getAddTaskCallback());
    }

    private void initComponent() {
        button_addTask = findViewById(R.id.main_fab_add_expense);
        button_addTask.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), AddTask.class);
            launcher.launch(intent);
        });
    }

    private void configNavigation() {
       Toolbar toolbar = findViewById(R.id.toolbar);
       setSupportActionBar(toolbar);

       drawerLayout = findViewById(R.id.drawer_layout);
       ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }

    private NavigationView.OnNavigationItemSelectedListener getSelectedItemEvent() {
        return new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        };
    }

    private ActivityResultCallback<ActivityResult> getAddTaskCallback() {
        return result -> {
            if(result.getResultCode() == RESULT_OK && result.getData() != null) {
                Intent data = result.getData();
            }
        };
    }
}