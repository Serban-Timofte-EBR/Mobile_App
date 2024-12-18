package eu.ase.ro.a5_car;

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

import eu.ase.ro.a5_car.database.CarService;
import eu.ase.ro.a5_car.model.Car;
import eu.ase.ro.a5_car.network.AsyncTaskRunner;
import eu.ase.ro.a5_car.network.Callback;
import eu.ase.ro.a5_car.network.CarParser;
import eu.ase.ro.a5_car.network.HttpManager;

public class MainActivity extends AppCompatActivity {
    public static final String CARS_PREFERENCES = "cars_preferences";
    public static final String CAR_YEAR_PREF = "car_year_pref";
    private FloatingActionButton fabGetData;
    private Spinner spnComp;
    private TextInputEditText tietValue;
    private Button btnDelete;

    private final String NPOINT_URL = "https://api.npoint.io/e6e75ce93e54db938689";
    private AsyncTaskRunner asyncTaskRunner = new AsyncTaskRunner();

    private List<Car> cars = new ArrayList<>();

    private CarService carService;
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

        carService = new CarService(getApplicationContext());
        initComponent();

        carService.getAll(getAllCallback());
    }

    private void initComponent() {
        fabGetData = findViewById(R.id.main_fab_getData);
        fabGetData.setOnClickListener(getDataFromNPOINT());
        spnComp = findViewById(R.id.main_spn_comparation);
        tietValue = findViewById(R.id.main_tiet_value);
        btnDelete = findViewById(R.id.main_btn_delete);
        sharedPreferences = getApplicationContext().getSharedPreferences(CARS_PREFERENCES, Context.MODE_PRIVATE);

        loadPreferences();

        btnDelete.setOnClickListener(deleteCarsFromDB());
    }

    private void loadPreferences() {
        Integer year = sharedPreferences.getInt(CAR_YEAR_PREF, 0);
        tietValue.setText(year.toString());
    }

    private View.OnClickListener deleteCarsFromDB() {
        return view -> {
            Integer year = savePreferences();
            String spnCompValue = spnComp.getSelectedItem().toString();
            if (spnCompValue.equals("Mai Mic")){
                List<Car> carsToDelete = cars.stream()
                        .filter(car -> car.getFabricationYear() < year)
                        .collect(Collectors.toList());
                deleteCarsList(carsToDelete);
            } else if (spnCompValue.equals("Mai Mare")) {
                List<Car> carsToDelete = cars.stream()
                        .filter(car -> car.getFabricationYear() > year)
                        .collect(Collectors.toList());
                deleteCarsList(carsToDelete);
            } else if (spnCompValue.equals("Egal")) {
                List<Car> carsToDelete = cars.stream()
                        .filter(car -> car.getFabricationYear().equals(year))
                        .collect(Collectors.toList());
                Toast.makeText(getApplicationContext(), "Equals: " + carsToDelete.toString(), Toast.LENGTH_SHORT).show();
                deleteCarsList(carsToDelete);
            }
        };
    }

    private void deleteCarsList(List<Car> carsToDelete) {
        for (Car car : carsToDelete) {
            cars.clear();
            carService.delete(car, getDeleteCallback());
        }
    }

    private Callback<Boolean> getDeleteCallback() {
        return result -> {
            carService.getAll(getAllCallback());
        };
    }

    private Integer savePreferences() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Integer year = tietValue.getText().toString().isBlank() && Integer.valueOf(tietValue.getText().toString()) == null
                ? 0
                : Integer.valueOf(tietValue.getText().toString());

        editor.putInt(CAR_YEAR_PREF, year);

        editor.apply();
        return year;
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
            if (result != null) {
                List<Car> parsedCars = CarParser.parseJSONResponse(result);
                Toast.makeText(this, parsedCars.toString(), Toast.LENGTH_SHORT).show();

                for (Car car : parsedCars) {
                    if (!cars.contains(car)){
                        carService.insert(car, getInsertCallback());
                    }
                }
            } else {
                Toast.makeText(getApplicationContext(), "Eroare de conexiune", Toast.LENGTH_SHORT).show();
            }
        };
    }

    private Callback<Car> getInsertCallback() {
        return result -> {
            if (result.getId() > 0) {
                cars.add(result);
            }
        };
    }

    private Callback<List<Car>> getAllCallback() {
        return result -> {
            if (result != null) {
                cars.addAll(result);
            }
        };
    }
}