package eu.ase.ro.a3_event;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Date;

import eu.ase.ro.a3_event.model.DateConverter;
import eu.ase.ro.a3_event.model.Event;

public class AddActivity extends AppCompatActivity {
    public static final String ADD_EVENT_KEY = "add_event_key";
    private TextInputEditText tietTitle;
    private TextInputEditText tietDate;
    private TextInputEditText tietNrPersons;
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
        tietTitle = findViewById(R.id.tiet_event_title);
        tietDate = findViewById(R.id.tiet_event_date);
        tietNrPersons = findViewById(R.id.tiet_event_persons);
        btnSave = findViewById(R.id.btn_event_save);

        btnSave.setOnClickListener(saveUserExam());
    }

    private View.OnClickListener saveUserExam() {
        return view -> {
            if (isValid()) {
                Event userEvent = generateEventFromUI();
                intent.putExtra(ADD_EVENT_KEY, userEvent);
                setResult(RESULT_OK, intent);
                finish();
            }
        };
    }

    private boolean isValid() {
        if (tietTitle.getText().toString().isBlank()) {
            Toast.makeText(getApplicationContext(), "Title is invalid", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (tietDate.getText().toString().isBlank() || DateConverter.toDate(tietDate.getText().toString()) == null) {
            Toast.makeText(getApplicationContext(), "Date is invalid", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (tietNrPersons.getText().toString().isBlank() || Integer.parseInt(tietNrPersons.getText().toString()) < 0) {
            Toast.makeText(getApplicationContext(), "Number of persons is invalid", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private Event generateEventFromUI() {
        Date date = DateConverter.toDate(tietDate.getText().toString());
        String title = tietTitle.getText().toString();
        Integer nrPersons = Integer.parseInt(tietNrPersons.getText().toString());

        return new Event(date, title, nrPersons);
    }
}