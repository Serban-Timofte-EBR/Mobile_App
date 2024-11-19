package eu.ase.ro.student;

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

import eu.ase.ro.student.databinding.ActivityMainBinding;
import eu.ase.ro.student.fragments.CatalogFragment;
import eu.ase.ro.student.fragments.HomeFragment;
import eu.ase.ro.student.model.Student;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    private FloatingActionButton fabAdd;

    private List<Student> students = new ArrayList<>();

    private ActivityResultLauncher<Intent> launcher;
    private Intent intent;
    
    private Fragment currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        configNavig();

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(getEventListener());

        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), getAddStudentCallback());
        fabAdd = findViewById(R.id.timofte_serban_main_fab_add);
        fabAdd.setOnClickListener(v -> {
            intent = new Intent(this, AddStudent.class);
            launcher.launch(intent);
        });

        if (savedInstanceState == null) {
            currentFragment = HomeFragment.getInstance(students);
            openFragment();
            navigationView.setCheckedItem(R.id.timofte_serban_nav_home);
        }
    }

    private void configNavig() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        actionBarDrawerToggle.syncState();
    }

    private NavigationView.OnNavigationItemSelectedListener getEventListener() {
        return new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.timofte_serban_nav_home) {
                    currentFragment = HomeFragment.getInstance(students);
                } else if (item.getItemId() == R.id.timofte_serban_nav_catalog) {
                    currentFragment = new CatalogFragment();
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

    private ActivityResultCallback<ActivityResult> getAddStudentCallback() {
        return result -> {
            if (result.getResultCode() == RESULT_OK || result.getData() != null) {
                Student student = (Student) result.getData().getParcelableExtra(AddStudent.STUDENT_KEY);
                students.add(student);
                if (currentFragment instanceof HomeFragment) {
                    ((HomeFragment) currentFragment).notifyData();
                }
                Toast.makeText(this, students.toString(), Toast.LENGTH_SHORT).show();
            }
        };
    }
}