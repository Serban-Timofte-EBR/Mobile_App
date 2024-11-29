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
    public static List<Expense> fromJSON(String json) {
        List<Expense> results = new ArrayList<>();

        try {
            JSONArray array = new JSONArray(json);

            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
                Expense expense = getExpense(object);
                results.add(expense);
            }

            return results;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }

    private static @NonNull Expense getExpense(JSONObject object) throws JSONException {
        Date date = DateConverter.toDate(object.getString("date"));
        Double amount = object.getDouble("amount");
        String category = object.getString("category");
        String description = object.getString("description");

        Expense expense = new Expense(date, amount, category, description);
        return expense;
    }
}
