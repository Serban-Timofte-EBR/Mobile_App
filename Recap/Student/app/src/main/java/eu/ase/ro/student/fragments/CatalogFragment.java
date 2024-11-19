package eu.ase.ro.student.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import eu.ase.ro.student.R;
import eu.ase.ro.student.model.HttpManager;
import eu.ase.ro.student.model.Student;
import eu.ase.ro.student.model.StudentAdapter;

public class CatalogFragment extends Fragment {
    private ListView lvCatalog;
    private List<Student> students = new ArrayList<>();

    private String URL = "https://api.npoint.io/e82c9fd5f507a1ff1f44";

    private ExecutorService executor = Executors.newCachedThreadPool();
    private Handler handler = new Handler(Looper.getMainLooper());

    public CatalogFragment() {
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
        View view = inflater.inflate(R.layout.fragment_catalog, container, false);

        if (getContext() != null) {
            executor.execute(() -> {
                HttpManager httpManager = new HttpManager(URL);
                String result = httpManager.getCall();

                if (result != null) {
                    List<Student> parsedList = parseResult(result);

                    if (parsedList != null) {
                        handler.post(() -> {
                            students.clear();
                            students.addAll(parsedList);

                            Log.i("Catalog", students.toString());

                            lvCatalog = view.findViewById(R.id.timofte_serban_catalog_lv);
                            StudentAdapter adapter = new StudentAdapter(getContext(),
                                    R.layout.row_lv_home,
                                    students,
                                    getLayoutInflater());
                            lvCatalog.setAdapter(adapter);
                        });
                    }
                }
            });

            lvCatalog = view.findViewById(R.id.timofte_serban_catalog_lv);
            lvCatalog.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Student selectedStudent = students.get(position);

                    if (selectedStudent == null) {
                        Toast.makeText(getContext(), "Not a valid line", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), selectedStudent.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        return view;
    }

    private List<Student> parseResult(String result) {
        List<Student> list = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(result);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                String name = jsonObject.getString("name");
                Integer age = jsonObject.getInt("age");
                String faculty = jsonObject.getString("faculty");
                String frequency = jsonObject.getString("frequency");

                list.add(new Student(name, age, faculty, frequency));
            }

            return list;
        } catch (JSONException e) {
            return null;
        }


    }
}