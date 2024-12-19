package eu.ase.ro.a8_workshop;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class WorkshopParser {
    public static List<Workshop> fromJSON(String json) {
        List<Workshop> result = new ArrayList<>();

        try {
            JSONObject root = new JSONObject(json);
            JSONObject workshopsObject = root.getJSONObject("workshops");
            JSONArray dataArray = workshopsObject.getJSONArray("data");
            for (int i = 0; i < dataArray.length(); i++) {
                JSONObject dataObject = dataArray.getJSONObject(i);

                Location location = Location.valueOf(dataObject.getString("location"));
                String organizer = dataObject.getString("organizer");
                float price = Float.parseFloat(String.valueOf(dataObject.getDouble("totalPrice")));

                result.add(new Workshop(organizer, location, price));
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

        return result;
    }
}
