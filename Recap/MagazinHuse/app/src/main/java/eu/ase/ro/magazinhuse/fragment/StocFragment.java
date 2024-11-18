package eu.ase.ro.magazinhuse.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import eu.ase.ro.magazinhuse.R;
import eu.ase.ro.magazinhuse.model.HttpManager;
import eu.ase.ro.magazinhuse.model.HusaTelefon;
import eu.ase.ro.magazinhuse.model.HusaTelefonArrayAdapter;

public class StocFragment extends Fragment {
    private String URL = "https://api.npoint.io/c37490faa0ea649c9826";
    private ExecutorService executor = Executors.newCachedThreadPool();
    private Handler handler = new Handler(Looper.getMainLooper());

    private List<HusaTelefon> huse = new ArrayList<>();
    private ListView lvHuseStoc;

    public StocFragment() {
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
        View view = inflater.inflate(R.layout.fragment_stoc, container, false);

        HttpManager httpManager = new HttpManager(URL);
        executor.execute(() -> {
            String result = httpManager.getCall();
            if (result != null) {
                List<HusaTelefon> parsedHuse = parseResult(result);

                handler.post(() -> {
                    huse.clear();
                    huse.addAll(parsedHuse);
                    displayStoc(view);
                });
            }
        });

        return view;
    }

    private List<HusaTelefon> parseResult(String result) {
        List<HusaTelefon> huse = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(result);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                String material = jsonObject.getString("material");
                Double lungime = jsonObject.getDouble("lungime");
                String model = jsonObject.getString("modelTelefon");

                huse.add(new HusaTelefon(material, lungime, model));
            }

            return huse;
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }


    private void displayStoc(View view) {
       lvHuseStoc = view.findViewById(R.id.timofte_serban_stoc_lv);
        HusaTelefonArrayAdapter adapter = new HusaTelefonArrayAdapter(getContext().getApplicationContext(),
                R.layout.row_lv_home,
                huse,
                getLayoutInflater());
        lvHuseStoc.setAdapter(adapter);
    }
}