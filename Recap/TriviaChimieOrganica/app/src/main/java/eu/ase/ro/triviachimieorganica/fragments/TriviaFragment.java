package eu.ase.ro.triviachimieorganica.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import eu.ase.ro.triviachimieorganica.DisplayTriviaResult;
import eu.ase.ro.triviachimieorganica.MainActivity;
import eu.ase.ro.triviachimieorganica.R;
import eu.ase.ro.triviachimieorganica.models.Question;
import eu.ase.ro.triviachimieorganica.models.Result;
import eu.ase.ro.triviachimieorganica.network.HttpManager;

public class TriviaFragment extends Fragment {
    public static final String TRIVIA_SCORE = "trivia_score";
    public static final String TRIVIA_QUESTIONS = "trivia_questions";

    private final String URL = "https://api.npoint.io/cc5a9c7874a009746d52";

    private List<Question> questions = new ArrayList<>();
    private int indexCurrentQuestion = 0;
    private int score = 0;

    private TextView tvQuestion;

    private RadioGroup rgOptions;
    private RadioButton rbOption1;
    private RadioButton rbOption2;
    private RadioButton rbOption3;

    private Spinner spnOptions;

    private CheckBox cbOption1;
    private CheckBox cbOption2;
    private CheckBox cbOption3;

    private TextInputLayout tilOptions;
    private TextInputEditText tietOption;

    private Button btnNext;

    private ActivityResultLauncher<Intent> launcher;
    private Intent intent;

    private List<Result> results = new ArrayList<>();

    private ExecutorService executor = Executors.newCachedThreadPool();
    private Handler handler = new Handler(Looper.getMainLooper());

