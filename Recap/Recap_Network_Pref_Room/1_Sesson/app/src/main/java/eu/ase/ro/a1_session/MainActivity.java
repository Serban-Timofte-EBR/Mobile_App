package eu.ase.ro.a1_session;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;

import eu.ase.ro.a1_session.model.Session;
import eu.ase.ro.a1_session.network.AsyncTaskRunner;
import eu.ase.ro.a1_session.network.Callback;
import eu.ase.ro.a1_session.network.HttpManager;
import eu.ase.ro.a1_session.network.SessionParser;

public class MainActivity extends AppCompatActivity {
    public static final String SPINNER_SELECTION_SHARED = "spinner_selection_shared";
    private final String NPOINT_URL = "https://api.npoint.io/0b4e456e680e358ed1ba";

    private Spinner spnFilter;
    private ListView lvSessions;
    private Button btnGetData;
    private Button btnProfile;
    private FloatingActionButton fabAddSession;

    private AsyncTaskRunner asyncTaskRunner = new AsyncTaskRunner();
    private List<Session> sessions = new ArrayList<>();

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

        initComponent();
    }

    private void initComponent() {
        spnFilter = findViewById(R.id.main_spn_filter);
        lvSessions = findViewById(R.id.main_lv_sessions);
        btnGetData = findViewById(R.id.main_btn_getData);
        btnProfile = findViewById(R.id.main_btn_profile);

        spnFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedSpeaker = spnFilter.getSelectedItem().toString();
                filterSessionsBySpeaker(selectedSpeaker);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        setLvSessionsAdapter();

        btnGetData.setOnClickListener(getNPointSessions());
        btnProfile.setOnClickListener(startProfileAcitivity());
    }

    private View.OnClickListener startProfileAcitivity() {
        return v -> {
            Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
            startActivity(intent);
        };
    }

    private void filterSessionsBySpeaker(String selectedSpeaker) {
        List<Session> result = new ArrayList<>();

        if (selectedSpeaker.equals("All")) {
            result.addAll(sessions);
        } else {
            for(Session session : sessions) {
                if (session.getSpeaker().equals(selectedSpeaker)) {
                    result.add(session);
                }
            }
        }

        ArrayAdapter<Session> adapterFilter = new ArrayAdapter<>(
                getApplicationContext(),
                android.R.layout.simple_list_item_1,
                result);
        lvSessions.setAdapter(adapterFilter);
    }


    private void setLvSessionsAdapter() {
        ArrayAdapter<Session> adapter = new ArrayAdapter<>(getApplicationContext(),
                android.R.layout.simple_list_item_1,
                sessions);
        lvSessions.setAdapter(adapter);
    }

    private void notifyAdapeter() {
        ArrayAdapter<Session> adapter = (ArrayAdapter<Session>) lvSessions.getAdapter();
        adapter.notifyDataSetChanged();
    }

    private View.OnClickListener getNPointSessions() {
        return v -> {
            Callable<String> callable = new HttpManager(NPOINT_URL);
            Callback<String> callback = getCallbackFromHttpManager();
            asyncTaskRunner.executeAsync(callable, callback);
        };
    }

    private Callback<String> getCallbackFromHttpManager() {
        return result -> {
            sessions.addAll(SessionParser.fromJSON(result));
            notifyAdapeter();
            setSpnValues();
        };
    }

    private void setSpnValues() {
        Set<String> uniqueSpikers = new HashSet<>();
        uniqueSpikers.add("All");
        for (Session session : sessions) {
            uniqueSpikers.add(session.getSpeaker());
        }

        List<String> speakersForSpinner = new ArrayList<>(uniqueSpikers);
        ArrayAdapter<String> spnAdapter = new ArrayAdapter<>(getApplicationContext(),
                android.R.layout.simple_spinner_item,
                speakersForSpinner);
        spnAdapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        spnFilter.setAdapter(spnAdapter);
    }
}