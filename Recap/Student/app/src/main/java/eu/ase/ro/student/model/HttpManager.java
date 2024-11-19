package eu.ase.ro.student.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpManager {
    private HttpURLConnection httpURLConnection;
    private InputStream inputStream;
    private InputStreamReader inputStreamReader;
    private BufferedReader bufferedReader;
    private final String URL;

    public HttpManager(String URL) {
        this.URL = URL;
    }

    public String getCall() {
        try {
            httpURLConnection = (HttpURLConnection) new URL(URL).openConnection();
            inputStream = httpURLConnection.getInputStream();
            inputStreamReader = new InputStreamReader(inputStream);
            bufferedReader = new BufferedReader(inputStreamReader);

            String line;
            StringBuilder result = new StringBuilder();

            while ((line = bufferedReader.readLine()) != null) {
                result.append(line);
            }

            return result.toString();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                inputStreamReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            httpURLConnection.disconnect();
        }
    }
}
