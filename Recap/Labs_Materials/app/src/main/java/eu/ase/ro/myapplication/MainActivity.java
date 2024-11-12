package eu.ase.ro.myapplication;

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
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import eu.ase.ro.myapplication.databinding.ActivityMainBinding;
import eu.ase.ro.myapplication.model.Task;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    private FloatingActionButton fabAdd;
    private ActivityResultLauncher<Intent> launcher;

    private List<Task> tasks = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        configNavigation();

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(getItemListenerEvent());

        // Deschidem activitatea AddTask
        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), getAddTaskCallback());

        fabAdd = findViewById(R.id.main_fab_add);
        fabAdd.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), AddTaskActivity.class);
            launcher.launch(intent);
        });
    }

    // preiau info de la add task
    private ActivityResultCallback<ActivityResult> getAddTaskCallback() {
        return result -> {
            if(result.getResultCode() == RESULT_OK && result.getData() != null) {
                Task task = (Task) result.getData().getSerializableExtra(AddTaskActivity.TASK_KEY);
                tasks.add(task);
                Log.i("MainActivity add a task action", "taks = " + tasks.toString());
            }
        };
    }

    private void configNavigation() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        actionBarDrawerToggle.syncState();
    }

    private NavigationView.OnNavigationItemSelectedListener getItemListenerEvent() {
        return new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.nav_home) {
                    Toast.makeText(MainActivity.this, "Home is clicked", Toast.LENGTH_SHORT).show();
                }
                else if (item.getItemId() == R.id.nav_about) {
                    Toast.makeText(MainActivity.this, "About is clicked", Toast.LENGTH_SHORT).show();
                } else if(item.getItemId() == R.id.nav_profile) {
                    Toast.makeText(MainActivity.this, "Profile is clicked", Toast.LENGTH_SHORT).show();
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        };
    }
}