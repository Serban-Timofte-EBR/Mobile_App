package eu.ase.ro.a5_car;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import eu.ase.ro.a5_car.model.Car;
import eu.ase.ro.a5_car.network.AsyncTaskRunner;
import eu.ase.ro.a5_car.network.Callback;
import eu.ase.ro.a5_car.network.CarParser;
import eu.ase.ro.a5_car.network.HttpManager;

public class MainActivity extends AppCompatActivity {
    private FloatingActionButton fabGetData;

    private final String NPOINT_URL = "https://api.npoint.io/e6e75ce93e54db938689";
    private AsyncTaskRunner asyncTaskRunner = new AsyncTaskRunner();

    private List<Car> cars = new ArrayList<>();

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

        initComponent();
    }

    private void initComponent() {
        fabGetData = findViewById(R.id.main_fab_getData);
        fabGetData.setOnClickListener(getDataFromNPOINT());
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
                Log.i("NPOINT", parsedCars.toString());
            } else {
                Toast.makeText(getApplicationContext(), R.string.main_it_is_a_problem_with_the_connection, Toast.LENGTH_SHORT).show();
            }
        };
    }
}