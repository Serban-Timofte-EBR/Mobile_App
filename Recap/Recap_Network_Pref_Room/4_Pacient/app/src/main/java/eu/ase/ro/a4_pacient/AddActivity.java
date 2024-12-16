package eu.ase.ro.a4_pacient;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;

import eu.ase.ro.a4_pacient.model.Patient;

public class AddActivity extends AppCompatActivity {
    public static final String ADD_PATIENT_KEY = "add_patient_key";
    public static final String UPDATE_ACTIVITY_KEY = "update_activity_key";
    private TextInputEditText tietName;
    private TextInputEditText tietHospital;
    private TextInputEditText tietNrConsultation;
    private Button btnSave;

    private Long patientID = 0L;

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        intent = getIntent();
        initComponent();
    }

    private void initComponent() {
        tietName = findViewById(R.id.tiet_name);
        tietHospital = findViewById(R.id.tiet_hospital);
        tietNrConsultation = findViewById(R.id.tiet_consultations);
        btnSave = findViewById(R.id.btn_save_patient);

        if (intent.getSerializableExtra(MainActivity.MAIN_UPDATE_ACTIVITY_KEY) != null) {
            Patient updatePatient = (Patient) intent.getSerializableExtra(MainActivity.MAIN_UPDATE_ACTIVITY_KEY);
            patientID = updatePatient.getId();
            tietName.setText(updatePatient.getName().toString());
            tietHospital.setText(updatePatient.getHospital().toString());
            tietNrConsultation.setText(updatePatient.getNumberOfConsultations().toString());

            btnSave.setOnClickListener(updateActivityPatient());
        } else {
            btnSave.setOnClickListener(saveActivityPatient());
        }
    }

    private View.OnClickListener updateActivityPatient() {
        return view -> {
            if (isValid()) {
                Patient userPatient = getPatientFromActivity();
                userPatient.setId(patientID);
                intent.putExtra(UPDATE_ACTIVITY_KEY, userPatient);
                setResult(RESULT_OK, intent);
                finish();
            }
        };
    }

    private View.OnClickListener saveActivityPatient() {
        return view -> {
            if (isValid()) {
                Patient activityPatient = getPatientFromActivity();
                intent.putExtra(ADD_PATIENT_KEY, activityPatient);
                setResult(RESULT_OK, intent);
                finish();
            }
        };
    }

    private boolean isValid() {
        if (tietName.getText().toString().isBlank()) {
            Toast.makeText(getApplicationContext(), R.string.add_invalid_name, Toast.LENGTH_SHORT).show();
            return false;
        }

        if (tietHospital.getText().toString().isBlank()) {
            Toast.makeText(getApplicationContext(), R.string.add_invalid_hospital, Toast.LENGTH_SHORT).show();
            return false;
        }

        if (tietNrConsultation.getText().toString().isBlank() || Integer.parseInt(tietNrConsultation.getText().toString()) < 0) {
            Toast.makeText(getApplicationContext(), R.string.add_invalid_number_of_consultation, Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private Patient getPatientFromActivity() {
        String name = tietName.getText().toString();
        String hospital = tietHospital.getText().toString();
        Integer nrCons = Integer.parseInt(tietNrConsultation.getText().toString());

        return new Patient(name, hospital, nrCons);
    }
}