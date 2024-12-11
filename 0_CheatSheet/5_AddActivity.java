package eu.ase.ro.recapapp;

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

import eu.ase.ro.recapapp.model.DateConverter;
import eu.ase.ro.recapapp.model.Lab;

public class AddActivity extends AppCompatActivity {
    public static final String KEY_LAB = "key_lab";

    private TextInputEditText tietLabName;
    private TextInputEditText tietLabDate;
    private Spinner spnLabRoom;
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

        intent = getIntent();
        initComponent();
    }

    private void initComponent() {
        tietLabDate = findViewById(R.id.add_tiet_lab_date);
        tietLabName = findViewById(R.id.add_tiet_lab_name);
        spnLabRoom = findViewById(R.id.add_spinner_class);
        btnSave = findViewById(R.id.add_btn_save);

        btnSave.setOnClickListener(v -> {
            if (isValid()) {
                String labName = tietLabName.getText().toString();
                Date labDate = DateConverter.toDate(tietLabDate.getText().toString());
                Integer labRoom = Integer.parseInt(spnLabRoom.getSelectedItem().toString());

                Lab userLab = new Lab(labDate, labRoom, labName);
                intent.putExtra(KEY_LAB, userLab);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    private boolean isValid() {
        String labName = tietLabName.getText() != null ? tietLabName.getText().toString().trim() : "";
        String labDateInput = tietLabDate.getText() != null ? tietLabDate.getText().toString().trim() : "";

        if (labName.isEmpty()) {
            Toast.makeText(this, "Lab Name cannot be empty", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (labDateInput.isEmpty()) {
            Toast.makeText(this, "Lab Date cannot be empty", Toast.LENGTH_SHORT).show();
            return false;
        }

        Date labDate = new DateConverter().toDate(labDateInput);
        if (labDate == null) {
            Toast.makeText(this, "Invalid date format. Use dd-MM-yyyy", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (spnLabRoom.getSelectedItem() == null) {
            Toast.makeText(this, "Please select a class from the spinner", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}