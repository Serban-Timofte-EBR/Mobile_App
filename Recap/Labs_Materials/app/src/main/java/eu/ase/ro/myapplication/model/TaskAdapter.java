package eu.ase.ro.myapplication.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import eu.ase.ro.myapplication.R;

public class TaskAdapter extends ArrayAdapter<Task> {

    private Context context;
    private int resource;
    private List<Task> tasks;
    private LayoutInflater inflater;

    public TaskAdapter(@NonNull Context context, int resource, @NonNull List<Task> objects, LayoutInflater inflater) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource; // ID ul layoutului, echivalent cu:
//        this.resource = R.layout.lv_row;
        this.tasks = objects;
        this.inflater = inflater;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = inflater.inflate(resource, parent, false);

        Task task = tasks.get(position);
        TextView tvDeadline = view.findViewById(R.id.row_tv_deadline);
        tvDeadline.setText(DateConverter.fromDate(task.getDeadline()));

        TextView tvUsername = view.findViewById(R.id.row_tv_username);
        tvUsername.setText(task.getUsername());

        TextView tvPrice = view.findViewById(R.id.row_tv_price);
        tvPrice.setText(context.getString(R.string.row_price_template, task.getPricePerHour()));

        TextView tvDescription = view.findViewById(R.id.row_tv_description);
        tvDescription.setText(task.getDescription());

        return view;
    }
}
