package eu.ase.ro.damapp.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;

import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;

import eu.ase.ro.damapp.R;

public class ProfileFragment extends Fragment {

    private static final String PROFILE_SHARED = "profile_shared";
    public static final String NAME_KEY = "name_key";
    public static final String SALARY_KEY = "salary_key";
    public static final String VOTING_KEY = "voting_key";
    private TextInputEditText tietName;
    private TextInputEditText tietSalary;
    private RatingBar rbVoting;
    private Button btnSave;

    private SharedPreferences sharedPreferences;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getContext() == null) {
            return;
        }
        sharedPreferences = getContext()
                .getSharedPreferences(PROFILE_SHARED, Context.MODE_PRIVATE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        initComponents(view);
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

    private void initComponents(View view) {
        tietName = view.findViewById(R.id.profile_tiet_name);
        tietSalary = view.findViewById(R.id.profile_tiet_salary);
        rbVoting = view.findViewById(R.id.profile_rb_voting);
        btnSave = view.findViewById(R.id.profile_btn_save);

        btnSave.setOnClickListener(getSaveEvent());
    }

    private View.OnClickListener getSaveEvent() {
        return view -> {
            //salvare
            String name = tietName.getText() != null
                    ? tietName.getText().toString()
                    : "";
            float salary = tietSalary.getText() != null &&
                    !tietSalary.getText().toString().isBlank()
                    ? Float.parseFloat(tietSalary.getText().toString())
                    : 0;
            float voting = rbVoting.getRating();

            //salvam in fisierul de preferinte
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(NAME_KEY, name);
            editor.putFloat(SALARY_KEY, salary);
            editor.putFloat(VOTING_KEY, voting);
            editor.apply();
        };
    }
}