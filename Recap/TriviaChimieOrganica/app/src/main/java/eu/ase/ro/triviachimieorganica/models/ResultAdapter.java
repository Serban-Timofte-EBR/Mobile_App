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

public class ResultAdapter extends ArrayAdapter<Result> {
    private Context context;
    private int resource;
    private List<Result> results;
    private LayoutInflater inflater;

    public ResultAdapter(@NonNull Context context, int resource, @NonNull List<Result> objects, LayoutInflater inflater) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.results = objects;
        this.inflater = inflater;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = inflater.inflate(resource, parent, false);

        Result result = results.get(position);

        TextView tvDate = view.findViewById(R.id.timofte_serban_row_tv_date);
        tvDate.setText(DateConverter.fromDate(result.getTriviaDate()));

        TextView tvScore = view.findViewById(R.id.timofte_serban_row_tv_result);
        tvScore.setText(result.getScore().toString());

        return view;
    }
}
