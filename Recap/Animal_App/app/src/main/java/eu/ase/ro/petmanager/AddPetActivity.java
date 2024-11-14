package eu.ase.ro.petmanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;

import eu.ase.ro.petmanager.model.DateConverter;
import eu.ase.ro.petmanager.model.Pet;

public class AddPetActivity extends AppCompatActivity {
    public static final String PET_KEY = "pet_key";

    private TextInputEditText tietPetName;
    private Spinner spinnerPetOwner;
    private TextInputEditText tietPetBornDate;
    private TextInputEditText tietPetAge;
    private Button btnSave;

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_pet);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        intent = getIntent();
        initComponent();
    }

    private void initComponent() {
        tietPetName = findViewById(R.id.add_tiet_petName);
        spinnerPetOwner = findViewById(R.id.add_spn_ownerName);
        tietPetBornDate = findViewById(R.id.add_tiet_petBith);
        tietPetAge = findViewById(R.id.add_tiet_petAge);
        btnSave = findViewById(R.id.add_btn_save);

        btnSave.setOnClickListener(savePet());
    }

    private View.OnClickListener savePet() {
        return view -> {
          if(isValid()) {
              Pet userPet = createPetFromView();
              intent.putExtra(PET_KEY, userPet);
              setResult(RESULT_OK, intent);
              finish();
          }
        };
    }

    private boolean isValid() {
        if(tietPetName.getText() == null || tietPetName.getText().toString().trim().length() < 3) {
            Toast.makeText(this, "Pet Name invalid", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(DateConverter.toDate(tietPetBornDate.getText().toString()) == null) {
            Toast.makeText(this, "Born date invalid", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(tietPetAge.getText() == null) {
            Toast.makeText(this, "Pet Age invalid", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    };

    private Pet createPetFromView() {
        Pet pet = new Pet(tietPetName.getText().toString(),
                spinnerPetOwner.getSelectedItem().toString(),
                DateConverter.toDate(tietPetBornDate.getText().toString()),
                Integer.parseInt(tietPetAge.getText().toString()));
        return pet;
    }
}