package eu.ase.ro.a4_pacient;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import eu.ase.ro.a4_pacient.database.PatientService;
import eu.ase.ro.a4_pacient.model.Patient;
import eu.ase.ro.a4_pacient.network.AsyncTaskRunner;
import eu.ase.ro.a4_pacient.network.Callback;
import eu.ase.ro.a4_pacient.network.HttpManager;
import eu.ase.ro.a4_pacient.network.PatientParser;

public class MainActivity extends AppCompatActivity {
    public static final String MAIN_UPDATE_ACTIVITY_KEY = "main_update_activity_key";
    private final String NPOINT_URL = "https://api.npoint.io/f72e65ad5018e0ffe3c0";
    private TextView tvWelcome;
    private ListView lvPatients;
    private FloatingActionButton fabAdd;
    private FloatingActionButton fabInstitution;
    private FloatingActionButton fabGetData;

    private List<Patient> patients = new ArrayList<>();

    private ActivityResultLauncher<Intent> launcher;
    private PatientService patientService;
    private AsyncTaskRunner asyncTaskRunner = new AsyncTaskRunner();

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
        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), getAddPatientCallback());
        initComponent();

        patientService = new PatientService(getApplicationContext());
        patientService.getAll(getAllCallback());
    }

    @Override
    protected void onResume() {
        super.onResume();
        displayWelcomeMessage();
    }

    private ActivityResultCallback<ActivityResult> getAddPatientCallback() {
        return result -> {
            if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                if (result.getData().getSerializableExtra(AddActivity.ADD_PATIENT_KEY) != null) {
                    Patient resultPatient = (Patient) result.getData().getSerializableExtra(AddActivity.ADD_PATIENT_KEY);
                    patientService.insert(resultPatient, getInsertCallback());
                } else if (result.getData().getSerializableExtra(AddActivity.UPDATE_ACTIVITY_KEY) != null) {
                    Patient updatedPatient = (Patient) result.getData().getSerializableExtra(AddActivity.UPDATE_ACTIVITY_KEY);
                    patientService.update(updatedPatient, getUpdateCallback());
                }
            }
        };
    }

    private void initComponent() {
        tvWelcome = findViewById(R.id.welcome_tv);
        lvPatients = findViewById(R.id.main_lv_patients);
        fabAdd = findViewById(R.id.main_fab_add);
        fabInstitution = findViewById(R.id.main_fab_institution);
        fabGetData = findViewById(R.id.main_fab_getData);

        displayWelcomeMessage();

        ArrayAdapter<Patient> adapter = new ArrayAdapter<>(
                getApplicationContext(),
                android.R.layout.simple_list_item_1,
                patients
        );
        lvPatients.setAdapter(adapter);

        lvPatients.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Patient updatePatient = patients.get(position);
                Intent updateIntent = new Intent(getApplicationContext(), AddActivity.class);
                updateIntent.putExtra(MAIN_UPDATE_ACTIVITY_KEY, updatePatient);
                launcher.launch(updateIntent);
            }
        });

        lvPatients.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Patient deletePatient = patients.get(position);
                int initialSize = patients.size();
                patientService.delete(deletePatient, getDeleteCallback());
                return patients.size() < initialSize;
            }
        });

        fabInstitution.setOnClickListener(launchHospitalActivity());
        fabAdd.setOnClickListener(launchAddActivity());
        fabGetData.setOnClickListener(getNPOINTData());
    }

    private View.OnClickListener getNPOINTData() {
        return view -> {
            Callable<String> callable = new HttpManager(NPOINT_URL);
            Callback<String> callback = getNPOINTCallback();
            asyncTaskRunner.executeAsync(callable, callback);
        };
    }

    private Callback<String> getNPOINTCallback() {
        return result -> {
            if (result != null) {
                List<Patient> npointList = PatientParser.parseJSON(result);

                for (Patient patient : npointList) {
                    if (!patients.contains(patient)) {
                        patientService.insert(patient, getInsertCallback());
                    }
                }
            }
        };
    }

    private void displayWelcomeMessage() {
        sharedPreferences = getApplicationContext().getSharedPreferences(HospitalActivity.HOSPITAL_PREFERENCES, Context.MODE_PRIVATE);
        String hospitalName = sharedPreferences.getString(HospitalActivity.PREF_HOSPITAL_NAME, "");
        tvWelcome.setText(getString(R.string.welcom_message_template, hospitalName));
    }

    private View.OnClickListener launchHospitalActivity() {
        return view -> {
            Intent intent = new Intent(getApplicationContext(), HospitalActivity.class);
            startActivity(intent);
        };
    }

    private View.OnClickListener launchAddActivity() {
        return view -> {
            Intent intent = new Intent(getApplicationContext(), AddActivity.class);
            launcher.launch(intent);
        };
    }

    private void notifyAdapter() {
        ArrayAdapter<Patient> adapter = (ArrayAdapter<Patient>) lvPatients.getAdapter();
        adapter.notifyDataSetChanged();
    }

    private Callback<List<Patient>> getAllCallback() {
        return result -> {
            if (result != null) {
                patients.addAll(result);
                notifyAdapter();
            }
        };
    }

    private Callback<Patient> getInsertCallback() {
        return result -> {
            if (result.getId() > 0) {
                patients.add(result);
                notifyAdapter();
            }
        };
    }

    private Callback<Patient> getUpdateCallback() {
        return result -> {
            if (result != null) {
                patients.clear();
                patientService.getAll(getAllCallback());
            }
        };
    }

    private Callback<Boolean> getDeleteCallback() {
        return result -> {
            patients.clear();
            patientService.getAll(getAllCallback());
        };
    }
}