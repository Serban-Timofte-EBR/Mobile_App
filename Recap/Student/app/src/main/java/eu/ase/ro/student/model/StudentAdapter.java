package eu.ase.ro.student.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import eu.ase.ro.student.R;

public class StudentAdapter extends ArrayAdapter<Student> {
    private Context context;
    private int resource;
    private List<Student> students;
    private LayoutInflater inflater;

    public StudentAdapter(@NonNull Context context, int resource, @NonNull List<Student> objects, LayoutInflater inflater) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.students = objects;
        this.inflater = inflater;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = inflater.inflate(resource, parent, false);

        Student student = students.get(position);
        TextView tvName = view.findViewById(R.id.timofte_serban_tow_tv_name);
        tvName.setText(student.getName());

        TextView tvAge = view.findViewById(R.id.timofte_serban_tow_tv_age);
        tvAge.setText(student.getAge().toString());

        TextView tvFaculty = view.findViewById(R.id.timofte_serban_tow_tv_faculty);
        tvFaculty.setText(student.getFaculty().toString());

        TextView tvFrequency = view.findViewById(R.id.timofte_serban_tow_tv_frequency);
        tvFrequency.setText(student.getFrequency().toString());

        return view;
    }
}
