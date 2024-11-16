package eu.ase.ro.triviachimieorganica.fragments;

import android.os.Bundle;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

import eu.ase.ro.triviachimieorganica.R;
import eu.ase.ro.triviachimieorganica.models.Question;

public class TriviaFragment extends Fragment {
    private List<Question> questions = new ArrayList<>();
    private int indexCurrentQuestion = 0;

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

    public TriviaFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loadQuestions();
        Log.i("TriviaFragment", questions.toString());
        Log.i("TriviaFragment", String.valueOf(questions.size()));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trivia, container, false);

        initComponent(view);
        displayQuestion();

        btnNext.setOnClickListener(v -> {
            if(indexCurrentQuestion == questions.size() - 1) {
                Toast.makeText(getContext(), "The result is comming soon on a new page!", Toast.LENGTH_SHORT).show();
            } else {
                indexCurrentQuestion++;
                displayQuestion();
                Toast.makeText(getContext(), "A new question ahead!", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    private void loadQuestions() {
        questions = new ArrayList<>();

        questions.add(new Question("Care este formula structurală a metanului?",
                "CH4",
                "C2H6",
                "C3H8",
                Question.TYPE_RADIO,
                "CH4"));

        questions.add(new Question("Ce element este esențial în chimia organică?",
                "Oxigen",
                "Carbon",
                "Hidrogen",
                Question.TYPE_RADIO,
                "Carbon"));

        questions.add(new Question("Selectați toți compușii organici:",
                "CH4",
                "H2O",
                "C6H12O6",
                Question.TYPE_CHECKBOX,
                "CH4,C6H12O6"));

        questions.add(new Question("Care dintre următoarele sunt hidrocarburi?",
                "Etan",
                "Metan",
                "Dioxid de carbon",
                Question.TYPE_CHECKBOX,
                "Etan,Metan"));

        questions.add(new Question("Ce tip de legătură există în alcani?",
                "Simplă",
                "Dublă",
                "Triplă",
                Question.TYPE_SPINNER,
                "Simplă" ));

        questions.add(new Question("Care este cel mai simplu alcan?",
                "Etan",
                "Metan",
                "Propan",
                Question.TYPE_SPINNER,
                "Metan"));

        questions.add(new Question("Scrieți formula moleculară a etanolului:",
                null,
                null,
                null,
                Question.TYPE_TEXT,
                "C2H6O"));

        questions.add(new Question("Care este grupa funcțională a alcoolilor?",
                null,
                null,
                null,
                Question.TYPE_TEXT,
                "OH"));

        questions.add(new Question("Ce compus este utilizat drept combustibil organic?",
                "Metan",
                "Etanol",
                "Oxigen",
                Question.TYPE_RADIO,
                "Metan"));

        questions.add(new Question("Care este produsul principal al fotosintezei?",
                "Glucoză",
                "Oxigen",
                "Apă",
                Question.TYPE_SPINNER,
                "Glucoză"));
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
}