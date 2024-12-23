package eu.ase.ro.triviachimieorganica.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

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

        btnStartTrivia.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Starting Trivia...", Toast.LENGTH_SHORT).show();
            startTrivia();
        });

        return view;
    }

    private void startTrivia() {
        TriviaFragment trivia = new TriviaFragment();

        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.timofte_serban_main_fl, trivia)
                .commit();

        NavigationView navigationView = getActivity().findViewById(R.id.nav_view);
        navigationView.setCheckedItem(R.id.timofte_serban_nav_trivia);
    }
}