package eu.ase.ro.online_shop_app.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import eu.ase.ro.online_shop_app.R;

public class ProductAdapter extends ArrayAdapter<Product> {
    private Context context;
    private int resource;
    private List<Product> products;
    private LayoutInflater inflater;

    public ProductAdapter(@NonNull Context context, int resource, @NonNull List<Product> objects, LayoutInflater inflater) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.products = objects;
        this.inflater = inflater;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = inflater.inflate(resource, parent, false);

        Product product = products.get(position);

        TextView name = view.findViewById(R.id.row_name);
        name.setText(product.getName());

        TextView price = view.findViewById(R.id.row_price);
        price.setText(getContext().getString(R.string.row_price_template, product.getPrice()));

        return view;
    }
}
