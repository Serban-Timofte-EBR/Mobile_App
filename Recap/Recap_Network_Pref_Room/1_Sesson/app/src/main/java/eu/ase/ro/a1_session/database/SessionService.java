package eu.ase.ro.a1_session.database;

import android.content.Context;

import java.util.List;
import java.util.concurrent.Callable;

import eu.ase.ro.a1_session.model.Session;
import eu.ase.ro.a1_session.network.AsyncTaskRunner;
import eu.ase.ro.a1_session.network.Callback;

public class SessionService {
    public final SessionDao sessionDao;
    private AsyncTaskRunner asyncTaskRunner = new AsyncTaskRunner();

    public SessionService(Context context) {
        sessionDao = DatabaseManager.getInstance(context).getSessionDao();
    }

    public void getAll(Callback<List<Session>> callback) {
        Callable<List<Session>> callable = sessionDao::getAll;
        asyncTaskRunner.executeAsync(callable, callback);
    }

    public void insert(Session session, Callback<Session> callback) {
        Callable<Session> callable = new Callable<Session>() {
            @Override
            public Session call() throws Exception {
                if (session.getId() > 0) {
                    return null;
                }

                long id = sessionDao.insert(session);

                if (id < 0) {
                    return null;
                }

                session.setId(id);
                return session;
            }
        };
        asyncTaskRunner.executeAsync(callable, callback);
    }

    public void update(Session session, Callback<Session> callback) {
        Callable<Session> callable = new Callable<Session>() {
            @Override
            public Session call() throws Exception {
                if (session.getId() < 0) {
                    return null;
                }

                int count = sessionDao.update(session);

                if (count < 1) {
                    return null;
                }

                return session;
            }
        };
        asyncTaskRunner.executeAsync(callable, callback);
    }

    public void delete(Session session, Callback<Boolean> callback) {
        Callable<Boolean> callable = new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                if (session.getId() <= 0) {
                    return null;
                }

                int count = sessionDao.delete(session);

                return count == 1;
            }
        };
        asyncTaskRunner.executeAsync(callable, callback);
    }
}
