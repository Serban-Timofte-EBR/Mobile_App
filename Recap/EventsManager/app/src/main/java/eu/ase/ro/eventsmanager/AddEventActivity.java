package eu.ase.ro.eventsmanager;

import android.content.Intent;
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

import eu.ase.ro.eventsmanager.model.Event;

public class AddEventActivity extends AppCompatActivity {
    public static final String EVENT_KEY = "event_key";

    private TextInputEditText tietPerson;
    private Spinner spnLocation;
    private RadioGroup rgType;
    private Button btnSave;

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_event);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        intent = getIntent();
        initComponents();
    }

    private void initComponents() {
        tietPerson = findViewById(R.id.timofte_serban_add_tiet_contactPerson);
        spnLocation = findViewById(R.id.timofte_serban_add_spn_location);
        rgType = findViewById(R.id.timofte_serban_add_rg_type);
        btnSave = findViewById(R.id.timofte_serban_add_btn_save);

        btnSave.setOnClickListener(v -> {
            if (isValid()) {
                String person = tietPerson.getText().toString();
                String location = spnLocation.getSelectedItem().toString();
                String type = rgType.getCheckedRadioButtonId() == R.id.timofte_serban_add_rb_nunta ? "Nunta" :
                        rgType.getCheckedRadioButtonId() == R.id.timofte_serban_add_rb_botez ? "Botez" : "Majorat";

                Event userEvent = new Event(person, location, type);
                intent.putExtra(EVENT_KEY, userEvent);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    private boolean isValid() {
        if (tietPerson.getText().toString().trim().isEmpty() || tietPerson.getText().toString().trim().length() < 3) {
            Toast.makeText(this, "Invalid Name", Toast.LENGTH_SHORT).show();
            return  false;
        }

        return true;
    }
}