package eu.ase.ro.magazinhuse;

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

import eu.ase.ro.magazinhuse.model.HusaTelefon;

public class AddHusaActivity extends AppCompatActivity {
    public static final String HUSA_KEY = "husa_key";

    private TextInputEditText tietMaterial;
    private TextInputEditText tietLungime;
    private Spinner spnModel;
    private Button btnSave;

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_husa);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        intent = getIntent();
        initComponent();

        btnSave.setOnClickListener(v -> {
            String material = tietMaterial.getText().toString();
            Double lungime = Double.parseDouble(tietLungime.getText().toString());
            String model = spnModel.getSelectedItem().toString();

            HusaTelefon husaUser = new HusaTelefon(material, lungime, model);

            intent.putExtra(HUSA_KEY, husaUser);
            setResult(RESULT_OK, intent);
            finish();
        });
    }

    private void initComponent() {
        tietMaterial = findViewById(R.id.timofte_serban_tiet_material);
        tietLungime = findViewById(R.id.timofte_serban_tiet_lungime);
        spnModel = findViewById(R.id.timofte_serban_spn_telefon);
        btnSave = findViewById(R.id.timofte_serban_add_btn_save);
    }
}