package eu.ase.ro.a2_exam;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

import eu.ase.ro.a2_exam.model.Course;
import eu.ase.ro.a2_exam.model.DateConvertor;
import eu.ase.ro.a2_exam.model.Exam;

public class AddExamActivity extends AppCompatActivity {
    public static final String ADD_EXAM_KEY = "add_exam_key";
    public static final String UPDATED_EXAM_FROM_ADD_KEY = "updated_exam_from_add_key";
    private Intent intent;
    private TextInputEditText tietDate;
    private Spinner spnCourse;
    private TextInputEditText tietClasroom;
    private TextInputEditText tietSupervisor;
    private Button btnSave;
    private Long examId = 0L;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_exam);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        intent = getIntent();
        initComponent();
    }

    private void initComponent() {
        tietDate = findViewById(R.id.tiet_date);
        spnCourse = findViewById(R.id.spinner_course);
        tietClasroom = findViewById(R.id.tiet_classroom);
        tietSupervisor = findViewById(R.id.tiet_supervisor);
        btnSave = findViewById(R.id.btn_save_exam);

        if (intent.getSerializableExtra(MainActivity.UPDATE_EXAM_KEY) != null) {
            Exam examForUpdate = (Exam) intent.getSerializableExtra(MainActivity.UPDATE_EXAM_KEY);
            tietDate.setText(DateConvertor.fromDate(examForUpdate.getDate()));
            tietClasroom.setText(examForUpdate.getClassRoom());
            tietSupervisor.setText(examForUpdate.getSupervisor());
            examId = examForUpdate.getId();

            btnSave.setOnClickListener(updateExamFromActiviy());
        } else {
            btnSave.setOnClickListener(saveExamFromActivity());
        }
    }

    private View.OnClickListener updateExamFromActiviy() {
        return view -> {
          if (isValid()) {
              Exam userExam = generateExamFromUI();
              userExam.setId(examId);
              intent.putExtra(UPDATED_EXAM_FROM_ADD_KEY, userExam);
              setResult(RESULT_OK, intent);
              finish();
          }
        };
    }

    private View.OnClickListener saveExamFromActivity() {
        return view -> {
          if (isValid()) {
              Exam userExam = generateExamFromUI();
              intent.putExtra(ADD_EXAM_KEY, userExam);
              setResult(RESULT_OK, intent);
              finish();
          }
        };
    }

    private Exam generateExamFromUI() {
        Date date = DateConvertor.toDate(tietDate.getText().toString());
        Course course = Course.valueOf(spnCourse.getSelectedItem().toString());
        String clasRoom = tietClasroom.getText().toString();
        String supervisor = tietSupervisor.getText().toString();
        return new Exam(date, course, clasRoom, supervisor);
    }

    private boolean isValid() {
        if (DateConvertor.toDate(tietDate.getText().toString()) == null) {
            Toast.makeText(getApplicationContext(), "Date Format is invalid!", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (tietClasroom.getText().toString().isBlank()) {
            Toast.makeText(getApplicationContext(), "Invalid Classroom!", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (tietSupervisor.getText().toString().isBlank()) {
            Toast.makeText(getApplicationContext(), "Invalid Supervisor!", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}