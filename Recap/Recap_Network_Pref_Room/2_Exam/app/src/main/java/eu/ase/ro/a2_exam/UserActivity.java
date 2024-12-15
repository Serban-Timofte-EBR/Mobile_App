package eu.ase.ro.a2_exam;

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

public class UserActivity extends AppCompatActivity {
    public static final String SHARED_PREFERENCES_USER = "shared_preferences_user";
    public static final String PREFERENCES_USER_NAME = "preferences_user_name";
    private TextInputEditText tietName;
    private Button btnSave;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initComponent();
    }

    private void initComponent() {
        tietName = findViewById(R.id.add_tiet_name);
        btnSave = findViewById(R.id.add_btn_save);
        sharedPreferences = getApplicationContext().getSharedPreferences(SHARED_PREFERENCES_USER, Context.MODE_PRIVATE);
        
        loadPreferences();

        btnSave.setOnClickListener(saveToPreferences());
    }

    private View.OnClickListener saveToPreferences() {
        return view -> {
            String user_name = tietName.getText() != null
                    ? tietName.getText().toString()
                    : "";

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(PREFERENCES_USER_NAME, user_name);
            editor.apply();
        };
    }

    private void loadPreferences() {
        String name = sharedPreferences.getString(PREFERENCES_USER_NAME, "");
        tietName.setText(name);
    }
}