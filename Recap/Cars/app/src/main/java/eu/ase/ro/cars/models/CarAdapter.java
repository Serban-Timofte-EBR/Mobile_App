package eu.ase.ro.cars.models;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import eu.ase.ro.cars.R;

public class CarAdapter extends ArrayAdapter<Car> {
    private Context context;
    private int resource;
    private List<Car> cars;
    private LayoutInflater inflater;

    public CarAdapter(@NonNull Context context, int resource, @NonNull List<Car> objects, LayoutInflater inflater) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.cars = objects;
        this.inflater = inflater;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = inflater.inflate(resource, parent, false);

        Car car = cars.get(position);
        TextView tvBrand = view.findViewById(R.id.timofte_serban_row_home_tv_brand);
        tvBrand.setText(car.getBrand().toString());

        TextView tvModel = view.findViewById(R.id.timofte_serban_row_home_tv_model);
        tvModel.setText(car.getModel().toString());

        TextView tvYear = view.findViewById(R.id.timofte_serban_row_home_tv_year);
        tvYear.setText(car.getYear().toString());

        return view;
    }
}
