package eu.ase.ro.a3_event.database;

import android.content.Context;
import android.telecom.Call;

import java.util.List;
import java.util.concurrent.Callable;

import eu.ase.ro.a3_event.model.Event;
import eu.ase.ro.a3_event.network.AsyncTaskRunner;
import eu.ase.ro.a3_event.network.Callback;

public class EventService {
    private final EventsDao eventsDao;
    private AsyncTaskRunner asyncTaskRunner = new AsyncTaskRunner();

    public EventService(Context context) {
        eventsDao = DatabaseManager.getInstance(context).getEventDao();
    }

    public void getAll(Callback<List<Event>> callback) {
        Callable<List<Event>> callable = eventsDao::getAll;
        asyncTaskRunner.executeAsync(callable, callback);
    }

    public void insert(Event event, Callback<Event> callback) {
        Callable<Event> callable = new Callable<Event>() {
            @Override
            public Event call() throws Exception {
                if (event.getId() > 0) {
                    return null;
                }

                long id = eventsDao.insert(event);
                event.setId(id);

                if (event.getId() < 0) {
                    return null;
                }

                return event;
            }
        };
        asyncTaskRunner.executeAsync(callable, callback);
    }
}
