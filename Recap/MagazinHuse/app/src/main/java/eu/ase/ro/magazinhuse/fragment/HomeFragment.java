package eu.ase.ro.magazinhuse.fragment;

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

import eu.ase.ro.magazinhuse.R;
import eu.ase.ro.magazinhuse.model.HusaTelefon;
import eu.ase.ro.magazinhuse.model.HusaTelefonArrayAdapter;

public class HomeFragment extends Fragment {
    public static final String ARGS_HUSE_KEY = "args_huse_key";

    private List<HusaTelefon> huse = new ArrayList<>();

    private ListView lvHuse;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment getInstance(List<HusaTelefon> list) {
        HomeFragment fragment = new HomeFragment();
        Bundle bundle = new Bundle();

        bundle.putParcelableArrayList(ARGS_HUSE_KEY, (ArrayList<? extends Parcelable>) list);

        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            huse = getArguments().getParcelableArrayList(ARGS_HUSE_KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        if (getContext() != null) {
            lvHuse = view.findViewById(R.id.timofte_serban_home_lv);
            HusaTelefonArrayAdapter adapter = new HusaTelefonArrayAdapter(getContext().getApplicationContext(),
                    R.layout.row_lv_home,
                    huse,
                    getLayoutInflater());
            lvHuse.setAdapter(adapter);

            lvHuse.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    HusaTelefon husa = huse.get(position);
                    Toast.makeText(getContext(), "Ai selectat: " + husa.toString(), Toast.LENGTH_SHORT).show();
                }
            });
        }

        return view;
    }

    public void notifyData() {
        ArrayAdapter<HusaTelefon> adapter = (ArrayAdapter<HusaTelefon>) lvHuse.getAdapter();
        adapter.notifyDataSetChanged();
    }
}