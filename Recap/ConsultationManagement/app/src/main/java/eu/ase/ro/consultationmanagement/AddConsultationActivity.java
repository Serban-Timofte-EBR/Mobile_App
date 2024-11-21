package eu.ase.ro.consultationmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Date;

import eu.ase.ro.consultationmanagement.model.Consultation;
import eu.ase.ro.consultationmanagement.model.DateConverter;

public class AddConsultationActivity extends AppCompatActivity {
    public static final String CONSULTATION_KEY = "cons_key";

    private TextInputEditText tietDate;
    private TextInputEditText tietPatient;
    private Spinner spnDiagnostic;
    private Button btnSave;

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_consultation);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        intent = getIntent();
        initComponent();

        btnSave.setOnClickListener(v -> {
            if (isValid()) {
                Date date = DateConverter.toDate(tietDate.getText().toString());
                String patient = tietPatient.getText().toString();
                String diagnostic = spnDiagnostic.getSelectedItem().toString();

                Consultation cons = new Consultation(date, patient, diagnostic);

                intent.putExtra(CONSULTATION_KEY, cons);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    private void initComponent() {
        tietDate = findViewById(R.id.timofte_serban_add_tiet_date);
        tietPatient = findViewById(R.id.timofte_serban_add_tiet_patient);
        spnDiagnostic = findViewById(R.id.timofte_serban_add_spn_diagnostic);
        btnSave = findViewById(R.id.timofte_serban_add_btn_save);
    }

    private boolean isValid() {
        if (tietDate.getText().toString().trim().isEmpty() || DateConverter.toDate(tietDate.getText().toString()) == null) {
            Toast.makeText(this, "Invalid date", Toast.LENGTH_SHORT).show();
            return false;
        } else if (tietPatient.getText().toString().trim().isEmpty() || tietPatient.getText().toString().trim().length() < 3) {
            Toast.makeText(this, "Invalid patient", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}