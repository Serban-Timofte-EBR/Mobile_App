package eu.ase.ro.recapapp;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

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

import eu.ase.ro.recapapp.databinding.ActivityMainBinding;
import eu.ase.ro.recapapp.model.Lab;
import eu.ase.ro.recapapp.model.LabAdapter;
import eu.ase.ro.recapapp.network.AsyncTaskRunner;
import eu.ase.ro.recapapp.network.Callback;
import eu.ase.ro.recapapp.network.HttpManager;
import eu.ase.ro.recapapp.network.LabParser;

public class MainActivity extends AppCompatActivity {
    private final String NPOINT_URL = "https://api.npoint.io/0bd23b4265753b5c1db1";
    private AsyncTaskRunner asyncTaskRunner = new AsyncTaskRunner();

    List<Lab> labs = new ArrayList<>();
    ListView lvLabs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        configNavigation();
        initComponent();
    }

    private void initComponent() {
        lvLabs = findViewById(R.id.main_lv_labs);
        LabAdapter adapter = new LabAdapter(getApplicationContext(),
                R.layout.row_lv_labs,
                labs,
                getLayoutInflater());
        lvLabs.setAdapter(adapter);
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