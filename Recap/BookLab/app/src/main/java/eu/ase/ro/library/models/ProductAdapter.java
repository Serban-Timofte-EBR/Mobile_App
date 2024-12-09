package eu.ase.ro.library.models;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import eu.ase.ro.library.R;

public class ProductAdapter extends ArrayAdapter<Product> {
    Context context;
    int resource;
    List<Product> products;
    LayoutInflater layoutInflater;

    public ProductAdapter(@NonNull Context context, int resource, @NonNull List<Product> objects, LayoutInflater inflater) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.products = objects;
        this.layoutInflater = inflater;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = layoutInflater.inflate(resource, parent, false);

        Product product = products.get(position);

        TextView tvCategory = view.findViewById(R.id.row_tv_category);
        tvCategory.setText(product.getCategory().toString());

        TextView tvPrice = view.findViewById(R.id.row_tv_price);
        tvPrice.setText(product.getPrice().toString());

        TextView tvName = view.findViewById(R.id.row_tv_name);
        tvName.setText(product.getName());

        return view;
    }
}
