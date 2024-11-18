package eu.ase.ro.magazinhuse.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import eu.ase.ro.magazinhuse.R;

public class HusaTelefonArrayAdapter extends ArrayAdapter<HusaTelefon> {
    private Context context;
    private int resource;
    private List<HusaTelefon> huse = new ArrayList<>();
    private LayoutInflater inflater;

    public HusaTelefonArrayAdapter(@NonNull Context context, int resource, @NonNull List<HusaTelefon> objects, LayoutInflater inflater) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.huse = objects;
        this.inflater = inflater;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = inflater.inflate(resource, parent, false);

        HusaTelefon husa = huse.get(position);

        TextView tvMaterial = view.findViewById(R.id.timofte_serbam_tv_material);
        tvMaterial.setText(husa.getMaterial());

        TextView tvLungime = view.findViewById(R.id.timofte_serbam_tv_lungime);
        tvLungime.setText(husa.getLungime().toString());

        TextView tvModel = view.findViewById(R.id.timofte_serbam_tv_model);
        tvModel.setText(husa.getModelTelefon());

        return view;
    }
}
