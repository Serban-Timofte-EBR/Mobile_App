package eu.ase.ro.a4_pacient;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;

public class HospitalActivity extends AppCompatActivity {
    public static final String HOSPITAL_PREFERENCES = "hospital_preferences";
    public static final String PREF_HOSPITAL_NAME = "pref_hospital_name";
    private TextInputEditText tietHospitalName;
    private Button btnSave;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_hospital);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initComponent();
    }

    private void initComponent() {
        tietHospitalName = findViewById(R.id.hospital_tiet_name);
        btnSave = findViewById(R.id.hospital_btn_save);
        sharedPreferences = getApplicationContext().getSharedPreferences(HOSPITAL_PREFERENCES, Context.MODE_PRIVATE);

        loadPreferences();

        btnSave.setOnClickListener(savePrefecences());
    }

    private void loadPreferences() {
        String hospitalName = sharedPreferences.getString(PREF_HOSPITAL_NAME, "");
        tietHospitalName.setText(hospitalName);
    }

    private View.OnClickListener savePrefecences() {
        return view -> {
            SharedPreferences.Editor editor = sharedPreferences.edit();

            String hospitalName = tietHospitalName.getText().toString().trim().length() >= 3
                    ? tietHospitalName.getText().toString()
                    : "";

            editor.putString(PREF_HOSPITAL_NAME, hospitalName);
            editor.apply();
            finish();
        };
    }
}