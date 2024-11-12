package eu.ase.ro.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

import eu.ase.ro.myapplication.model.DateConverter;
import eu.ase.ro.myapplication.model.Task;

public class AddTaskActivity extends AppCompatActivity {
    public static final String TASK_KEY = "task_key";

    private TextInputEditText tietDeadline;
    private TextInputEditText tietUsername;
    private TextInputEditText tietDescription;
    private Spinner spnCategory;
    private RadioGroup rgPrice;
    private Button btnSave;

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_task);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        
        initComponent();
        intent = getIntent();
    }

    private void initComponent() {
        tietDeadline = findViewById(R.id.add_tiet_date);
        tietUsername = findViewById(R.id.add_tiet_username);
        tietDescription = findViewById(R.id.add_tiet_description);
        spnCategory = findViewById(R.id.add_spinner_category);
        rgPrice = findViewById(R.id.add_rad_group_price);
        btnSave = findViewById(R.id.add_btn_save);

        btnSave.setOnClickListener(saveTask());
    }

    private View.OnClickListener saveTask() {
        return v -> {
            if(isValid()) {
                // Creem obiectul din datele inserate de utilizator
                Task task = buildTaskFromView();
                intent.putExtra(TASK_KEY, task);
                setResult(RESULT_OK, intent);
                finish();
            }
        };
    }

    private Task buildTaskFromView() {
        Date deadline = DateConverter.toDate(tietDeadline.getText().toString());
        String username = tietUsername.getText().toString();
        String description = tietDescription.getText().toString();
        String category = (String) spnCategory.getSelectedItem();
        Double price = rgPrice.getCheckedRadioButtonId() == R.id.add_rb_price_10 ? 10.0 : rgPrice.getCheckedRadioButtonId() == R.id.add_rb_price_20 ? 20.0 : 30.0;

        return new Task(deadline, username, description,category,price);
    }

    private boolean isValid() {
        if(tietDeadline.getText() == null || DateConverter.toDate(tietDeadline.getText().toString()) == null) {
            Toast.makeText(this, "Deadline format is not valid", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(tietUsername.getText().toString().trim().length() < 3) {
            Toast.makeText(this, "Username is too short!", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}