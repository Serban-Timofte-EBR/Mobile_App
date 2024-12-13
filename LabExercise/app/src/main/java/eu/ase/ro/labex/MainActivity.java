package eu.ase.ro.labex;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import eu.ase.ro.labex.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private final String NPOINT_URL = "https://api.npoint.io/5b2a9af3a9e1874bdc4d";

    private Button btnDescarca;
    List<Exam> exams = new ArrayList<>();

    private AsyncTaskRunner asyncTaskRunner = new AsyncTaskRunner();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initComponent();
    }

    private void initComponent() {
        btnDescarca = findViewById(R.id.timofte_serban_main_btn_download);
        btnDescarca.setOnClickListener(v -> {
            Callable<String> callable = new HttpManager(NPOINT_URL);
            Callback<String> callback = getExamCallback();

            asyncTaskRunner.executeAsync(callable, callback);
        });
    }

    private Callback<String> getExamCallback() {
        return result -> {
            if (result == null) {
                Toast.makeText(getApplicationContext(), "Eroare la decarcare", Toast.LENGTH_SHORT).show();
                return;
            }

            exams = ExamParser.fromJson(result);

            Log.i("ExamParser", "Lista de examene:");
            Log.i("ExamParser", exams.toString());

            Toast.makeText(getApplicationContext(), "Datele au fost descarcate și parșate!", Toast.LENGTH_SHORT).show();
        };
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}