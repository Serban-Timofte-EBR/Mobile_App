package eu.ase.ro.a2_exam.network;

import android.os.Handler;
import android.os.Looper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HttpManager implements Callable<String> {
    private final String NPOINT_URL;
    private HttpURLConnection httpURLConnection;

    public HttpManager(String NPOINT_URL) {
        this.NPOINT_URL = NPOINT_URL;
    }

    @Override
    public String call() {
        try {
            httpURLConnection = (HttpURLConnection) new URL(NPOINT_URL).openConnection();

            try (InputStream inputStream = httpURLConnection.getInputStream();
                 InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                 BufferedReader bufferedReader = new BufferedReader(inputStreamReader))
            {
                String line;
                StringBuilder result = new StringBuilder();
                while ((line = bufferedReader.readLine()) != null) {
                    result.append(line);
                }
                return result.toString();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            httpURLConnection.disconnect();
        }
    }
}
