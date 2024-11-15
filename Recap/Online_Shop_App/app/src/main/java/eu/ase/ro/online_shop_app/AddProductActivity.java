package eu.ase.ro.online_shop_app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;

import eu.ase.ro.online_shop_app.model.Product;

public class AddProductActivity extends AppCompatActivity {
    public static final String PRODUCT_KEY = "product_key";

    private TextInputEditText tietName;
    private TextInputEditText tietDescription;
    private Spinner spnCategory;
    private TextInputEditText tietPrice;
    private Button btnSave;

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_product2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        intent = getIntent();
        initComponent();
    }

    private void initComponent() {
        tietName = findViewById(R.id.add_tiet_name);
        tietDescription = findViewById(R.id.add_tiet_description);
        spnCategory = findViewById(R.id.add_spn_category);
        tietPrice = findViewById(R.id.add_tiet_price);
        btnSave = findViewById(R.id.add_btn_save);

        btnSave.setOnClickListener(v -> {
            if(isValid()) {
                Product userProduct = createProductFromUser();
                intent.putExtra(PRODUCT_KEY, userProduct);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    private boolean isValid() {
        if(tietName.getText() == null || tietName.getText().toString().trim().length() < 3) {
            Toast.makeText(this, "Name is invalid", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(tietDescription.getText() == null || tietDescription.getText().toString().trim().length() < 3) {
            Toast.makeText(this, "Description is invalid", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(tietPrice.getText() == null || tietPrice.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Price is invalid", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private Product createProductFromUser() {
        String name = tietName.getText().toString();
        String description = tietName.getText().toString();
        String category = spnCategory.getSelectedItem().toString();
        Double price = Double.parseDouble(tietPrice.getText().toString());

        Product userProduct = new Product(name, description, price, category);

        return userProduct;
    }
}