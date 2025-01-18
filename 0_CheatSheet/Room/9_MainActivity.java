package eu.ase.ro.recapapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import eu.ase.ro.recapapp.database.LabService;
import eu.ase.ro.recapapp.databinding.ActivityMainBinding;
import eu.ase.ro.recapapp.model.Lab;
import eu.ase.ro.recapapp.model.LabAdapter;
import eu.ase.ro.recapapp.network.AsyncTaskRunner;
import eu.ase.ro.recapapp.network.Callback;
import eu.ase.ro.recapapp.network.HttpManager;
import eu.ase.ro.recapapp.network.LabParser;

// Lucrul cu baza de date Room

// // libs.versions.toml
//     // libraries
//     room-common = { group = "androidx.room", name = "room-common", version.ref = "roomVersion" }
//     room-runtime = { group = "androidx.room", name = "room-runtime", version.ref = "roomVersion" }
//     room-compiler = { group = "androidx.room", name = "room-compiler", version.ref = "roomVersion" }

//     // versions
//     roomVersion = "2.6.1"

// // build.gradle (Module: app)
//     // dependencies
//     implementation libs.room.common
//     implementation libs.room.runtime
//     annotationProcessor libs.room.compiler


public class MainActivity extends AppCompatActivity {
    private final String NPOINT_URL = "https://api.npoint.io/0bd23b4265753b5c1db1";
    private AsyncTaskRunner asyncTaskRunner = new AsyncTaskRunner();

    List<Lab> labs = new ArrayList<>();

    private ActivityResultLauncher<Intent> launcher;
    private Intent intent;

    private LabService labService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), getAddLabCallback());
        configNavigation();
        initComponent();

        labService = new LabService(getApplicationContext());
        labService.getAll(getAllCallback());
    }

    private Callback<List<Lab>> getAllCallback() {
        return result -> {
            if (result != null) {
                labs.addAll(result);
                notifyAdapter();
            }
        };
    }

    private ActivityResultCallback<ActivityResult> getAddLabCallback() {
        return result -> {
            if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                Lab lab = (Lab) result.getData().getSerializableExtra(AddActivity.KEY_LAB);
                labs.add(lab);
                labService.insert(lab, insertCallback());
                notifyAdapter();
            }
        };
    }

    private Callback<Lab> insertCallback() {
        return result -> {
            if (result != null) {
                labs.add(result);
            }
        };
    }

    private void initComponent() {
        lvLabs = findViewById(R.id.main_lv_labs);
        LabAdapter adapter = new LabAdapter(getApplicationContext(),
                R.layout.row_lv_labs,
                labs,
                getLayoutInflater());
        lvLabs.setAdapter(adapter);

        fabAdd = findViewById(R.id.fab);
        fabAdd.setOnClickListener(v -> {
            intent = new Intent(getApplicationContext(), AddActivity.class);
            launcher.launch(intent);
        });
    }

    private void configNavigation() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.option_import) {
            Callable<String> callable = new HttpManager(NPOINT_URL);
            Callback<String> callback = getCallbackFromHttpManager();
            asyncTaskRunner.executeAsync(callable, callback);
        }
        return super.onOptionsItemSelected(item);
    }

    private Callback<String> getCallbackFromHttpManager() {
        return result -> {
            Toast.makeText(getApplicationContext(), result.toString(), Toast.LENGTH_SHORT).show();
            List<Lab> parsedLabs = LabParser.fromJSON(result);
            labs.addAll(parsedLabs);
            notifyAdapter();
        };
    }

    private void notifyAdapter() {
        LabAdapter adapter = (LabAdapter) lvLabs.getAdapter();
        adapter.notifyDataSetChanged();
    }
}