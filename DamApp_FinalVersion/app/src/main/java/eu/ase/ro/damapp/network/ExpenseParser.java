package eu.ase.ro.damapp.network;

import androidx.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import eu.ase.ro.damapp.model.DateConverter;
import eu.ase.ro.damapp.model.Expense;

public class ExpenseParser {

    public static final String DATE = "date";
    public static final String AMOUNT = "amount";
    public static final String CATEGORY = "category";
    public static final String DESCRIPTION = "description";

    public static List<Expense> fromJson(String json) {
        try {
            return getExpensesFromJson(new JSONArray(json));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    @NonNull
    private static List<Expense> getExpensesFromJson(JSONArray array) throws JSONException {
        List<Expense> results = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            JSONObject object = array.getJSONObject(i);
            Expense expense = getExpenseFromJson(object);
            results.add(expense);
        }
        return results;
    }

    @NonNull
    private static Expense getExpenseFromJson(JSONObject object) throws JSONException {
        Date date = DateConverter.toDate(object.getString(DATE));
        double amount = object.getDouble(AMOUNT);
        String category = object.getString(CATEGORY);
        String description = object.getString(DESCRIPTION);

        return new Expense(date, amount, category, description);
    }
}
