package eu.ase.ro.triviachimieorganica.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import eu.ase.ro.triviachimieorganica.R;


public class HomeFragment extends Fragment {
    private Button btnStartTrivia;
    private Button btnInfo;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        btnStartTrivia = view.findViewById(R.id.timofte_serban_home_btn_start_trivia);
        btnInfo = view.findViewById(R.id.timofte_serban_home_btn_info);

        btnStartTrivia.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Start Trivia", Toast.LENGTH_SHORT).show();
        });

        return view;
    }
}