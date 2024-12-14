package eu.ase.ro.a1_session.network;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import eu.ase.ro.a1_session.model.DateConvertor;
import eu.ase.ro.a1_session.model.Session;

public class SessionParser {
    public static List<Session> fromJSON(String result) {
        List<Session> results = new ArrayList<>();
        try {
            JSONObject root = new JSONObject(result);
            JSONObject conferenceObject = root.getJSONObject("conference");
            JSONArray sessionsArray = conferenceObject.getJSONArray("sessions");
            for (int i = 0; i < sessionsArray.length(); i++) {
                JSONObject jsonObject = sessionsArray.getJSONObject(i);

                long id = jsonObject.getLong("id");
                Date date = DateConvertor.toDate(jsonObject.getString("date"));
                String room = jsonObject.getString("room");
                String title = jsonObject.getString("title");
                String speaker = jsonObject.getString("speaker");
                int duration = jsonObject.getInt("duration");

                Session session = new Session(title, date, speaker, duration, room);
                results.add(session);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return results;
    }
}
