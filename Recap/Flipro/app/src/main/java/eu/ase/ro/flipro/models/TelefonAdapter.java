package eu.ase.ro.flipro.models;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import eu.ase.ro.flipro.R;

public class TelefonAdapter extends ArrayAdapter<Telefon> {
    private Context context;
    private int resource;
    private List<Telefon> phones;
    private LayoutInflater inflater;

    public TelefonAdapter(@NonNull Context context, int resource, @NonNull List<Telefon> objects, LayoutInflater inflater) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.phones = objects;
        this.inflater = inflater;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = inflater.inflate(resource, parent, false);

        Telefon phone = phones.get(position);

        TextView tvBrand = view.findViewById(R.id.timofte_serban_row_home_tv_brand);
        tvBrand.setText(phone.getBrand());

        TextView tvModel = view.findViewById(R.id.timofte_serban_row_home_tv_model);
        tvModel.setText(phone.getModel());

        TextView tvBattery = view.findViewById(R.id.timofte_serban_row_home_tv_battery);
        tvBattery.setText(phone.getProcentBaterie().toString());

        return view;
    }
}
