package eu.ase.ro.a5_car.database;

import android.content.Context;
import android.telecom.Call;

import java.util.List;
import java.util.concurrent.Callable;

import eu.ase.ro.a5_car.model.Car;
import eu.ase.ro.a5_car.network.AsyncTaskRunner;
import eu.ase.ro.a5_car.network.Callback;

public class CarService {
    private final CarDao carDao;
    private AsyncTaskRunner asyncTaskRunner = new AsyncTaskRunner();

    public CarService(Context context) {
        this.carDao = DatabaseManager.getInstance(context).getCarDao();
    }

    public void getAll(Callback<List<Car>> callback) {
        Callable<List<Car>> callable = carDao::getAll;
        asyncTaskRunner.executeAsync(callable, callback);
    }

    public void insert(Car car, Callback<Car> callback) {
        Callable<Car> callable = new Callable<Car>() {
            @Override
            public Car call() throws Exception {
                if (car.getId() > 0) {
                    return null;
                }

                long id = carDao.insert(car);

                if (id < 0) {
                    return null;
                }

                car.setId(id);
                return car;
            }
        };
        asyncTaskRunner.executeAsync(callable, callback);
    }

    public void delete(Car car, Callback<Boolean> callback) {
        Callable<Boolean> callable = new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                if (car.getId() <= 0) {
                    return null;
                }

                int count = carDao.delete(car);

                return count == 1;
            }
        };
        asyncTaskRunner.executeAsync(callable, callback);
    }
}
