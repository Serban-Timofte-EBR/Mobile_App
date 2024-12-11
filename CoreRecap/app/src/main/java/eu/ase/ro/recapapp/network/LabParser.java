package eu.ase.ro.recapapp.network;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import eu.ase.ro.recapapp.model.DateConverter;
import eu.ase.ro.recapapp.model.Lab;

public class LabParser {
    public static List<Lab> fromJSON(String json) {
        List<Lab> results = new ArrayList<>();

        try {
            JSONArray jsonArray = new JSONArray(json);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                Date date = DateConverter.toDate(jsonObject.getString("labDate"));
                Integer classNumber = jsonObject.getInt("classNumber");
                String labName = jsonObject.getString("labName");

                results.add(new Lab(date, classNumber, labName));
            }

            return results;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
