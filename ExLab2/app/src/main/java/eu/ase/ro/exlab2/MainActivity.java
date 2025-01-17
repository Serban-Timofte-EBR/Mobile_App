package eu.ase.ro.exlab2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final String PREF_FILE_NAME = "timofte_serban_preferences";
    private static final String PREF_USERNAME_KEY = "username";

    private TextView tvGreeting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvGreeting = findViewById(R.id.timofte_serban_main_tv_greeting);

        findViewById(R.id.timofte_serban_main_btn_about).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AboutActivity.class);
                startActivity(intent);
            }
        });

        displayGreeting();
    }

    private void displayGreeting() {
        SharedPreferences sharedPreferences = getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
        String username = sharedPreferences.getString(PREF_USERNAME_KEY, null);

        if (username != null && !username.trim().isEmpty()) {
            String greeting = getString(R.string.greeting_template, username);
            tvGreeting.setText(greeting);
        } else {
            tvGreeting.setText(R.string.greeting_default);
        }
    }
}