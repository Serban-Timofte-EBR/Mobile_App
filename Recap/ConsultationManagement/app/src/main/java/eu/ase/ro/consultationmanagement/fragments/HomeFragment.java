package eu.ase.ro.consultationmanagement.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import eu.ase.ro.consultationmanagement.R;
import eu.ase.ro.consultationmanagement.model.Consultation;
import eu.ase.ro.consultationmanagement.model.ConsultationAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    private static final String ARGS_CONSULTATIONS_KEY = "args_consultation_key";

    private List<Consultation> consultations = new ArrayList<>();
    private ListView lvConsultations;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment getInstance(List<Consultation> list) {
        HomeFragment fragment = new HomeFragment();
        Bundle bundle = new Bundle();

        bundle.putParcelableArrayList(ARGS_CONSULTATIONS_KEY, (ArrayList<? extends Parcelable>) list);

        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            consultations = getArguments().getParcelableArrayList(ARGS_CONSULTATIONS_KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        if (getContext() != null) {
            lvConsultations = view.findViewById(R.id.timofte_serban_home_lv_consultations);
            ConsultationAdapter adapter = new ConsultationAdapter(getContext(),
                    R.layout.row_lv_home,
                    consultations,
                    getLayoutInflater());
            lvConsultations.setAdapter(adapter);

            lvConsultations.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Consultation consultation = consultations.get(position);

                    Toast.makeText(getContext(), "Selected One: " + consultation.toString(), Toast.LENGTH_SHORT).show();
                }
            });
        }

        return view;
    }

    public void notifyData() {
        ArrayAdapter<Consultation> adapter = (ArrayAdapter<Consultation>) lvConsultations.getAdapter();
        adapter.notifyDataSetChanged();
    }
}