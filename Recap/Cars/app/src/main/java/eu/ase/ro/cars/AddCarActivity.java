package eu.ase.ro.cars;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;

import eu.ase.ro.cars.models.Car;

public class AddCarActivity extends AppCompatActivity {
    public static final String CAR_KEY = "car_key";

    private TextInputEditText tietModel;
    private Spinner spnBrand;
    private TextInputEditText tietYear;
    private Button btnSave;

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_car);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        intent = getIntent();
        initComponent();
    }

    private void initComponent() {
        tietModel = findViewById(R.id.timofte_serban_add_tiet_model);
        spnBrand = findViewById(R.id.timofte_serban_add_spn_brand);
        tietYear = findViewById(R.id.timofte_serban_add_tiet_year);
        btnSave = findViewById(R.id.timofte_serban_add_btn_save);

        btnSave.setOnClickListener(v -> {
            Car userCar = getUserCar();
            intent.putExtra(CAR_KEY, userCar);
            setResult(RESULT_OK, intent);
            finish();
        });
    }

    private Car getUserCar() {
        String model = tietModel.getText().toString();
        String brand = spnBrand.getSelectedItem().toString();
        Integer year = Integer.parseInt(tietYear.getText().toString());

        return new Car(brand, model, year);
    }
}