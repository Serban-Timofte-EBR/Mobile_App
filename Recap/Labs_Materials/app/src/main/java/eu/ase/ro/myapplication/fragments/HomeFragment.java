package eu.ase.ro.myapplication.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import eu.ase.ro.myapplication.R;
import eu.ase.ro.myapplication.model.Task;


public class HomeFragment extends Fragment {
    public static final String ARGS_TASKS_KEY = "args_tasks_key";

    private List<Task> tasks = new ArrayList<>();

    private ListView lvTasks;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment getInstance(List<Task> tasks) {
        HomeFragment fragment = new HomeFragment();
        Bundle bundle = new Bundle();

        bundle.putParcelableArrayList(ARGS_TASKS_KEY, (ArrayList<? extends Parcelable>) tasks);

        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            tasks = getArguments().getParcelableArrayList(ARGS_TASKS_KEY);
            Log.i("HomeFragment", tasks.toString());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.i("HomeFragment", tasks.toString());
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        if(getContext() != null) {
            lvTasks = view.findViewById(R.id.hom_lv_tasks);
            ArrayAdapter<Task> adapter = new ArrayAdapter<>(getContext().getApplicationContext(),
                    android.R.layout.simple_list_item_1,
                    tasks);
            lvTasks.setAdapter(adapter);
        }
        return view;
    }

    public void notifyAdapter() {
        ArrayAdapter<Task> adapter = (ArrayAdapter<Task>) lvTasks.getAdapter();
        adapter.notifyDataSetChanged();
    }
}