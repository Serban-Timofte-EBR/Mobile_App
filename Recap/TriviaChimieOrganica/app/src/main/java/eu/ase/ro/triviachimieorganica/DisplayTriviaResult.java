package eu.ase.ro.triviachimieorganica;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

import eu.ase.ro.triviachimieorganica.fragments.TriviaFragment;
import eu.ase.ro.triviachimieorganica.models.Question;
import eu.ase.ro.triviachimieorganica.models.QuestionAdapter;

public class DisplayTriviaResult extends AppCompatActivity {
    public static final String RESET_FRAGMENT = "RESET_FRAGMENT";

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    private TextView tvScore;
    private ListView lvQuestions;
    private Button btnBack;

    private Intent intent;

    private Integer score;
    private List<Question> questions = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_display_trivia_result);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initComponent();
    }

    private void initComponent() {
        tvScore = findViewById(R.id.timofte_serban_result_tv_score);
        lvQuestions = findViewById(R.id.timofte_serban_result_lv_questions);
        btnBack = findViewById(R.id.timofte_serban_result_btn_back);

        intent = getIntent();
        score = intent.getIntExtra(TriviaFragment.TRIVIA_SCORE, 0);
        questions = intent.getParcelableArrayListExtra(TriviaFragment.TRIVIA_QUESTIONS);

        QuestionAdapter adapter = new QuestionAdapter(getApplicationContext(),
                R.layout.display_result_lv_row,
                questions,
                getLayoutInflater());
        lvQuestions.setAdapter(adapter);

        tvScore.setText(getString(R.string.display_score_template, score));

        btnBack.setOnClickListener(v -> {
            setResult(RESULT_OK, intent);
            finish();
        });
    }

}