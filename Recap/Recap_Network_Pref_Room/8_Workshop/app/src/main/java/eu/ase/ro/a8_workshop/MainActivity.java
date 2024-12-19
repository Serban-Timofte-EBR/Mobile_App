package eu.ase.ro.a8_workshop;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

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
    public static final String WORKSHOP_PREFERENCES = "workshop_preferences";
    public static final String WORKSHOP_PRICE = "workshop_price";
    public static final String WORKSHOP_COMPARATION = "workshop_comparation";
    private final String NPOINT_URL = "https://api.npoint.io/9165df24cb1b721c3dd6";

    private List<Workshop> workshops = new ArrayList<>();

    private Spinner spnComparation;
    private TextInputEditText tietPrice;
    private FloatingActionButton fabGetData;
    private Button btnDelete;
    private Button btnUpdate;

    private AsyncTaskRunner asyncTaskRunner = new AsyncTaskRunner();

    private WorkshopService workshopService;

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
        workshopService = new WorkshopService(getApplicationContext());
        initComponent();

        workshopService.getAll(getAllCallback());
    }

    private Callback<List<Workshop>> getAllCallback() {
        return result -> {
            workshops.addAll(result);
        };
    }

    private void initComponent() {
        spnComparation = findViewById(R.id.main_spn_comp);
        tietPrice = findViewById(R.id.main_tiet_price);
        fabGetData = findViewById(R.id.main_fab_get);
        btnDelete = findViewById(R.id.main_btn_delete);
        btnUpdate = findViewById(R.id.main_btn_update);
        sharedPreferences = getApplicationContext().getSharedPreferences(WORKSHOP_PREFERENCES, Context.MODE_PRIVATE);

        loadPreferences();

        fabGetData.setOnClickListener(getDataFromNPOINT());
        btnDelete.setOnClickListener(deleteWorkshops());
        btnUpdate.setOnClickListener(updateWorkshops());
    }

    private View.OnClickListener updateWorkshops() {
        return view -> {
            float price = savePreferences();
            for (Workshop workshop : workshops) {
                workshop.setTotalPrice(workshop.getTotalPrice() + price);
                workshopService.update(workshop, getUpdateCallback());
            }
        };
    }

    private Callback<Workshop> getUpdateCallback() {
        return result -> {
            workshops.clear();
            workshopService.getAll(getAllCallback());
        };
    }

    private void loadPreferences() {
        Float price = sharedPreferences.getFloat(WORKSHOP_PRICE, 0F);
        int spnValuePosition = sharedPreferences.getInt(WORKSHOP_COMPARATION, 0);
        tietPrice.setText(price.toString());
        spnComparation.setSelection(spnValuePosition);
    }

    private View.OnClickListener deleteWorkshops() {
        return view -> {
            float price = savePreferences();
            String spnValue = spnComparation.getSelectedItem().toString();

            switch (spnValue) {
                case "Mai Mic":
                    List<Workshop> workshopsToDelete = workshops.stream()
                            .filter(workshop -> workshop.getTotalPrice() < price)
                            .collect(Collectors.toList());
                    deleteList(workshopsToDelete);
                    break;

                case "Mai Mare":
                    List<Workshop> workshopsToDelete2 = workshops.stream()
                            .filter(workshop -> workshop.getTotalPrice() > price)
                            .collect(Collectors.toList());
                    deleteList(workshopsToDelete2);
                    break;

                case "Egal":
                    List<Workshop> workshopsToDelete3 = workshops.stream()
                            .filter(workshop -> workshop.getTotalPrice() == price)
                            .collect(Collectors.toList());
                    deleteList(workshopsToDelete3);
                    break;
            }
        };
    }

    private void deleteList(List<Workshop> workshopsToDelete) {
        for (Workshop workshop : workshopsToDelete) {
            workshopService.delete(workshop, getDeleteCallback());
        }
    }

    private Callback<Boolean> getDeleteCallback() {
        return result -> {
            workshops.clear();
            workshopService.getAll(getAllCallback());
        };
    }

    private float savePreferences() {
        SharedPreferences.Editor editor = sharedPreferences.edit();

        float price = tietPrice.getText().toString().isBlank() == false && Float.parseFloat(tietPrice.getText().toString()) > 0F
                ? Float.parseFloat(tietPrice.getText().toString())
                : 0F;
        int spinnerValuePositon = spnComparation.getSelectedItemPosition();

        editor.putFloat(WORKSHOP_PRICE, price);
        editor.putInt(WORKSHOP_COMPARATION, spinnerValuePositon);
        editor.apply();

        return price;
    }

    private View.OnClickListener getDataFromNPOINT() {
        return view -> {
            Callable<String> callable = new HttpManager(NPOINT_URL);
            Callback<String> callback = getNPOINTCallback();
            asyncTaskRunner.executeAsync(callable, callback);
        };
    }

    private Callback<String> getNPOINTCallback() {
        return result -> {
            List<Workshop> parsedWorkshops = WorkshopParser.fromJSON(result);
            Log.i("NPOINT", parsedWorkshops.toString());

            for (Workshop workshop : parsedWorkshops) {
                if (!workshops.contains(workshop)) {
                    workshopService.insert(workshop, getInsertCallback());
                }
            }
        };
    }

    private Callback<Workshop> getInsertCallback() {
        return result -> {
            if (result != null) {
                workshops.add(result);
            }
        };
    }
}