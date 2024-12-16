package eu.ase.ro.a3_event.network;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import eu.ase.ro.a3_event.model.DateConverter;
import eu.ase.ro.a3_event.model.Event;

public class EventParser {
    public static List<Event> fromJSON(String json) {
        List<Event> events = new ArrayList<>();

        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONObject eventsObject = jsonObject.getJSONObject("events");
            JSONArray dataArray = eventsObject.getJSONArray("data");
            for (int i = 0; i < dataArray.length(); i++) {
                JSONObject dataObject = dataArray.getJSONObject(i);

                JSONObject detailsObject = dataObject.getJSONObject("details");
                String title = detailsObject.getString("title");
                Date date = DateConverter.toDate(detailsObject.getString("date"));

                JSONObject participantsObject = detailsObject.getJSONObject("participants");
                Integer nrPersons = participantsObject.getInt("nrOfPersons");

                events.add(new Event(date, title, nrPersons));
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

        return events;
    }
}
