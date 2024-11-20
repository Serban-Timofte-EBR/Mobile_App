package eu.ase.ro.cars.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import eu.ase.ro.cars.R;
import eu.ase.ro.cars.models.Car;
import eu.ase.ro.cars.models.HttpManager;
import eu.ase.ro.cars.models.Price;

public class PricesFragment extends Fragment {
    private final String URL = "https://api.npoint.io/a6025f29427ae036dd52";

    private List<Price> prices = new ArrayList<>();

    private ExecutorService executor = Executors.newCachedThreadPool();
    private Handler handler = new Handler(Looper.getMainLooper());

    private ListView lvPrices;

    public PricesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_prices, container, false);

        lvPrices = view.findViewById(R.id.timofte_serban_prices_lv);

        executor.execute(() -> {
            HttpManager httpManager = new HttpManager(URL);
            String result = httpManager.getCall();

            if (result != null) {
                List<Price> parsedList = parseResult(result);

                if (parsedList != null) {
                    handler.post(() -> {
                        prices.clear();
                        prices.addAll(parsedList);

                        ArrayAdapter<Price> adapter = new ArrayAdapter<>(getContext(),
                                android.R.layout.simple_list_item_1,
                                prices);
                        lvPrices.setAdapter(adapter);
                    });
                }
            }
        });

        return view;
    }

    private List<Price> parseResult(String result) {
        List<Price> list = new ArrayList<>();

        try {
            JSONArray jsonArray = new JSONArray(result);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                String brand = jsonObject.getString("brand");
                String model = jsonObject.getString("model");
                Double price = jsonObject.getDouble("price");

                list.add(new Price(brand, model, price));
            }

            return list;
        } catch (JSONException e) {
            return null;
        }
    }
}