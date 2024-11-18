package eu.ase.ro.triviachimieorganica.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import eu.ase.ro.triviachimieorganica.R;
import eu.ase.ro.triviachimieorganica.models.Result;
import eu.ase.ro.triviachimieorganica.models.ResultAdapter;

public class HistoryFragment extends Fragment {
    public static final String ARGS_RESULTS_KEY = "arg_results_key";

    private List<Result> results = new ArrayList<>();

    private ListView lvResults;
    private SearchView svResult;

    public HistoryFragment() {
        // Required empty public constructor
    }

    public static HistoryFragment getInstance(List<Result> results) {
        HistoryFragment fragment = new HistoryFragment();
        Bundle bundle = new Bundle();

        bundle.putParcelableArrayList(ARGS_RESULTS_KEY, (ArrayList<? extends Parcelable>) results);

        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            results = getArguments().getParcelableArrayList(ARGS_RESULTS_KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_history, container, false);

        if (getContext() != null) {
            lvResults = view.findViewById(R.id.timofte_serban_history_lv);
            svResult = view.findViewById(R.id.timofte_serban_sv_datePicker);
            ResultAdapter adapter = new ResultAdapter(getContext(),
                    R.layout.row_lv_history,
                    results,
                    getLayoutInflater());
            lvResults.setAdapter(adapter);

            initSeachView(adapter);
        }

        return view;
    }

    private void initSeachView(ResultAdapter adapter) {
        svResult.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
    }

    public void notifyData() {
        ArrayAdapter<Result> adapter = (ArrayAdapter<Result>) lvResults.getAdapter();
        adapter.notifyDataSetChanged();
    }
}