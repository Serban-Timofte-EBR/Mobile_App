package eu.ase.ro.damapp.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import eu.ase.ro.damapp.R;
import eu.ase.ro.damapp.model.Expense;

public class HomeFragment extends Fragment {

    public static final String ARGS_EXPENSES_KEY = "args_expenses_key";

    private List<Expense> expenses = new ArrayList<>();

    private ListView lvExpenses;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment getInstance(List<Expense> expenses){
        HomeFragment fragment = new HomeFragment();
        Bundle bundle = new Bundle();

        bundle.putParcelableArrayList(ARGS_EXPENSES_KEY, (ArrayList<? extends Parcelable>) expenses);

        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null) {
            expenses = getArguments().getParcelableArrayList(ARGS_EXPENSES_KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        if(getContext() != null) {
            lvExpenses = view.findViewById(R.id.hom_lv_expenses);
            ArrayAdapter<Expense> adapter = new ArrayAdapter<>(
                    getContext().getApplicationContext(),
                    android.R.layout.simple_list_item_1,
                    expenses);
            lvExpenses.setAdapter(adapter);
        }
        return view;
    }

    public void notifyAdapter() {
        ArrayAdapter<Expense> adapter = (ArrayAdapter<Expense>) lvExpenses.getAdapter();
        adapter.notifyDataSetChanged();
    }
}