package eu.ase.ro.a6_costum;

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

import eu.ase.ro.a6_costum.database.CostumeService;
import eu.ase.ro.a6_costum.model.Costume;
import eu.ase.ro.a6_costum.network.AsyncTaskRunner;
import eu.ase.ro.a6_costum.network.Callback;
import eu.ase.ro.a6_costum.network.CostumeParser;
import eu.ase.ro.a6_costum.network.HttpManager;

public class MainActivity extends AppCompatActivity {
    private final String NPOINT_URL = "https://api.npoint.io/d25865b34f76b1505bc0";

    private List<Costume> costumes = new ArrayList<>();

    private Spinner spnComparation;
    private TextInputEditText tietIncresePrice;
    private FloatingActionButton fabGetData;
    private Button btnDelete;
    private Button btnUpdate;

    private AsyncTaskRunner asyncTaskRunner = new AsyncTaskRunner();
    private CostumeService costumeService;

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
        costumeService = new CostumeService(getApplicationContext());
        initComponent();
        costumeService.getAll(getAllCallback());
    }

    private void initComponent() {
        spnComparation = findViewById(R.id.main_spn_comparation);
        tietIncresePrice = findViewById(R.id.main_tiet_addedPrice);
        fabGetData = findViewById(R.id.main_fab_getData);
        btnDelete = findViewById(R.id.main_btn_delete);
        btnUpdate = findViewById(R.id.main_btn_update);

        fabGetData.setOnClickListener(readDataFromURL());
        btnDelete.setOnClickListener(deleteData());
        btnUpdate.setOnClickListener(updateData());
    }

    private View.OnClickListener updateData() {
        return view -> {
            if (costumes != null) {
            Float price = Float.parseFloat(tietIncresePrice.getText().toString());
            for (Costume costume : costumes) {
                costume.setPrice(costume.getPrice() + price);
                costumeService.update(costume, getUpdateCallback());
            }
            }
        };
    }

    private Callback<Costume> getUpdateCallback() {
        return result -> {
            costumes.clear();
            costumeService.getAll(getAllCallback());
        };
    }

    private View.OnClickListener deleteData() {
        return view -> {
            String spinnerFilter = spnComparation.getSelectedItem().toString();
            Float price = Float.parseFloat(tietIncresePrice.getText().toString());
            if (spinnerFilter.equals("Mai Mic")) {
                List<Costume> costumeToDelete = costumes.stream()
                        .filter(costume -> costume.getPrice() < price)
                        .collect(Collectors.toList());
                deleteAll(costumeToDelete);
            } else if (spinnerFilter.equals("Mai Mare")) {
                List<Costume> costumeToDelete = costumes.stream()
                        .filter(costume -> costume.getPrice() > price)
                        .collect(Collectors.toList());
                deleteAll(costumeToDelete);
            } else {
                List<Costume> costumeToDelete = costumes.stream()
                        .filter(costume -> costume.getPrice().equals(price))
                        .collect(Collectors.toList());
                deleteAll(costumeToDelete);
            }
        };
    }

    private void deleteAll(List<Costume> costumeToDelete) {
        for (Costume costume : costumeToDelete) {
            costumeService.delete(costume, getDeleteCallback());
        }
    }

    private View.OnClickListener readDataFromURL() {
        return view -> {
            Callable<String> callable = new HttpManager(NPOINT_URL);
            Callback<String> callback = getDataCallback();
            asyncTaskRunner.executeAsync(callable, callback);
        };
    }

    private Callback<String> getDataCallback() {
        return result -> {
            List<Costume> parsedResponse = CostumeParser.parseJSONResponse(result);

            for (Costume costume : parsedResponse) {
                if (!costumes.contains(costume)) {
                    costumeService.insert(costume, getInsertCallback());
                }
            }
        };
    }

    private Callback<Costume> getInsertCallback() {
        return result -> {
            Log.i("NPOINT", "Resultatul: " + result.toString());
            if (result != null) {
                costumes.add(result);
            }
        };
    }

    private Callback<List<Costume>> getAllCallback() {
        return result -> {
            costumes.addAll(result);
        };
    }

    private Callback<Boolean> getDeleteCallback() {
        return result -> {
            costumes.clear();
            costumeService.getAll(getAllCallback());
        };
    }
}