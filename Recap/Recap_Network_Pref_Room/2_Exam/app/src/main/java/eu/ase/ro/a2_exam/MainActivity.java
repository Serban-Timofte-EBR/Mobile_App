package eu.ase.ro.a2_exam;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;

import eu.ase.ro.a2_exam.database.ExamService;
import eu.ase.ro.a2_exam.model.Exam;
import eu.ase.ro.a2_exam.network.AsyncTaskRunner;
import eu.ase.ro.a2_exam.network.Callback;
import eu.ase.ro.a2_exam.network.ExamParser;
import eu.ase.ro.a2_exam.network.HttpManager;

public class MainActivity extends AppCompatActivity {
    private final String NPOINT_URL = "https://api.npoint.io/5b2a9af3a9e1874bdc4d";

    private List<Exam> exams = new ArrayList<>();

    private TextView tvHelloUser;
    private Spinner spnCourses;
    private Button btnGetData;
    private Button btnUserInfo;
    private FloatingActionButton fabAddExam;
    private ListView lvExams;

    private AsyncTaskRunner asyncTaskRunner = new AsyncTaskRunner();
    private SharedPreferences sharedPreferences;

    private ActivityResultLauncher<Intent> launcher;

    private ExamService examService;

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

        initComponent();

        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), addExamCallback());
        examService = new ExamService(getApplicationContext());
        examService.getAll(getAllCallback());
    }

    @Override
    protected void onResume() {
        super.onResume();
        displayHelloUser();
    }

    private void initComponent() {
        tvHelloUser = findViewById(R.id.main_tv_user);
        spnCourses = findViewById(R.id.main_spinner_courses);
        btnGetData = findViewById(R.id.main_btn_getData);
        btnUserInfo = findViewById(R.id.main_btn_user);
        fabAddExam = findViewById(R.id.main_fab_add);
        lvExams = findViewById(R.id.main_lv_exams);
        sharedPreferences = getApplicationContext().getSharedPreferences(UserActivity.SHARED_PREFERENCES_USER, Context.MODE_PRIVATE);

        displayHelloUser();

        ArrayAdapter<Exam> adapter = new ArrayAdapter<>(
                getApplicationContext(),
                android.R.layout.simple_list_item_1,
                exams);
        lvExams.setAdapter(adapter);
        lvExams.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Exam selectedExam = exams.get(position);
                Toast.makeText(getApplicationContext(), "Exam ID: " + selectedExam.getId(), Toast.LENGTH_SHORT).show();
            }
        });

        btnGetData.setOnClickListener(getDataFromNPoint());
        btnUserInfo.setOnClickListener(startUserActivity());
        fabAddExam.setOnClickListener(startAddExamActivity());
    }

    private View.OnClickListener startAddExamActivity() {
        return view -> {
            Intent intent = new Intent(getApplicationContext(), AddExamActivity.class);
            launcher.launch(intent);
        };
    }

    private void displayHelloUser() {
        String user_name = sharedPreferences.getString(UserActivity.PREFERENCES_USER_NAME, "");
        tvHelloUser.setText(getApplicationContext().getString(R.string.main_hello_user_template, user_name));
    }

    private View.OnClickListener startUserActivity() {
        return  view -> {
            Intent intent = new Intent(getApplicationContext(), UserActivity.class);
            startActivity(intent);
        };
    }

    private View.OnClickListener getDataFromNPoint() {
        return result -> {
            Callable<String> callable = new HttpManager(NPOINT_URL);
            Callback<String> callback = getDataCallback();
            asyncTaskRunner.executeAsync(callable, callback);
        };
    }

    private Callback<String> getDataCallback() {
        return result -> {
            List<Exam> jsonExams = ExamParser.fromJSON(result);

            for (Exam exam: jsonExams) {
                if (!exams.contains(exam)) {
                    examService.insert(exam, getInsertCallback());
                } else {
                    Toast.makeText(getApplicationContext(), "Some imported examns are already in the database", Toast.LENGTH_SHORT).show();
                }
            }
        };
    }

    private void notifyAdapter() {
        ArrayAdapter<Exam> adapter = (ArrayAdapter<Exam>) lvExams.getAdapter();
        adapter.notifyDataSetChanged();
        setUpSpinner();
    }

    private void setUpSpinner() {
        Set<String> courses = new HashSet<>();
        courses.add("All");
        for (Exam exam : exams) {
            courses.add(exam.getCourse().toString());
        }

        List<String> coursesForSpinner = new ArrayList<>(courses);

        ArrayAdapter<String> spnAdapter = new ArrayAdapter<>(
                getApplicationContext(),
                android.R.layout.simple_spinner_item,
                coursesForSpinner
        );
        spnAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnCourses.setAdapter(spnAdapter);
    }

    private ActivityResultCallback<ActivityResult> addExamCallback() {
        return result -> {
            if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                Exam addedExam = (Exam) result.getData().getSerializableExtra(AddExamActivity.ADD_EXAM_KEY);
                examService.insert(addedExam, getInsertCallback());
            }
        };
    }

    private Callback<List<Exam>> getAllCallback() {
        return result -> {
            exams.addAll(result);
            notifyAdapter();
        };
    }

    private Callback<Exam> getInsertCallback() {
        return result -> {
            examService.getAll(getAllCallback());
        };
    }
}