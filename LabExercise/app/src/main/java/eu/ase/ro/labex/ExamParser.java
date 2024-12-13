package eu.ase.ro.labex;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import eu.ase.ro.labex.Exam;

public class ExamParser {

    // Formatul datei utilizat în JSON
    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm";

    public static List<Exam> fromJson(String jsonString) {
        List<Exam> exams = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        try {
            // Parse the root JSON object
            JSONObject root = new JSONObject(jsonString);

            // Get the "values" array
            JSONArray values = root.getJSONArray("values");

            // Iterate over the "values" array
            for (int i = 0; i < values.length(); i++) {
                JSONObject valueObject = values.getJSONObject(i);

                // Get the nested "data" object
                if (!valueObject.has("data")) {
                    continue; // Skip if "data" is missing
                }
                JSONObject dataObject = valueObject.getJSONObject("data");

                // Extract fields from the "data" object
                String dateStr = dataObject.optString("date", null);
                String courseStr = dataObject.optString("course", null);
                String classroom = dataObject.optString("classroom", null);
                String supervisor = dataObject.optString("supervisor", null);

                // Skip entries with missing mandatory fields
                if (dateStr == null || courseStr == null || classroom == null || supervisor == null) {
                    continue;
                }

                // Parse date
                Date date = null;
                try {
                    date = dateFormat.parse(dateStr);
                } catch (ParseException e) {
                    e.printStackTrace();
                    continue; // Skip invalid date formats
                }

                // Parse course as enum
                Course course = null;
                try {
                    course = Course.valueOf(courseStr);
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                    continue; // Skip unknown courses
                }

                // Create an Exam object and add it to the list
                Exam exam = new Exam(course, date, classroom, supervisor);
                exams.add(exam);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return exams;
    }

}