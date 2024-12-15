package eu.ase.ro.a2_exam.network;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import eu.ase.ro.a2_exam.model.Course;
import eu.ase.ro.a2_exam.model.DateConvertor;
import eu.ase.ro.a2_exam.model.Exam;

public class ExamParser {
    public static List<Exam> fromJSON(String result) {
        List<Exam> exams = new ArrayList<>();

        try {
            JSONObject root = new JSONObject(result);
            JSONArray valuesArray = root.getJSONArray("values");
            for (int i = 0; i < valuesArray.length(); i++) {
                JSONObject valueObject = valuesArray.getJSONObject(i);
                JSONObject data = valueObject.getJSONObject("data");

                Date date = DateConvertor.toDate(data.getString("date"));
                Course course = Course.valueOf(data.getString("course"));
                String classRoom = data.getString("classroom");
                String supervisor = data.getString("supervisor");

                exams.add(new Exam(date, course, classRoom, supervisor));
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        return exams;
    }
}
