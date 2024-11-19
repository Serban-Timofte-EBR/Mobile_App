package eu.ase.ro.cars.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import eu.ase.ro.cars.R;
import eu.ase.ro.cars.models.Car;

public class HomeFragment extends Fragment {
    public static final String ARGS_CAR_KEY = "args_car_key";

    private List<Car> cars = new ArrayList<>();

    private ListView lvCars;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment getInstance(List<Car> list) {
        HomeFragment fragment = new HomeFragment();
        Bundle bundle = new Bundle();

        bundle.putParcelableArrayList(ARGS_CAR_KEY, (ArrayList<? extends Parcelable>) list);

        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            cars = getArguments().getParcelableArrayList(ARGS_CAR_KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        if (getContext() != null) {
            lvCars = view.findViewById(R.id.timofte_serban_home_lv);
            ArrayAdapter<Car> adapter = new ArrayAdapter<>(getContext(),
                    android.R.layout.simple_list_item_1,
                    cars);
            lvCars.setAdapter(adapter);
        }
        return view;
    }

    public void notifyData() {
        ArrayAdapter<Car> adapter = (ArrayAdapter<Car>) lvCars.getAdapter();
        adapter.notifyDataSetChanged();
    }
}