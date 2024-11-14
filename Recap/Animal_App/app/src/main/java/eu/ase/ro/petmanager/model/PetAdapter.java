package eu.ase.ro.petmanager.model;

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

import eu.ase.ro.petmanager.R;

public class PetAdapter extends ArrayAdapter<Pet> {
    private Context context;
    private int resource;
    private List<Pet> pets = new ArrayList<>();
    private LayoutInflater inflater;

    public PetAdapter(@NonNull Context context, int resource, @NonNull List<Pet> objects, LayoutInflater inflater) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.pets = objects;
        this.inflater = inflater;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = inflater.inflate(resource, parent, false);

        Pet pet = pets.get(position);
        TextView tvPetName = view.findViewById(R.id.row_tv_petName);
        tvPetName.setText(pet.getPetName());
        TextView tvPetOwner = view.findViewById(R.id.row_tv_petOwner);
        tvPetOwner.setText(pet.getPetOwner());
        TextView tvPetBirth = view.findViewById(R.id.row_tv_petBirth);
        tvPetBirth.setText(DateConverter.fromDate(pet.getBornDate()));
        TextView tvAge = view.findViewById(R.id.row_tv_petAge);
        tvAge.setText(context.getString(R.string.row_pet_age_template, pet.getAge()));

        return view;
    }
}
