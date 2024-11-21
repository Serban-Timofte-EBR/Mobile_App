package eu.ase.ro.consultationmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import eu.ase.ro.consultationmanagement.databinding.ActivityMainBinding;
import eu.ase.ro.consultationmanagement.fragments.HomeFragment;
import eu.ase.ro.consultationmanagement.model.Consultation;
import eu.ase.ro.consultationmanagement.model.DateConverter;
import eu.ase.ro.consultationmanagement.model.HttpManager;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    private FloatingActionButton fabAdd;
    private Intent intent;
    private ActivityResultLauncher<Intent> launcher;

    private List<Consultation> consultations = new ArrayList<>();

    private Fragment currentFragment;

    private ExecutorService executor = Executors.newCachedThreadPool();
    private Handler handler = new Handler(Looper.getMainLooper());
    private final String URL = "https://api.npoint.io/0edcee8d25b73d07beab";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        configNavigation();

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(getNavItemEvent());

        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), getAddConsultationCallback());

        fabAdd = findViewById(R.id.timofte_serban_fab_add);
        fabAdd.setOnClickListener(v -> {
            intent = new Intent(getApplicationContext(), AddConsultationActivity.class);
            launcher.launch(intent);
        });

        if (savedInstanceState == null) {
            currentFragment = HomeFragment.getInstance(consultations);
            openFragment();
            navigationView.setCheckedItem(R.id.timofte_serban_nav_home);
        }
    }

    private NavigationView.OnNavigationItemSelectedListener getNavItemEvent() {
        return new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.timofte_serban_nav_home) {
                    currentFragment = HomeFragment.getInstance(consultations);
                }
                openFragment();
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        };
    }

    private void openFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.timofte_serban_main_fl, currentFragment)
                .commit();
    }

    private ActivityResultCallback<ActivityResult> getAddConsultationCallback() {
        return result -> {
            if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                Consultation consultation = (Consultation) result.getData().getParcelableExtra(AddConsultationActivity.CONSULTATION_KEY);
                consultations.add(consultation);
                if (currentFragment instanceof HomeFragment) {
                    ((HomeFragment) currentFragment).notifyData();
                }
                Toast.makeText(this, consultations.toString(), Toast.LENGTH_SHORT).show();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.timofte_serban_action_import) {
            Toast.makeText(this, "Importing data ...", Toast.LENGTH_SHORT).show();
            executor.execute(() -> {
                HttpManager httpManager = new HttpManager(URL);
                String result = httpManager.getCall();
                Log.i("Network", "Result: " + result);
                if (result!= null) {
                    List<Consultation> parsedList = parseResult(result);
                    Log.i("Network", "Parsed List: " + parsedList.toString());
                    if (parsedList != null) {
                        handler.post(() -> {
                            consultations.addAll(parsedList);
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

    private List<Consultation> parseResult(String result) {
        try {
            List<Consultation> list = new ArrayList<>();
            JSONArray jsonArray = new JSONArray(result);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                Date date = DateConverter.toDate(jsonObject.getString("date"));
                String patient = jsonObject.getString("patient");
                String diagnostic = jsonObject.getString("diagnostic");

                list.add(new Consultation(date, patient, diagnostic));
            }

            return list;
        } catch (JSONException e) {
            return null;
        }
    }
}