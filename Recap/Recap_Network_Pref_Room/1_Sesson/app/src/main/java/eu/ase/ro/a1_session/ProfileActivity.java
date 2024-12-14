package eu.ase.ro.a1_session;

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

public class ProfileActivity extends AppCompatActivity {
    public static final String PROFILE_PREFERENCE = "profile_preference";
    public static final String NAME_KEY = "name_key";
    public static final String USERNAME_KEY = "username_key";
    public static final String EMAIL_KEY = "email_key";

    private TextInputEditText tietName;
    private TextInputEditText tietUsername;
    private TextInputEditText tietEmail;
    private Button btnSave;
    
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        
        initComponent();
    }

    private void initComponent() {
        tietName = findViewById(R.id.tiet_profile_name);
        tietUsername = findViewById(R.id.tiet_profile_username);
        tietEmail = findViewById(R.id.tiet_profile_email);
        btnSave = findViewById(R.id.btn_profile_save);
        sharedPreferences = getApplicationContext().getSharedPreferences(PROFILE_PREFERENCE, Context.MODE_PRIVATE);
        
        loadPreferences();
        
        btnSave.setOnClickListener(saveProfilePreferences());
    }

    private void loadPreferences() {
        String name = sharedPreferences.getString(NAME_KEY, "");
        String username = sharedPreferences.getString(USERNAME_KEY, "");
        String email = sharedPreferences.getString(EMAIL_KEY, "");

        tietName.setText(name);
        tietUsername.setText(username);
        tietEmail.setText(email);
    }

    private View.OnClickListener saveProfilePreferences() {
        return v -> {
            String name = tietName.getText() != null
                    ? tietName.getText().toString()
                    : "";
            String username = tietUsername.getText() != null
                    ? tietUsername.getText().toString()
                    : "";
            String email = tietEmail.getText() != null
                    ? tietEmail.getText().toString()
                    : "";

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(NAME_KEY, name);
            editor.putString(USERNAME_KEY, username);
            editor.putString(EMAIL_KEY, email);
            editor.apply();
        };
    }
}