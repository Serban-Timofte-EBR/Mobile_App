package eu.ase.ro.s2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import eu.ase.ro.s2.domain.Student;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton fabAdd;
    private ListView lvStudents;

    private ActivityResultLauncher<Intent> launcher;
    private List<Student> students = new ArrayList<>();

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

        initComponents();
        launcher = registerForActivityResult(new ActivityResultContracts
                .StartActivityForResult(), getAddStudentCallback());

    }

    private ActivityResultCallback<ActivityResult> getAddStudentCallback() {
        return result -> {
            //pasul 4 din schema
            if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                Student student = (Student) result.getData()
                        .getSerializableExtra(AddActivity.STUDENT_KEY);
                Toast.makeText(getApplicationContext(), student.toString(),
                        Toast.LENGTH_SHORT).show();
                students.add(student);
                //notificare adapter sa incarce noul obiect pe ecran
                ArrayAdapter<Student> adapter = (ArrayAdapter<Student>) lvStudents.getAdapter();
                adapter.notifyDataSetChanged();
            }
        };
    }

    private void initComponents() {
        fabAdd = findViewById(R.id.main_fab_add);
        fabAdd.setOnClickListener(getAddStudentEvent());
        lvStudents = findViewById(R.id.main_lv_students);
        ArrayAdapter<Student> adapter = new ArrayAdapter<>(getApplicationContext(),
                android.R.layout.simple_list_item_1, students);
        lvStudents.setAdapter(adapter);
    }

    private View.OnClickListener getAddStudentEvent() {
        return view -> {
            Intent intent = new Intent(getApplicationContext(),
                    AddActivity.class);
            //pasul 1 din schema
            launcher.launch(intent);
            //startActivity(intent);
        };
    }
}