package eu.ase.ro.flipro.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import eu.ase.ro.flipro.R;
import eu.ase.ro.flipro.models.Telefon;
import eu.ase.ro.flipro.models.TelefonAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    private List<Telefon> phones;

    public static final String ARGS_PHONE_KEY = "args_phone_key";

    private ListView lvPhones;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment getInstance(List<Telefon> list) {
        HomeFragment fragment = new HomeFragment();
        Bundle bundle = new Bundle();

        bundle.putParcelableArrayList(ARGS_PHONE_KEY, (ArrayList<? extends Parcelable>) list);

        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            phones = getArguments().getParcelableArrayList(ARGS_PHONE_KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        if (getContext() != null) {
            lvPhones = view.findViewById(R.id.timofte_serban_home_lv);
            TelefonAdapter adapter = new TelefonAdapter(getContext(),
                    R.layout.row_lv_home,
                    phones,
                    getLayoutInflater());
            lvPhones.setAdapter(adapter);

            lvPhones.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Telefon phone = phones.get(position);

                    Fragment viewFrg = ViewPhoneFragment.getInstance(phone);

                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.timofte_serban_main_fl, viewFrg)
                            .commit();
                }
            });
        }

        return view;
    }

    public void notifyData() {
        ArrayAdapter<Telefon> adapter = (ArrayAdapter<Telefon>) lvPhones.getAdapter();
        adapter.notifyDataSetChanged();
    }
}