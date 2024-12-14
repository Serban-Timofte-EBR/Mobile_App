package eu.ase.ro.damapp.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;

import com.google.android.material.textfield.TextInputEditText;

import org.w3c.dom.Text;

import eu.ase.ro.damapp.R;

public class ProfileFragment extends Fragment {
    public static final String SALARY_KEY = "salary_key";
    public static final String VOTING_KEY = "voting_key";
    private static final String PROFILE_SHARED = "profile_shared";
    public static final String NAME_KEY = "name_key";

    private TextInputEditText tietName;
    private TextInputEditText tietSalary;
    private RatingBar rbVoting;
    private Button btnSave;

    // FISERE DE PREFERINTA - STOCAM DATE SIMPLE, DE PROFIL
    private SharedPreferences sharedPreferences;

    public ProfileFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getContext() == null){ return; }

        sharedPreferences = getContext().getSharedPreferences(PROFILE_SHARED, Context.MODE_PRIVATE);    // daca fisierul nu exista, se creeaza pe loc

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        initComponent(view);

        loadFromPreferences();

        return view;
    }

    private void loadFromPreferences() {
        String name = sharedPreferences.getString(NAME_KEY, "");
        float salary = sharedPreferences.getFloat(SALARY_KEY, 0);
        float voting = sharedPreferences.getFloat(VOTING_KEY, 0.5f);

        tietName.setText(name);
        tietSalary.setText(String.valueOf(salary));
        rbVoting.setRating(voting);
    }

    private void initComponent(View view) {
        tietName = view.findViewById(R.id.profile_tiet_name);
        tietSalary = view.findViewById(R.id.profile_tiet_salary);

        rbVoting = view.findViewById(R.id.profile_rb_voting);
        btnSave = view.findViewById(R.id.profile_btn_save);

        btnSave.setOnClickListener(getSaveEvent());
    }

    private View.OnClickListener getSaveEvent() {
        return v -> {
            // Salvarea informatiilor
            String name = tietName.getText() != null ? tietName.getText().toString() : "";
            float salary = tietSalary.getText() != null && !tietSalary.getText().toString().isBlank()
                    ? Float.parseFloat(tietSalary.getText().toString())
                    : 0;
            float voting = rbVoting.getRating();    // ia numarul de stele selectate de user

            // Salvam valorile in fisierul de preferinte
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(NAME_KEY, name);
            editor.putFloat(SALARY_KEY, salary);
            editor.putFloat(VOTING_KEY, voting);
            editor.apply();

            // Daca vrem sa navigam in alt fragment ar trebui sa facem o functie publica goHome si sa ii dam call aici
        };
    }
}