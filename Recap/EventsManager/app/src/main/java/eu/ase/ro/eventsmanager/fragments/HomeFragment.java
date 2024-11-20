package eu.ase.ro.eventsmanager.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import eu.ase.ro.eventsmanager.R;
import eu.ase.ro.eventsmanager.model.Event;
import eu.ase.ro.eventsmanager.model.EventAdapter;
import eu.ase.ro.eventsmanager.model.HttpManager;

public class HomeFragment extends Fragment {
    public static final String ARGS_EVENT_KEY = "args_event_key";
    private List<Event> events;
    private ListView lvEvents;

    private final String URL = "https://api.npoint.io/48d5c3939a2ef72b8835";
    private ExecutorService executor = Executors.newCachedThreadPool();
    private Handler handler = new Handler(Looper.getMainLooper());

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment getInstance(List<Event> list) {
        HomeFragment fragment = new HomeFragment();
        Bundle bundle = new Bundle();

        bundle.putParcelableArrayList(ARGS_EVENT_KEY, (ArrayList<? extends Parcelable>) list);

        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            events = getArguments().getParcelableArrayList(ARGS_EVENT_KEY);
        }
    }

    private List<Event> parseResult(String result) {
        try {
            List<Event> list = new ArrayList<>();
            JSONArray jsonArray = new JSONArray(result);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                String person = jsonObject.getString("contactPerson");
                String location = jsonObject.getString("location");
                String type = jsonObject.getString("eventType");

                list.add(new Event(person, location, type));
            }

            return list;
        } catch (JSONException e) {
            return null;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        if (getContext() != null) {
            lvEvents = view.findViewById(R.id.timofte_serban_home_lv_events);

            EventAdapter adapter = new EventAdapter(getContext(),
                    R.layout.row_lv_home,
                    events,
                    getLayoutInflater());
            lvEvents.setAdapter(adapter);

            lvEvents.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Event event = events.get(position);

                    EventFragment eventFragment = EventFragment.getInstance(event);
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.timofte_serban_main_fl, eventFragment).commit();
                }
            });

            if (events == null || events.isEmpty()) {
                executor.execute(() -> {
                    HttpManager httpManager = new HttpManager(URL);
                    String result = httpManager.getCall();

                    if (result != null) {
                        List<Event> parsedList = parseResult(result);

                        if (parsedList != null) {
                            handler.post(() -> {
                                events.addAll(parsedList);
                                notifyData();
                            });
                        }
                    }
                });
            }

        }

        return view;
    }

    public void notifyData() {
        EventAdapter adapter = (EventAdapter) lvEvents.getAdapter();
        adapter.notifyDataSetChanged();
    }
}