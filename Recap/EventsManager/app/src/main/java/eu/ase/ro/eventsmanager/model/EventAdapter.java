package eu.ase.ro.eventsmanager.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import eu.ase.ro.eventsmanager.R;

public class EventAdapter extends ArrayAdapter<Event> {
    private Context context;
    private int resource;
    private List<Event> events;
    private LayoutInflater inflater;

    public EventAdapter(@NonNull Context context, int resource, @NonNull List<Event> objects, LayoutInflater inflater) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.events = objects;
        this.inflater = inflater;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = inflater.inflate(resource, parent, false);

        Event event = events.get(position);
        TextView tvLocation = view.findViewById(R.id.timofte_serban_row_home_location);
        tvLocation.setText(event.getLocation());

        TextView tvPerson = view.findViewById(R.id.timofte_serban_row_home_persom);
        tvPerson.setText(event.getContactPerson());

        TextView tvtype = view.findViewById(R.id.timofte_serban_row_home_type);
        tvtype.setText(event.getEventType());

        return view;
    }
}
