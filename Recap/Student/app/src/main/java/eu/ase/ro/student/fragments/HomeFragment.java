package eu.ase.ro.student.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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

public class HomeFragment extends Fragment {
    public static final String ARGS_STUNDETS_KEY = "args_students_key";

    private List<Student> students = new ArrayList<>();

    private ListView lvStudents;

    private ExecutorService executors = Executors.newCachedThreadPool();
    private Handler handler = new Handler(Looper.getMainLooper());

    private String URL = "https://api.npoint.io/e82c9fd5f507a1ff1f44";

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment getInstance(List<Student> list) {
        HomeFragment fragment = new HomeFragment();
        Bundle bundle = new Bundle();

        bundle.putParcelableArrayList(ARGS_STUNDETS_KEY, (ArrayList<? extends Parcelable>) list);

        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            students = getArguments().getParcelableArrayList(ARGS_STUNDETS_KEY);
            Toast.makeText(getContext(), students.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        if (getContext() != null) {
            lvStudents = view.findViewById(R.id.timfote_serban_home_lv_students);

            executors.execute(() -> {
                HttpManager httpManager = new HttpManager(URL);
                String result = httpManager.getCall();

                if (result != null) {
                    List<Student> parsedList = parsedResult(result);

                    if (parsedList != null) {
                        handler.post(() -> {
                           students.addAll(parsedList);
                           notifyData();
                        });
                    }
                }
            });

            StudentAdapter adapter = new StudentAdapter(getContext(),
                    R.layout.row_lv_home,
                    students,
                    getLayoutInflater());
            lvStudents.setAdapter(adapter);
        }

        return view;
    }

    private List<Student> parsedResult(String result) {
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

    public void notifyData() {
        ArrayAdapter<Student> adapter = (ArrayAdapter<Student>) lvStudents.getAdapter();
        adapter.notifyDataSetChanged();
    }
}