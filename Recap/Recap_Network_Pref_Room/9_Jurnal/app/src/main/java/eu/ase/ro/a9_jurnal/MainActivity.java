package eu.ase.ro.a9_jurnal;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity {
    public static final String JURNALS_PREFERENCES = "jurnals_preferences";
    public static final String JURNAL_EXPENSE = "jurnal_expense";
    public static final String JURNAL_INDEX = "jurnal_index";
    private final String NPOINT_URL = "https://api.npoint.io/efcea1d46c36fa555d04";

    private List<Jurnal> jurnals = new ArrayList<>();

    private FloatingActionButton fabSync;
    private Spinner spnComp;
    private TextInputEditText tietExpenses;
    private Button btnDelete;

    private AsyncTaskRunner asyncTaskRunner = new AsyncTaskRunner();
    private JurnalService jurnalService;

    private SharedPreferences sharedPreferences;

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
        jurnalService = new JurnalService(getApplicationContext());
        initComponent();

        jurnalService.getAll(getAllCallback());
    }

    private Callback<List<Jurnal>> getAllCallback() {
        return result -> {
            jurnals.addAll(result);
        };
    }

    private void initComponent() {
        fabSync = findViewById(R.id.main_fab_sync);
        spnComp = findViewById(R.id.main_spn_com);
        tietExpenses = findViewById(R.id.main_tiet_expenses);
        btnDelete = findViewById(R.id.main_btn_delete);
        sharedPreferences = getApplicationContext().getSharedPreferences(JURNALS_PREFERENCES, Context.MODE_PRIVATE);

        loadPreferences();

        fabSync.setOnClickListener(getData());
        btnDelete.setOnClickListener(deleteJurnals());
    }

    private void loadPreferences() {
        int expense = sharedPreferences.getInt(JURNAL_EXPENSE, 0);
        int index = sharedPreferences.getInt(JURNAL_INDEX, 0);

        tietExpenses.setText(String.valueOf(expense));
        spnComp.setSelection(index);
    }

    private View.OnClickListener deleteJurnals() {
        return view -> {
            String compValue = spnComp.getSelectedItem().toString();
            int expensesPrag = tietExpenses.getText().toString().isBlank()
                ? 0
                : Integer.parseInt(tietExpenses.getText().toString());

            if (expensesPrag == 0) {
                Toast.makeText(getApplicationContext(), R.string.datele_pentru_stergere_nu_sunt_valide, Toast.LENGTH_SHORT).show();
                return;
            }

            switch (compValue) {
                case "are valoarea egala cu":
                    List<Jurnal> jurnalsToDelete = jurnals.stream()
                            .filter(jurnal -> jurnal.getExpenses() == expensesPrag)
                            .collect(Collectors.toList());
                    deleteListOfJurnals(jurnalsToDelete);
                    break;

                case "are valoarea mai mica decat":
                    List<Jurnal> jurnalsToDelete3 = jurnals.stream()
                            .filter(jurnal -> jurnal.getExpenses() < expensesPrag)
                            .collect(Collectors.toList());
                    deleteListOfJurnals(jurnalsToDelete3);
                    break;

                case "are valoarea mai mare decat":
                    List<Jurnal> jurnalsToDelete2 = jurnals.stream()
                            .filter(jurnal -> jurnal.getExpenses() > expensesPrag)
                            .collect(Collectors.toList());
                    deleteListOfJurnals(jurnalsToDelete2);
                    break;
            }
        };
    }

    private void deleteListOfJurnals(List<Jurnal> jurnalsToDelete) {
        for (Jurnal jurnal : jurnalsToDelete) {
            jurnalService.delete(jurnal, getDeleteCallback());
        }
    }

    private Callback<Boolean> getDeleteCallback() {
        return result -> {
            jurnals.clear();
            jurnalService.getAll(getAllCallback());
            savePreferences();
        };
    }

    private View.OnClickListener getData() {
        return view -> {
            Callable<String> callable = new HttpManager(NPOINT_URL);
            Callback<String> callback = getNPOINTCallback();
            asyncTaskRunner.executeAsync(callable, callback);
        };
    }

    private Callback<String> getNPOINTCallback() {
        return result -> {
            List<Jurnal> parsedJSON = JurnalParser.fromJSON(result);
            Log.i("NPOINT", parsedJSON.toString());

            for (Jurnal jurnal : parsedJSON) {
                if (!jurnals.contains(jurnal)) {
                    jurnalService.insert(jurnal, getInsertCallback());
                }
            }
        };
    }

    private Callback<Jurnal> getInsertCallback() {
        return result -> {
            if (result != null) {
                jurnals.add(result);
            }
        };
    }

    private void savePreferences() {
        int expense = tietExpenses.getText().toString().isBlank()
                ? 0
                : Integer.parseInt(tietExpenses.getText().toString());
        int index = spnComp.getSelectedItemPosition();

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(JURNAL_EXPENSE, expense);
        editor.putInt(JURNAL_INDEX, index);
        editor.apply();
    }
}