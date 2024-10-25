package eu.ase.ro.damapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;

import java.text.ParseException;
import java.util.Date;

import eu.ase.ro.damapp.utils.DateConv;
import eu.ase.ro.damapp.utils.Expense;

public class AddExpenseActivity extends AppCompatActivity {

    private TextInputEditText ti_date;
    private Date user_date;

    private TextInputEditText ti_amount;
    private double user_amount;

    private TextInputEditText ti_description;
    private String user_description;

    private Spinner spinner_category;
    private String user_category;

    private Button btn_save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_expense);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initComponents();

        btn_save.setOnClickListener(view -> {
            if(validateFormInput()) {
                Expense user_expense = new Expense(user_date, user_amount, user_category, user_description);
                Intent result_intent = new Intent();
                result_intent.putExtra("EXPENSE_KEY", user_expense);
                setResult(RESULT_OK, result_intent);
                finish();
            }
        });
    }

    private void initComponents() {
        ti_date = findViewById(R.id.timofte_serban_add_tiet_date);
        ti_amount = findViewById(R.id.timofte_serban_add_tiet_amount);
        ti_description = findViewById(R.id.timofte_serban_add_expense_tiet_description);
        spinner_category = findViewById(R.id.timofte_serban_add_expense_spinner_category);
        btn_save = findViewById(R.id.timofte_serban_add_expense_btn_save);
    }

    private boolean validateFormInput() {
        String input_date = ti_date.getText().toString();
        try {
            user_date = DateConv.convert_string_to_date(input_date);
        } catch (ParseException e) {
            Toast.makeText(getApplicationContext(), "Invalid date format", Toast.LENGTH_LONG).show();
            return false;
        }

        String input_amount = ti_amount.getText().toString();
        try {
            user_amount = Double.parseDouble(input_amount);
            if(user_amount < 0) {
                Toast.makeText(getApplicationContext(), "Invalid amount value", Toast.LENGTH_SHORT).show();
                return false;
            }
        } catch (Exception ex) {
            Toast.makeText(getApplicationContext(), "Invalid amount value", Toast.LENGTH_SHORT).show();
            return false;
        }

        user_description = ti_description.getText().toString();
        user_category = spinner_category.getSelectedItem().toString();

        return true;
    }
}