    public TriviaFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), getCallback());

        loadQuestions();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trivia, container, false);

        initComponent(view);
        displayQuestion();

        Log.i("TriviaFragment", results.toString());

        btnNext.setOnClickListener(v -> {
            if (!isAnswerSelected()) {
                Toast.makeText(getContext(), "Select an answer before moving forward!", Toast.LENGTH_SHORT).show();
                return;
            }
            manageScore(view);

            if(indexCurrentQuestion == questions.size() - 1) {
                Toast.makeText(getContext(), "The result is comming soon on a new page!", Toast.LENGTH_SHORT).show();

                Result result = new Result(new Date(), score);
                ((MainActivity) getActivity()).addResult(result);

                intent = new Intent(getContext().getApplicationContext(), DisplayTriviaResult.class);
                intent.putExtra(TRIVIA_SCORE, score);
                intent.putParcelableArrayListExtra(TRIVIA_QUESTIONS, (ArrayList<? extends Parcelable>) questions);
                launcher.launch(intent);
            } else {
                indexCurrentQuestion++;
                displayQuestion();
            }
        });

        return view;
    }

    private boolean isAnswerSelected() {
        Question currentQuestion = questions.get(indexCurrentQuestion);

        switch (currentQuestion.getType()) {
            case Question.TYPE_RADIO:
                return rgOptions.getCheckedRadioButtonId() != -1;

            case Question.TYPE_CHECKBOX:
                return cbOption1.isChecked() || cbOption2.isChecked() || cbOption3.isChecked();

            case Question.TYPE_SPINNER:
                return spnOptions.getSelectedItemPosition() != -1;

            case Question.TYPE_TEXT:
                String userAnswer = tietOption.getText() != null ? tietOption.getText().toString() : "";
                return !userAnswer.trim().isEmpty();

            default:
                return false;
        }
    }

    private void loadQuestions() {
        executor.execute(() -> {
            HttpManager manager = new HttpManager(URL);
            String result = manager.getCall();

            if (result != null) {
                List<Question> parsedQuestions = parseResponse(result);
                handler.post(() -> {
                    questions.clear();
                    questions.addAll(parsedQuestions);
                    indexCurrentQuestion = 0;
                    score = 0;
                    displayQuestion();
                });
            }
        });
    }

    private List<Question> parseResponse(String result) {
        try {
            List<Question> parsedQuestions = new ArrayList<>();
            JSONArray jsonArray = new JSONArray(result);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                String question = jsonObject.getString("question");
                String option1 = jsonObject.getString("option1");
                String option2 = jsonObject.getString("option2");
                String option3 = jsonObject.getString("option3");
                int type = jsonObject.getInt("type");
                String correctAnswer = jsonObject.getString("correctAnswer");

                parsedQuestions.add(new Question(question, option1, option2, option3, type, correctAnswer));
            }

            return parsedQuestions;
        } catch (JSONException e) {
            return null;
        }
    }

    private void initComponent(View view) {
        tvQuestion = view.findViewById(R.id.timofte_serban_trivia_tv_question);

        rgOptions = view.findViewById(R.id.timofte_serban_trivia_rg_options);
        rbOption1 = view.findViewById(R.id.timofte_serban_trivia_rb_option1);
        rbOption2 = view.findViewById(R.id.timofte_serban_trivia_rb_option2);
        rbOption3 = view.findViewById(R.id.timofte_serban_trivia_rb_option3);

        spnOptions = view.findViewById(R.id.timofte_serban_trivia_spn_options);
        cbOption1 = view.findViewById(R.id.timofte_serban_trivia_cb_option1);
        cbOption2 = view.findViewById(R.id.timofte_serban_trivia_cb_option2);
        cbOption3 = view.findViewById(R.id.timofte_serban_trivia_cb_option3);

        tilOptions = view.findViewById(R.id.timofte_serban_trivia_til_options);
        tietOption = view.findViewById(R.id.timofte_serban_tiet_option);

        btnNext = view.findViewById(R.id.timofte_serban_trivia_btn_next);
    }

    private void displayQuestion() {
        if (questions == null || questions.size() == 0) {
            Toast.makeText(getContext(), "NO QUESTION", Toast.LENGTH_SHORT).show();
            return;
        }
        Question currentQuestion = questions.get(indexCurrentQuestion);

        tvQuestion.setText(currentQuestion.getQuestion());

        switch (currentQuestion.getType()) {
            case Question.TYPE_RADIO:
                rgOptions.setVisibility(View.VISIBLE);

                cbOption1.setVisibility(View.GONE);
                cbOption2.setVisibility(View.GONE);
                cbOption3.setVisibility(View.GONE);
                spnOptions.setVisibility(View.GONE);
                tilOptions.setVisibility(View.GONE);

                rbOption1.setText(currentQuestion.getOption1());
                rbOption2.setText(currentQuestion.getOption2());
                rbOption3.setText(currentQuestion.getOption3());

                break;

            case Question.TYPE_CHECKBOX:
                cbOption1.setVisibility(View.VISIBLE);
                cbOption2.setVisibility(View.VISIBLE);
                cbOption3.setVisibility(View.VISIBLE);

                rgOptions.setVisibility(View.GONE);
                spnOptions.setVisibility(View.GONE);
                tilOptions.setVisibility(View.GONE);

                cbOption1.setChecked(false);
                cbOption2.setChecked(false);
                cbOption3.setChecked(false);

                cbOption1.setText(currentQuestion.getOption1());
                cbOption2.setText(currentQuestion.getOption2());
                cbOption3.setText(currentQuestion.getOption3());

                break;

            case Question.TYPE_SPINNER:
                spnOptions.setVisibility(View.VISIBLE);

                cbOption1.setVisibility(View.GONE);
                cbOption2.setVisibility(View.GONE);
                cbOption3.setVisibility(View.GONE);
                rgOptions.setVisibility(View.GONE);
                tilOptions.setVisibility(View.GONE);

                List<String> options = new ArrayList<>();
                options.add(currentQuestion.getOption1());
                options.add(currentQuestion.getOption2());
                options.add(currentQuestion.getOption3());

                ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),
                        android.R.layout.simple_list_item_1,
                        options);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spnOptions.setAdapter(adapter);

                break;

            case Question.TYPE_TEXT:
                tilOptions.setVisibility(View.VISIBLE);

                cbOption1.setVisibility(View.GONE);
                cbOption2.setVisibility(View.GONE);
                cbOption3.setVisibility(View.GONE);
                rgOptions.setVisibility(View.GONE);
                spnOptions.setVisibility(View.GONE);

                tietOption.setText("");

                break;
        }
    }

    private void manageScore(View view) {
        Question currentQuestion = questions.get(indexCurrentQuestion);

        switch (currentQuestion.getType()) {
            case Question.TYPE_RADIO:
                int selectedRbID = rgOptions.getCheckedRadioButtonId();
                RadioButton selectedRb = view.findViewById(selectedRbID);
                if (selectedRb.getText().equals(currentQuestion.getCorrectAnswer())) {
                    score += 4;
                } else if (score > 0) {
                    score -= 1;
                }
                break;

            case Question.TYPE_CHECKBOX:
                List<String> selectedAnswers = new ArrayList<>();

                if (cbOption1.isSelected()) {
                    selectedAnswers.add(cbOption1.getText().toString());
                }

                if (cbOption2.isSelected()) {
                    selectedAnswers.add(cbOption2.getText().toString());
                }

                if (cbOption3.isSelected()) {
                    selectedAnswers.add(cbOption3.getText().toString());
                }

                String concatedAnswers = String.join(", ", selectedAnswers);

                if (currentQuestion.getCorrectAnswer().equals(concatedAnswers)) {
                    score += 8;
                } else if (score > 0){
                    score -= 1;
                }
                break;

            case Question.TYPE_SPINNER:
                String selectedAnswer = spnOptions.getSelectedItem().toString();
                if (selectedAnswer.equals(currentQuestion.getCorrectAnswer())) {
                    score += 4;
                } else if (score > 0) {
                    score -= 1;
                }
                break;

            case Question.TYPE_TEXT:
                String userAnswer = tietOption.getText().toString();
                if (userAnswer.toLowerCase().equals(currentQuestion.getCorrectAnswer().toLowerCase())) {
                    score += 12;
                } else if (score > 0) {
                    score -= 1;
                }
                break;
        }
    }

    private ActivityResultCallback<ActivityResult> getCallback() {
        return result -> {
            if (result.getResultCode() == AppCompatActivity.RESULT_OK) {
                indexCurrentQuestion = 0;
                score = 0;
                loadQuestions();
                displayQuestion();
            }
        };
    }
}