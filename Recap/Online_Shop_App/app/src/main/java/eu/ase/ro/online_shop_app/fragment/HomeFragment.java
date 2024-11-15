package eu.ase.ro.online_shop_app.fragment;

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

import eu.ase.ro.online_shop_app.R;
import eu.ase.ro.online_shop_app.model.Product;
import eu.ase.ro.online_shop_app.model.ProductAdapter;

public class HomeFragment extends Fragment {
    public static final String ARGS_PRODUCT_KEY = "args_product_key";

    private List<Product> products = new ArrayList<>();
    private ListView lvProducts;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment getInstance(List<Product> list) {
        HomeFragment fragment = new HomeFragment();
        Bundle bundle = new Bundle();

        bundle.putParcelableArrayList(ARGS_PRODUCT_KEY, (ArrayList<? extends Parcelable>) list);

        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            products = getArguments().getParcelableArrayList(ARGS_PRODUCT_KEY);
            Log.i("HomeFragment", products.toString());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        if (getContext() != null) {
            lvProducts = view.findViewById(R.id.home_lv_product);
            ProductAdapter adapter = new ProductAdapter(getContext().getApplicationContext(),
                    R.layout.lv_row,
                    products,
                    getLayoutInflater());
            lvProducts.setAdapter(adapter);
        }
        return view;
    }

    public void notifyAdapter(){
        ArrayAdapter<Product> adapter = (ArrayAdapter<Product>) lvProducts.getAdapter();
        adapter.notifyDataSetChanged();
    }
}