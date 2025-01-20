package eu.ase.ro.damapp.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;
import java.util.zip.Inflater;

import eu.ase.ro.damapp.R;

public class ExpenseAdapter
        extends ArrayAdapter<Expense> {

    private Context contex;
    private int resource;
    private List<Expense> expenses;
    private LayoutInflater inflater;

    public ExpenseAdapter(@NonNull Context context,
                          int resource,
                          @NonNull List<Expense> objects,
                          LayoutInflater inflater)
    {
        super(context, resource, objects);
        this.contex = context;
        this.resource = resource;
        this.expenses = objects;
        this.inflater = inflater;
    }

    @NonNull
    @Override
    public View getView(
            int position,
            @Nullable View convertView,
            @NonNull ViewGroup parent)
    {
        View view = inflater.inflate(resource, parent, false);  // creem un view pentru a incarca XML-ul in fragment si sa putem folosi acel findViewById
        Expense expense = expenses.get(position);

        TextView tvDate = view.findViewById(R.id.row_tv_date);
        tvDate.setText(DateConverter.fromDate(expense.getDate()));

        TextView tvCategory = view.findViewById(R.id.row_tv_category);
        tvCategory.setText(expense.getCategory());

        TextView tvDescription = view.findViewById(R.id.row_tv_description);
        tvDescription.setText(expense.getDescription());

        TextView tvAmount = view.findViewById(R.id.row_tv_amount);
        tvAmount.setText(contex.getString(R.string.expense_row_amount_template, expense.getAmount()));

        return view;
    }
}
