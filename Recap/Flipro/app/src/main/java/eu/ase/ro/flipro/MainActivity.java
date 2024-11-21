package eu.ase.ro.flipro;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import eu.ase.ro.flipro.databinding.ActivityMainBinding;
import eu.ase.ro.flipro.fragments.HomeFragment;
import eu.ase.ro.flipro.models.HttpManager;
import eu.ase.ro.flipro.models.Telefon;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    private FloatingActionButton fabAdd;

    private ActivityResultLauncher<Intent> launcher;
    private Intent intent;

    private List<Telefon> phones = new ArrayList<>();

    private Fragment currentFragment;

    private final String URL = "https://api.npoint.io/bc6fd90292c7cfefbfbd";
    private ExecutorService executor = Executors.newCachedThreadPool();
    private Handler handler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), getAddPhoneCallback());
        configNavigation();

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(getItemSelectedEvent());

        fabAdd = findViewById(R.id.timofte_serban_main_fab_add);
        fabAdd.setOnClickListener(v -> {
            intent = new Intent(getApplicationContext(), AddTelefonActivity.class);
            launcher.launch(intent);
        });

        if (savedInstanceState == null) {
            currentFragment = HomeFragment.getInstance(phones);
            openFragment();
            navigationView.setCheckedItem(R.id.timofte_serban_nav_home);
        }
    }

    private ActivityResultCallback<ActivityResult> getAddPhoneCallback() {
        return result -> {
            if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                Telefon phone = (Telefon) result.getData().getParcelableExtra(AddTelefonActivity.PHONE_KEY);
                phones.add(phone);

                if (currentFragment instanceof HomeFragment) {
                    ((HomeFragment) currentFragment).notifyData();
                }
//                Toast.makeText(this, phones.toString(), Toast.LENGTH_SHORT).show();
            }
        };
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

    private NavigationView.OnNavigationItemSelectedListener getItemSelectedEvent() {
        return new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.timofte_serban_nav_home) {
                    currentFragment = HomeFragment.getInstance(phones);
                }
                openFragment();
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        };
    }

    private void openFragment() {
        getSupportFragmentManager().beginTransaction().replace(R.id.timofte_serban_main_fl, currentFragment).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.timofte_serban_action_import_data) {
            Toast.makeText(this, "Importing data ...", Toast.LENGTH_SHORT).show();
            executor.execute(() -> {
                HttpManager httpManager = new HttpManager(URL);
                String result = httpManager.getCall();

                if (result != null) {
                    List<Telefon> parsedList = parseList(result);

                    if (parsedList != null) {
                        handler.post(() -> {
                            phones.addAll(parsedList);
                            if (currentFragment instanceof HomeFragment) {
                                ((HomeFragment) currentFragment).notifyData();
                            }
                        });
                    }
                }
            });
        }
        return super.onOptionsItemSelected(item);
    }

    private List<Telefon> parseList(String result) {
        try {

            List<Telefon> list = new ArrayList<>();
            JSONArray jsonArray = new JSONArray(result);

            for (int i = 0; i <jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                String brand = jsonObject.getString("brand");
                String model = jsonObject.getString("model");
                Integer baterie = jsonObject.getInt("procentBaterie");

                list.add(new Telefon(brand, model, baterie));
            }

            return list;
        } catch (JSONException e) {
            return null;
        }
    }
}