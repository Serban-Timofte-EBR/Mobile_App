package eu.ase.ro.a9_jurnal;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class JurnalParser {
    public static List<Jurnal> fromJSON(String json) {
        List<Jurnal> result = new ArrayList<>();

        try {
            JSONObject root = new JSONObject(json);
            JSONObject detailsObject = root.getJSONObject("details");
            JSONArray datasets = detailsObject.getJSONArray("datasets");
            for (int i = 0; i < datasets.length(); i++) {
                JSONObject object = datasets.getJSONObject(i);
                JSONObject jurnalObject = object.getJSONObject("jurnal");

                int expenses = jurnalObject.getInt("expenses");
                String destination = jurnalObject.getString("destination");
                Date date = DateConverter.toDate(jurnalObject.getString("departureDate"));

                result.add(new Jurnal(expenses, date, destination));
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

        return result;
    }
}
