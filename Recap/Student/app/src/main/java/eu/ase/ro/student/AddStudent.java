package eu.ase.ro.student;

import android.content.Intent;
import android.os.Bundle;
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

import org.w3c.dom.Text;

import eu.ase.ro.student.model.Student;

public class AddStudent extends AppCompatActivity {
    public static final String STUDENT_KEY = "student_key";

    private TextInputEditText tietName;
    private TextInputEditText tietAge;
    private Spinner spnFaculty;
    private RadioGroup rgFrequency;
    private RadioButton rbZi;
    private RadioButton rbID;
    private Button btnSave;

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_student);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        intent = getIntent();
        initComponent();
    }

    private void initComponent() {
        tietName = findViewById(R.id.timofte_serban_add_tiet_name);
        tietAge = findViewById(R.id.timofte_serban_add_tiet_age);
        spnFaculty = findViewById(R.id.timofte_serban_add_spn_faculty);
        rgFrequency = findViewById(R.id.timofte_serban_rg_frequency);
        rbZi = findViewById(R.id.timofte_serban_rb_zi);
        rbID = findViewById(R.id.timofte_serban_rb_id);
        btnSave = findViewById(R.id.timofte_serban_add_btn_save);

        btnSave.setOnClickListener(v -> {
            if(isValid()) {
                Student userStudent = createUserStudent();
                intent.putExtra(STUDENT_KEY, userStudent);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    private boolean isValid() {
        if (tietName.getText() == null || tietName.getText().toString().trim().length() < 3) {
            Toast.makeText(this, "Invalid name", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (tietAge.getText() == null || tietAge.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Invalid age", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private Student createUserStudent() {
        String name = tietName.getText().toString();
        try {
            Integer age = Integer.parseInt(tietAge.getText().toString());
            String faculty = spnFaculty.getSelectedItem().toString();
            String frequency = rgFrequency.getCheckedRadioButtonId() == rbZi.getId() ? "Zi" :
                    "La Distanta";
            return new Student(name, age, faculty, frequency);
        } catch (Error e) {
            Toast.makeText(this, "Invalid number for age!", Toast.LENGTH_SHORT).show();
            return null;
        }
    }
}