package eu.ase.ro.recapapp.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import eu.ase.ro.recapapp.R;

public class LabAdapter extends ArrayAdapter<Lab> {
    private Context context;
    private int resource;
    private List<Lab> labs;
    private LayoutInflater inflater;

    public LabAdapter(@NonNull Context context, int resource, @NonNull List<Lab> objects, LayoutInflater inflater) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.labs = objects;
        this.inflater = inflater;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = inflater.inflate(resource, parent, false);

        Lab lab = labs.get(position);

        TextView tvLabDate = view.findViewById(R.id.row_lab_date);
        tvLabDate.setText(DateConverter.fromDate(lab.getLabDate()));

        TextView tvLabName = view.findViewById(R.id.row_lab_name);
        tvLabName.setText(lab.getLabName());

        TextView tvLabClass = view.findViewById(R.id.row_class_number);
        tvLabClass.setText(lab.getClassNumber().toString());

        return view;
    }
}
