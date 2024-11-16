package eu.ase.ro.triviachimieorganica.models;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import eu.ase.ro.triviachimieorganica.R;

public class QuestionAdapter extends ArrayAdapter<Question> {
    private Context context;
    private int resource;
    private List<Question> questions;
    private LayoutInflater inflater;

    public QuestionAdapter(@NonNull Context context, int resource, @NonNull List<Question> objects, LayoutInflater inflater) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.questions = objects;
        this.inflater = inflater;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = inflater.inflate(resource, parent, false);

        Question question = questions.get(position);
        TextView tvQuestion = view.findViewById(R.id.timofte_serban_row_tv_question);
        TextView tvAnswer = view.findViewById(R.id.timofte_serban_row_tv_answer);

        tvQuestion.setText(question.getQuestion());
        tvAnswer.setText(question.getCorrectAnswer());

        return view;
    }
}
