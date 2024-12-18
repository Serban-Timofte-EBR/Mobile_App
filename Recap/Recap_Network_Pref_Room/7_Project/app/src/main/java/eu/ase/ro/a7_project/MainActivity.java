package eu.ase.ro.a7_project;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity {
    public static final String COMPANY_PREFERENCES = "company_preferences";
    public static final String NAME_PREF_KEY = "name_pref_key";
    private String NPOINT_URL = "https://api.npoint.io/c6be9bb84fb1746498cd";

    private List<Project> projects = new ArrayList<>();

    private Spinner spnComparation;
    private TextInputEditText tietCompany;
    private TextInputEditText tietBugetPrag;
    private FloatingActionButton fabGetData;
    private Button btnDelete;
    private Button btnUpdate;

    private AsyncTaskRunner asyncTaskRunner = new AsyncTaskRunner();
    private ProjectService projectService;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        projectService = new ProjectService(getApplicationContext());
        initComponent();

        projectService.getAll(getAllCallback());
    }

    private void initComponent() {
        spnComparation = findViewById(R.id.main_spn_comparation);
        tietCompany = findViewById(R.id.main_tiet_company);
        tietBugetPrag = findViewById(R.id.main_tiet_buget);
        fabGetData = findViewById(R.id.main_fab_get);
        btnDelete = findViewById(R.id.main_btn_delete);
        btnUpdate = findViewById(R.id.main_btn_update);
        sharedPreferences = getApplicationContext().getSharedPreferences(COMPANY_PREFERENCES, Context.MODE_PRIVATE);

        loadPreferences();

        fabGetData.setOnClickListener(getDataFromURL());
        btnDelete.setOnClickListener(deleteProjectsFromDB());
        btnUpdate.setOnClickListener(updateProjectsFromDB());
    }

    private View.OnClickListener updateProjectsFromDB() {
        return view -> {
            String companyName = tietCompany.getText().toString().isBlank()
                                ? ""
                                : tietCompany.getText().toString();

            if (companyName.length() > 3) {
                List<Project> projectsForUpdate = projects.stream()
                        .filter(project -> project.getCompany().equals(companyName))
                        .collect(Collectors.toList());

                projectsForUpdate.forEach(project -> project.setCost(0.0F));

                for (Project project : projectsForUpdate) {
                    projectService.update(project, getUpdateCallback());
                }
            }
        };
    }

    private Callback<Project> getUpdateCallback() {
        return result -> {
            projects.clear();
            projectService.getAll(getAllCallback());
        };
    }

    private void loadPreferences() {
        String companyName = sharedPreferences.getString(NAME_PREF_KEY, "");
        tietCompany.setText(companyName);
    }

    private View.OnClickListener deleteProjectsFromDB() {
        return view -> {
            String companyName = savePreferences();

            float bugetPrag = tietBugetPrag.getText().toString().isBlank()
                    ? 0
                    : Float.parseFloat(tietBugetPrag.getText().toString());

            String comparationValue = spnComparation.getSelectedItem().toString();

            switch (comparationValue) {
                case "Mai Mic":
                    List<Project> projectsToDelete = projects.stream()
                            .filter(project -> project.getCost() < bugetPrag)
                            .collect(Collectors.toList());
                    deleteProjects(projectsToDelete);
                    break;

                case "Mai Mare":
                    List<Project> projectsToDelete2 = projects.stream()
                            .filter(project -> project.getCost() > bugetPrag)
                            .collect(Collectors.toList());
                    deleteProjects(projectsToDelete2);
                    break;

                case "Egal":
                    List<Project> projectsToDelete3 = projects.stream()
                            .filter(project -> project.getCost() == bugetPrag)
                            .collect(Collectors.toList());
                    deleteProjects(projectsToDelete3);
                    break;
            }
        };
    }

    private void deleteProjects(List<Project> projectsToDelete) {
        for (Project project : projectsToDelete) {
            projectService.delete(project, getDeleteCallback());
        }
    }

    private Callback<Boolean> getDeleteCallback() {
        return result -> {
            projects.clear();
            projectService.getAll(getAllCallback());
        };
    }

    private String savePreferences() {
        String companyName = tietCompany.getText().toString().isBlank()
                ? ""
                : tietCompany.getText().toString();

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(NAME_PREF_KEY, companyName);
        editor.apply();

        return companyName;
    }

    private View.OnClickListener getDataFromURL() {
        return view -> {
            Callable<String> callable = new HttpManager(NPOINT_URL);
            Callback<String> callback = getDataFromURLCallback();
            asyncTaskRunner.executeAsync(callable, callback);
        };
    }

    private Callback<String> getDataFromURLCallback() {
        return result -> {
            List<Project> parsedProjects = ProjectParser.fromJSON(result);
            Log.i("NPOINT", parsedProjects.toString());

            for (Project project : parsedProjects) {
                if (!projects.contains(project)) {
                    projectService.insert(project, getInsertCallback());
                    Log.i("NPOINT", "Adaugam: " + project.toString());
                }
            }

            Log.i("NPOINT", "Lissta finala: " + projects.toString());
        };
    }

    private Callback<Project> getInsertCallback() {
        return result -> {
            if (result!=null) {
                projects.add(result);
            }
        };
    }

    private Callback<List<Project>> getAllCallback() {
        return result -> {
            projects.addAll(result);
        };
    }
}