package eu.ase.ro.a4_pacient.database;

import android.content.Context;

import java.util.List;
import java.util.concurrent.Callable;

import eu.ase.ro.a4_pacient.model.Patient;
import eu.ase.ro.a4_pacient.network.AsyncTaskRunner;
import eu.ase.ro.a4_pacient.network.Callback;

public class PatientService {
    private final PatientDao patientDao;
    private AsyncTaskRunner asyncTaskRunner = new AsyncTaskRunner();

    public PatientService(Context context) {
        patientDao = DatabaseManager.getInstance(context).getPatientDao();
    }

    public void getAll(Callback<List<Patient>> callback) {
        Callable<List<Patient>> callable = patientDao::getAll;
        asyncTaskRunner.executeAsync(callable, callback);
    }

    public void insert(Patient patient, Callback<Patient> callback) {
        Callable<Patient> callable = new Callable<Patient>() {
            @Override
            public Patient call() throws Exception {
                if (patient.getId() > 0) {
                    return null;
                }

                long id = patientDao.insert(patient);

                if (id < 0) {
                    return null;
                }

                patient.setId(id);
                return patient;
            }
        };
        asyncTaskRunner.executeAsync(callable, callback);
    }

    public void update(Patient patient, Callback<Patient> callback) {
        Callable<Patient> callable = new Callable<Patient>() {
            @Override
            public Patient call() throws Exception {
                if (patient.getId() <= 0) {
                    return null;
                }

                int result = patientDao.update(patient);

                if (result < 1) {
                    return null;
                }

                return patient;
            }
        };
        asyncTaskRunner.executeAsync(callable, callback);
    }

    public void delete(Patient patient, Callback<Boolean> callback) {
        Callable<Boolean> callable = new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                if (patient.getId() <= 0) {
                    return false;
                }

                int count = patientDao.delete(patient);

                return count == 1;
            }
        };
        asyncTaskRunner.executeAsync(callable, callback);
    }
}
