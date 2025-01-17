package eu.ase.ro.exlab2;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AboutActivity extends AppCompatActivity {

    private static final String PREF_FILE_NAME = "timofte_serban_preferences";
    private static final String PREF_USERNAME_KEY = "username";
    private static final String PREF_RATING_KEY = "rating";

    private EditText etUsername;
    private RatingBar rbRating;
    private Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_about);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initComponents();
        loadData();
    }

    private void initComponents() {
        etUsername = findViewById(R.id.timofte_serban_about_et_username);
        rbRating = findViewById(R.id.timofte_serban_about_rb_rating);
        btnSave = findViewById(R.id.timofte_serban_about_btn_save);

        btnSave.setOnClickListener(v -> saveData());
    }

    private void saveData() {
        String username = etUsername.getText().toString().trim();
        float rating = rbRating.getRating();

        if (username.isEmpty()) {
            Toast.makeText(this, "Username cannot be empty!", Toast.LENGTH_SHORT).show();
            return;
        }

        SharedPreferences sharedPreferences = getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(PREF_USERNAME_KEY, username);
        editor.putFloat(PREF_RATING_KEY, rating);
        editor.apply();

        Toast.makeText(this, "Data saved successfully!", Toast.LENGTH_SHORT).show();
    }

    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);

        String username = sharedPreferences.getString(PREF_USERNAME_KEY, "");
        float rating = sharedPreferences.getFloat(PREF_RATING_KEY, 0F);

        etUsername.setText(username);
        rbRating.setRating(rating);
    }
}