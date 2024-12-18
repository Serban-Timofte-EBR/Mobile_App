package eu.ase.ro.a6_costum.network;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import eu.ase.ro.a6_costum.model.Costume;
import eu.ase.ro.a6_costum.model.Size;

public class CostumeParser {
    public static List<Costume> parseJSONResponse(String json) {
        List<Costume> costumes = new ArrayList<>();

        try {
            JSONObject root = new JSONObject(json);
            JSONObject costumesObject = root.getJSONObject("costumes");
            JSONArray dataArray = costumesObject.getJSONArray("data");
            for (int i = 0; i < dataArray.length(); i++) {
                JSONObject currObject = dataArray.getJSONObject(i);
                String manufacter = currObject.getString("manufacter");

                JSONObject detailsObject = currObject.getJSONObject("details");
                Size size = Size.valueOf(detailsObject.getString("size"));

                JSONObject discountObject = detailsObject.getJSONObject("price");
                Float price = (float) discountObject.getDouble("value");

                costumes.add(new Costume(manufacter, size, price));
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

        return costumes;
    }
}
