package eu.ase.ro.flipro;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;

import eu.ase.ro.flipro.models.Telefon;

public class AddTelefonActivity extends AppCompatActivity {
    public static final String PHONE_KEY = "phone_key";

    private Spinner spnBrand;
    private TextInputEditText tietModel;
    private RadioGroup rgBattery;
    private Button btnSave;

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_telefon);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        intent = getIntent();
        initComponent();

        btnSave.setOnClickListener(v -> {
            if (isValid()) {
                String brand = spnBrand.getSelectedItem().toString();
                String model = tietModel.getText().toString();
                Integer battery = rgBattery.getCheckedRadioButtonId() == R.id.timofte_serban_add_rb_80 ? 80 :
                        rgBattery.getCheckedRadioButtonId() == R.id.timofte_serban_add_rb_90 ? 90 : 100;

                Telefon userPhone = new Telefon(brand, model, battery);

                intent.putExtra(PHONE_KEY, userPhone);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    private void initComponent() {
        spnBrand = findViewById(R.id.timofte_serban_add_spn_brand);
        tietModel = findViewById(R.id.timofte_serban_add_tiet_model);
        rgBattery = findViewById(R.id.timofte_serban_add_rg_battery);
        btnSave = findViewById(R.id.timofte_serban_add_btn_save);
    }

    private boolean isValid() {
        if (tietModel.getText().toString().trim().isEmpty() || tietModel.getText().toString().length() < 3) {
            Toast.makeText(this, "Invalid model name", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}