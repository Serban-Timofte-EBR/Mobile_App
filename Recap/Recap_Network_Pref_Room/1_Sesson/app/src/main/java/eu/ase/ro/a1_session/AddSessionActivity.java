package eu.ase.ro.a1_session;

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

import eu.ase.ro.a1_session.model.DateConvertor;
import eu.ase.ro.a1_session.model.Session;

public class AddSessionActivity extends AppCompatActivity {

    public static final String ADD_SESSION_KEY = "add_session_key";
    private TextInputEditText tietTitle;
    private TextInputEditText tietDate;
    private TextInputEditText tietSpeaker;
    private TextInputEditText tietDuration;
    private Spinner spnRoom;
    private Button btnSave;

    private Long sessionUpdateID = 0L;

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_session);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        intent = getIntent();
        initComponent();
    }

    private void initComponent() {
        tietTitle = findViewById(R.id.tiet_add_title);
        tietDate = findViewById(R.id.tiet_add_date);
        tietSpeaker = findViewById(R.id.tiet_add_speaker);
        tietDuration = findViewById(R.id.tiet_add_duration);
        spnRoom = findViewById(R.id.spn_add_room);
        btnSave = findViewById(R.id.btn_add_save);

        if (intent.getSerializableExtra(MainActivity.MAIN_SESSION_KEY) != null) {
            Session sessionToUpdate = (Session) intent.getSerializableExtra(MainActivity.MAIN_SESSION_KEY);
            sessionUpdateID = sessionToUpdate.getId();
            tietTitle.setText(sessionToUpdate.getTitle());
            tietDate.setText(DateConvertor.fromDate(sessionToUpdate.getDate()));
            tietSpeaker.setText(sessionToUpdate.getSpeaker());
            tietDuration.setText(String.valueOf(sessionToUpdate.getDuration()));
        }
        
        btnSave.setOnClickListener(saveSession());
    }

    private View.OnClickListener saveSession() {
        return v -> {
            if (isValid()) {
                String title = tietTitle.getText().toString();
                Date date = DateConvertor.toDate(tietDate.getText().toString());
                String speaker = tietSpeaker.getText().toString();
                int duration = Integer.parseInt(tietDuration.getText().toString());
                String room = spnRoom.getSelectedItem().toString();

                Session userSession = new Session(title, date, speaker, duration, room);
                if (sessionUpdateID > 0) {
                    userSession.setId(sessionUpdateID);
                }
                intent.putExtra(ADD_SESSION_KEY, userSession);
                setResult(RESULT_OK, intent);
                finish();
            }
        };
    }

    private boolean isValid() {
        boolean valid = true;

        if (tietTitle.getText() == null || tietTitle.getText().toString().trim().isEmpty()) {
            tietTitle.setError(getString(R.string.error_title_required));
            valid = false;
        } else {
            tietTitle.setError(null);
        }

        String dateInput = tietDate.getText() != null ? tietDate.getText().toString().trim() : "";
        if (dateInput.isEmpty() || DateConvertor.toDate(dateInput) == null) {
            tietDate.setError(getString(R.string.error_invalid_date));
            valid = false;
        } else {
            tietDate.setError(null);
        }

        if (tietSpeaker.getText() == null || tietSpeaker.getText().toString().trim().isEmpty()) {
            tietSpeaker.setError(getString(R.string.error_speaker_required));
            valid = false;
        } else {
            tietSpeaker.setError(null);
        }

        String durationInput = tietDuration.getText() != null ? tietDuration.getText().toString().trim() : "";
        if (durationInput.isEmpty() || !durationInput.matches("\\d+")) {
            tietDuration.setError(getString(R.string.error_invalid_duration));
            valid = false;
        } else {
            tietDuration.setError(null);
        }

        if (spnRoom.getSelectedItem() == null || spnRoom.getSelectedItem().toString().equals(getString(R.string.spinner_default))) {
            tietTitle.requestFocus();
            showToast(getString(R.string.error_room_required));
            valid = false;
        }

        return valid;
    }

    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
}