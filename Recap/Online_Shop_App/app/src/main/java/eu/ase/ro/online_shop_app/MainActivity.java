package eu.ase.ro.online_shop_app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

import eu.ase.ro.online_shop_app.databinding.ActivityMainBinding;
import eu.ase.ro.online_shop_app.fragment.AboutFragment;
import eu.ase.ro.online_shop_app.fragment.HomeFragment;
import eu.ase.ro.online_shop_app.model.Product;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    private FloatingActionButton fabAdd;

    private Intent intent;
    private ActivityResultLauncher<Intent> launcher;

    private List<Product> products = new ArrayList<>();

    private Fragment currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initConfig();

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(getSelectedItemEvent());

        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), getAddProductCallback());

        fabAdd = findViewById(R.id.main_fab_add);
        fabAdd.setOnClickListener(view -> {
            intent = new Intent(this, AddProductActivity.class);
            launcher.launch(intent);
        });

        if (savedInstanceState == null) {
            currentFragment = HomeFragment.getInstance(products);
            openFragment();
            navigationView.setCheckedItem(R.id.nav_home);
        }
    }

    private void initConfig() {
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

    private NavigationView.OnNavigationItemSelectedListener getSelectedItemEvent() {
        return new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.nav_home) {
                    Toast.makeText(MainActivity.this, "Home clicked!", Toast.LENGTH_SHORT).show();
                    currentFragment = HomeFragment.getInstance(products);
                } else if (item.getItemId() == R.id.nav_about) {
                    Toast.makeText(MainActivity.this, "About clicked!", Toast.LENGTH_SHORT).show();
                    currentFragment = new AboutFragment();
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
                .replace(R.id.main_fl, currentFragment)
                .commit();
    }

    private ActivityResultCallback<ActivityResult> getAddProductCallback() {
        return result -> {
            if (result.getResultCode() == RESULT_OK &&
                    result.getData() != null) {
                Product product = (Product) result.getData().getParcelableExtra(AddProductActivity.PRODUCT_KEY);
                products.add(product);
                if (currentFragment instanceof HomeFragment) {
                    ((HomeFragment) currentFragment).notifyAdapter();
                }
                Log.i("MainActivity", products.toString());
            }
        };
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_import) {
            Toast.makeText(this, "Import data clicked!", Toast.LENGTH_SHORT).show();
        } else if (item.getItemId() == R.id.action_add_product) {
            Toast.makeText(this, "Add Product Action clicked!", Toast.LENGTH_SHORT).show();
            intent = new Intent(this, AddProductActivity.class);
            launcher.launch(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}