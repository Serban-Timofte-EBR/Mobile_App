package eu.ase.ro.a3_event;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

import eu.ase.ro.a3_event.database.EventService;
import eu.ase.ro.a3_event.model.Event;
import eu.ase.ro.a3_event.network.AsyncTaskRunner;
import eu.ase.ro.a3_event.network.Callback;
import eu.ase.ro.a3_event.network.EventParser;
import eu.ase.ro.a3_event.network.HttpManager;

public class MainActivity extends AppCompatActivity {
    private final String NPOIN_URL = "https://api.npoint.io/c0712800d0fda7ca8577";

    private Spinner spnNrPersons;
    private ListView lvEvents;
    private FloatingActionButton fabAdd;
    private Button getData;

    private List<Event> events = new ArrayList<>();
    private ActivityResultLauncher<Intent> launcher;
    private AsyncTaskRunner asyncTaskRunner = new AsyncTaskRunner();

    private EventService eventService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), getAddCallback());
        initComponent();

        eventService = new EventService(getApplicationContext());
        eventService.getAll(getAllCallback());
    }

    private void initComponent() {
        spnNrPersons = findViewById(R.id.main_spn_nrPersons);
        lvEvents = findViewById(R.id.main_lv_events);
        fabAdd = findViewById(R.id.main_fab_add);
        getData = findViewById(R.id.main_btn_getData);

        ArrayAdapter<Event> adapter = new ArrayAdapter<>(
                getApplicationContext(),
                android.R.layout.simple_list_item_1,
                events
        );
        lvEvents.setAdapter(adapter);

        lvEvents.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Event selectedEvent = events.get(position);
                Toast.makeText(getApplicationContext(), selectedEvent.getNrOfPersons().toString(), Toast.LENGTH_SHORT).show();
            }
        });

        spnNrPersons.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String nrPers_STR = spnNrPersons.getItemAtPosition(position).toString();

                if (!nrPers_STR.equals("All")) {
                    Integer nrPers = Integer.parseInt(nrPers_STR.trim());
                    Log.i("FilterDebug", "Selected nr persons: " + nrPers);


                    List<Event> filteredList = events.stream().filter(event -> event.getNrOfPersons() == nrPers).collect(Collectors.toList());
                    ArrayAdapter<Event> filteredAdapter = new ArrayAdapter<>(
                            getApplicationContext(),
                            android.R.layout.simple_list_item_1,
                            filteredList
                    );
                    lvEvents.setAdapter(filteredAdapter);
                    Log.i("FilterDebug", "Filtered list size: " + filteredList.size());
                } else {
                    ArrayAdapter<Event> adapter = new ArrayAdapter<>(
                            getApplicationContext(),
                            android.R.layout.simple_list_item_1,
                            events
                    );
                    lvEvents.setAdapter(adapter);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        fabAdd.setOnClickListener(launchAddExamActivity());
        getData.setOnClickListener(getDataFromURL());
    }

    private View.OnClickListener getDataFromURL() {
        return view -> {
            Callable<String> callable = new HttpManager(NPOIN_URL);
            Callback<String> callback = getHttpCallback();
            asyncTaskRunner.executeAsync(callable, callback);
        };
    }

    private Callback<String> getHttpCallback() {
        return result -> {
            if (result != null) {
                List<Event> jsonResponse = EventParser.fromJSON(result);

                for (Event event : jsonResponse) {
                    if (!events.contains(event)) {
                        eventService.insert(event, getInsertCallback());
                    }
                }
            }
        };
    }

    private ActivityResultCallback<ActivityResult> getAddCallback() {
        return result -> {
            if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                if (result.getData().getSerializableExtra(AddActivity.ADD_EVENT_KEY) != null) {
                    Event event = (Event) result.getData().getSerializableExtra(AddActivity.ADD_EVENT_KEY);
                    eventService.insert(event, getInsertCallback());
                    events.add(event);
                    notifyAdapter();
                }
            }
        };
    }

    private View.OnClickListener launchAddExamActivity() {
        return view -> {
            Intent intent = new Intent(getApplicationContext(), AddActivity.class);
            launcher.launch(intent);
        };
    }

    private void notifyAdapter() {
        ArrayAdapter<Event> adapter = (ArrayAdapter<Event>) lvEvents.getAdapter();
        adapter.notifyDataSetChanged();
        setUpAdapter();
    }

    private void setUpAdapter() {
        Set<String> spinnerUniqueValues = new HashSet<>();
        spinnerUniqueValues.add("All");
        for (Event event : events) {
            spinnerUniqueValues.add(event.getNrOfPersons().toString());
        }

        List<String> spinnerValues = new ArrayList<>(spinnerUniqueValues);
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(
                getApplicationContext(),
                android.R.layout.simple_spinner_dropdown_item,
                spinnerValues
        );
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spnNrPersons.setAdapter(spinnerAdapter);
    }

    private Callback<List<Event>> getAllCallback() {
        return result -> {
            if (result != null) {
                events.addAll(result);
                notifyAdapter();
            }
        };
    }

    private Callback<Event> getInsertCallback() {
        return result -> {
            if (result != null) {
                events.clear();
                eventService.getAll(getAllCallback());
            }
        };
    }
}