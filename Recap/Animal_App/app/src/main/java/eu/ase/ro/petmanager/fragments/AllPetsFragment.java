package eu.ase.ro.petmanager.fragments;

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

import eu.ase.ro.petmanager.R;
import eu.ase.ro.petmanager.model.Pet;
import eu.ase.ro.petmanager.model.PetAdapter;

public class AllPetsFragment extends Fragment {
    public static final String ARGS_PET_KEY = "args_pet_key";

    private List<Pet> pets = new ArrayList<>();

    private ListView lvPets;

    public AllPetsFragment() {
        // Required empty public constructor
    }

    public static AllPetsFragment getInstance(List<Pet> pets) {
        AllPetsFragment fragment = new AllPetsFragment();
        Bundle bundle = new Bundle();

        bundle.putParcelableArrayList(ARGS_PET_KEY, (ArrayList<? extends Parcelable>) pets);

        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null) {
            pets = getArguments().getParcelableArrayList(ARGS_PET_KEY);
            Log.i("AllPetsFragments", pets.toString());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_all_pets, container, false);
        if (getContext() != null) {
            lvPets = view.findViewById(R.id.pets_lv);
            PetAdapter adapter = new PetAdapter(getContext().getApplicationContext(),
                    R.layout.lv_pet,
                    pets,
                    getLayoutInflater());
            lvPets.setAdapter(adapter);
        }
        return view;
    }

    public void notifyAdapter() {
        ArrayAdapter<Pet> adapter = (ArrayAdapter<Pet>) lvPets.getAdapter();
        adapter.notifyDataSetChanged();
    }
}