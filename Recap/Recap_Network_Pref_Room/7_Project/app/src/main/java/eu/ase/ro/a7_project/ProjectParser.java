package eu.ase.ro.a7_project;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ProjectParser {
    public static List<Project> fromJSON(String json) {
        List<Project> projects = new ArrayList<>();

        try {
            JSONObject root = new JSONObject(json);
            JSONObject projectsObj = root.getJSONObject("projects");
            JSONArray dataArray = projectsObj.getJSONArray("data");
            for (int i = 0; i < dataArray.length(); i++) {
                JSONObject dataObj = dataArray.getJSONObject(i);

                float cost = (float) dataObj.getDouble("cost");
                String company = dataObj.getString("company");

                JSONObject detailsObject = dataObj.getJSONObject("contactDetails");
                String contactPerson = detailsObject.getString("contactPerson");

                projects.add(new Project(company, cost, contactPerson));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return projects;
    }
}
