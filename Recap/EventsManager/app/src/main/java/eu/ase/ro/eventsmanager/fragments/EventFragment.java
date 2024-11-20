package eu.ase.ro.eventsmanager.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import eu.ase.ro.eventsmanager.R;
import eu.ase.ro.eventsmanager.model.Event;


public class EventFragment extends Fragment {
    private Event event;
    public static final String ARGS_EVENT_FRG_KEY = "args_event_frg_key";

    public EventFragment() {
        // Required empty public constructor
    }

    public static EventFragment getInstance(Event event) {
        EventFragment fragment = new EventFragment();
        Bundle bundle = new Bundle();

        bundle.putParcelable(ARGS_EVENT_FRG_KEY, event);

        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            event = getArguments().getParcelable(ARGS_EVENT_FRG_KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_event, container, false);

        TextView tvOwner = view.findViewById(R.id.timofte_serban_eventFRG_tv_person);
        tvOwner.setText(getString(R.string.eventFRG_owner_template, event.getContactPerson()));

        TextView tvLocation = view.findViewById(R.id.timofte_serban_eventFRG_tv_location);
        tvLocation.setText(getString(R.string.eventFRG_location_template, event.getLocation()));

        return view;
    }
}