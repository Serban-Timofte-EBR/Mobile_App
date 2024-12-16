package eu.ase.ro.a4_pacient.network;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import eu.ase.ro.a4_pacient.model.Patient;

public class PatientParser {
    public static List<Patient> parseJSON(String json) {
        List<Patient> result = new ArrayList<>();

        try {
            JSONObject root = new JSONObject(json);
            JSONObject patientsObject = root.getJSONObject("patients");
            JSONArray dataArray = patientsObject.getJSONArray("data");
            for (int i = 0; i < dataArray.length(); i++) {
                JSONObject arrayObject = dataArray.getJSONObject(i);
                JSONObject detailsObject = arrayObject.getJSONObject("details");

                String name = detailsObject.getString("name");
                Integer nrOfCons = detailsObject.getInt("numberOfConsultations");

                JSONObject hospitalObject = detailsObject.getJSONObject("hospital");
                String hospitalName = hospitalObject.getString("name");

                result.add(new Patient(name, hospitalName, nrOfCons));
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

        return result;
    }
}
