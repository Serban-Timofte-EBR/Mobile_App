package eu.ase.ro.a5_car.network;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import eu.ase.ro.a5_car.model.Car;

public class CarParser {
    public static List<Car> parseJSONResponse(String json) {
        List<Car> cars = new ArrayList<>();
        Log.i("NPOINT", "JSON: " + json.toString());
        try {
            JSONObject root = new JSONObject(json);
            JSONObject garageInfo = root.getJSONObject("garageInfo");
            JSONArray carsDetails = garageInfo.getJSONArray("carsDetails");
            for (int i = 0; i < carsDetails.length(); i++) {
                JSONObject carDetailsObject = carsDetails.getJSONObject(i);
                JSONObject carObject = carDetailsObject.getJSONObject("car");

                String brand = carObject.getString("brand");
                String model = carObject.getString("model");
                Integer year = carObject.getInt("fabricationYear");

                cars.add(new Car(brand, model, year));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return cars;
    }
}
