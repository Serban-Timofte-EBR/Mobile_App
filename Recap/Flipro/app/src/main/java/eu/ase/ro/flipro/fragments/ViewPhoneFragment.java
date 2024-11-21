package eu.ase.ro.flipro.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import eu.ase.ro.flipro.R;
import eu.ase.ro.flipro.models.Telefon;

public class ViewPhoneFragment extends Fragment {
    public static final String VIEW_PHONE_KEY = "view_phone_key";

    private Telefon phone;

    public ViewPhoneFragment() {
        // Required empty public constructor
    }

    public static ViewPhoneFragment getInstance(Telefon phone) {
        ViewPhoneFragment fragment = new ViewPhoneFragment();
        Bundle bundle = new Bundle();

        bundle.putParcelable(VIEW_PHONE_KEY, phone);

        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            phone = getArguments().getParcelable(VIEW_PHONE_KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_view_phone, container, false);

        TextView tvBrand = view.findViewById(R.id.timofte_serban_view_tv_brand);
        tvBrand.setText(getString(R.string.view_brand_template, phone.getBrand()));

        TextView tvModel = view.findViewById(R.id.timofte_serban_view_tv_mode);
        tvModel.setText(getString(R.string.view_model_template, phone.getModel()));

        TextView tvBattery = view.findViewById(R.id.timofte_serban_view_tv_baterie);
        tvBattery.setText(getString(R.string.view_battery_template, phone.getProcentBaterie()));
        return view;
    }
}