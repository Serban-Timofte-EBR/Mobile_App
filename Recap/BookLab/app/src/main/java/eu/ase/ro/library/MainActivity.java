package eu.ase.ro.library;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import eu.ase.ro.library.databinding.ActivityMainBinding;
import eu.ase.ro.library.models.Product;
import eu.ase.ro.library.models.ProductAdapter;
import eu.ase.ro.library.network.HttpManager;

public class MainActivity extends AppCompatActivity {
    private final String NPOINT_URL = "https://api.npoint.io/4bfecbcd54853f5ac0d2";
    private ExecutorService executor = Executors.newCachedThreadPool();
    private Handler handler = new Handler(Looper.getMainLooper());

    private List<Product> products = new ArrayList<>();

    ListView lvProducts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvProducts = findViewById(R.id.main_lv_products);
        ProductAdapter adapter = new ProductAdapter(getApplicationContext(),
                R.layout.row_lv_products,
                products,
                getLayoutInflater());
        lvProducts.setAdapter(adapter);

        navigationInit();
    }

    private void fetchData() {
        executor.execute(() -> {
            HttpManager httpManager = new HttpManager(NPOINT_URL);
            String result = httpManager.getCall();

            if (result == null) {
                Toast.makeText(getApplicationContext(), "Empty result from URL", Toast.LENGTH_SHORT).show();
                return;
            }

            List<Product> parsedList = parseResult(result);

            handler.post(() -> {
                if (parsedList.isEmpty())
                {
                    Toast.makeText(getApplicationContext(), "No result from URL", Toast.LENGTH_SHORT).show();
                } else {
                    products.addAll(parsedList);
                    Toast.makeText(getApplicationContext(), products.toString(), Toast.LENGTH_SHORT).show();
                    notifyAdapter();
                }
            });
        });
    }

    private List<Product> parseResult(String result) {
        List<Product> list = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(result);

            if (jsonObject.has("data")) {
                JSONArray jsonArrayData = jsonObject.getJSONArray("data");

                for (int i = 0; i < jsonArrayData.length(); i++) {
                    JSONObject productObject = jsonArrayData.getJSONObject(i);

                    String categoryString = productObject.getString("category");
                    JSONObject details = productObject.getJSONObject("details");
                    String name = details.getString("name");
                    double price = details.getDouble("price");

                    Product.Category category = Product.Category.valueOf(categoryString);

                    Product product = new Product(category, price, name);

                    list.add(product);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    private void navigationInit() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.opt_import_data) {
            fetchData();
        }
        return super.onOptionsItemSelected(item);
    }

    private void notifyAdapter() {
        ArrayAdapter<Product> adapter = (ArrayAdapter<Product>) lvProducts.getAdapter();
        adapter.notifyDataSetChanged();
    }
}