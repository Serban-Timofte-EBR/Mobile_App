package eu.ase.ro.subiect1_task_manager;

import android.os.Bundle;
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

import eu.ase.ro.subiect1_task_manager.model.DateConverter;

public class AddTask extends AppCompatActivity {

    private TextInputEditText tiet_deadline;
    private TextInputEditText tiet_username;
    private TextInputEditText tiet_description;
    private Spinner spinner_category;
    private RadioGroup radioGroup_price;
    private Button button_save;

    private Date deadline;
    private String username;
    private String description;
    private String category;
    private Double pricePerHour;

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
    }

    private void initComponent() {
        tiet_deadline = findViewById(R.id.add_tiet_date);
        tiet_username = findViewById(R.id.add_tiet_username);
        tiet_description = findViewById(R.id.add_tiet_description);
        spinner_category = findViewById(R.id.add_spinner_category);
        radioGroup_price = findViewById(R.id.add_rad_group_price);
        button_save = findViewById(R.id.add_btn_save);
        button_save.setOnClickListener(view -> {
            if(isValid()) {

            }
        });
    }

    private boolean isValid() {
        deadline = DateConverter.toDate(tiet_deadline.getText().toString());
        username = tiet_username.getText().toString();
        description = tiet_description.getText().toString();
        category = spinner_category.getSelectedItem().toString();
        pricePerHour = (double) (radioGroup_price.getCheckedRadioButtonId() == R.id.add_rb_price_10 ? 10 : radioGroup_price.getCheckedRadioButtonId() == R.id.add_rb_price_20 ? 20 : 30);

        if (deadline == null) {
            Toast.makeText(this, "Deadline must be valid", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (username.trim().length() < 3) {
            Toast.makeText(this, "Username must be longer than 3 letters", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (description.trim().length() < 5) {
            Toast.makeText(this, "Description is not valid", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}