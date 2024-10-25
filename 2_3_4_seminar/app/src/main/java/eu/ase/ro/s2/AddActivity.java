package eu.ase.ro.s2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Date;

import eu.ase.ro.s2.domain.DateConverter;
import eu.ase.ro.s2.domain.Student;
import eu.ase.ro.s2.domain.StudyType;

public class AddActivity extends AppCompatActivity {
    public static final String STUDENT_KEY = "student_key";

    private TextInputEditText tietName;
    private TextInputEditText tietEnrollmentDate;
    private Spinner spnFaculty;
    private RadioGroup rgStudyType;
    private Button btnSave;

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

        initComponents();
        //pasul 2 din schema. New facem new Intent
        intent = getIntent();
    }

    private void initComponents() {
        tietName = findViewById(R.id.add_tiet_name);
        tietEnrollmentDate = findViewById(R.id.add_tiet_enrollment_date);
        spnFaculty = findViewById(R.id.add_spn_faculty);
        rgStudyType = findViewById(R.id.add_rg_study_type);
        btnSave = findViewById(R.id.add_btn_save);
        btnSave.setOnClickListener(view -> {
            //validare + construire obiect student
            if (isValid()) {
                //build student
                String name = tietName.getText().toString();
                Date enrollmentDate = DateConverter
                        .toDate(tietEnrollmentDate.getText().toString());
                String faculty = (String) spnFaculty.getSelectedItem();
                StudyType studyType = rgStudyType.getCheckedRadioButtonId() == R.id.add_rb_full_time ?
                        StudyType.FULL_TIME : StudyType.DISTANCE;

                Student student = new Student(name, enrollmentDate, faculty, studyType);
                Log.i("AddActivity", student.toString());

                //pasul 3 din schema
                intent.putExtra(STUDENT_KEY, student);
                setResult(RESULT_OK, intent);
                finish();
            }

        });
    }

    private boolean isValid() {
        if (tietName.getText() == null ||
                tietName.getText().toString().trim().length() < 3) {
            Toast.makeText(getApplicationContext(),
                            R.string.add_invalid_name,
                            Toast.LENGTH_LONG)
                    .show();
            return false;
        }

        if (tietEnrollmentDate.getText() == null ||
                DateConverter.toDate(tietEnrollmentDate
                        .getText().toString()) == null) {
            Toast.makeText(getApplicationContext(),
                            R.string.add_invalid_enrollment_date,
                            Toast.LENGTH_LONG)
                    .show();
            return false;
        }

        return true;
    }
}