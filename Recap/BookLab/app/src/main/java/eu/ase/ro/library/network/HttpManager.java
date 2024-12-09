package eu.ase.ro.library.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpManager {
    private final String URL;
    private HttpURLConnection httpURLConnection;

    public HttpManager(String URL) {
        this.URL = URL;
    }

    public String getCall() {
        try {
            httpURLConnection = (HttpURLConnection) new URL(URL).openConnection();

            try (InputStream inputStream = httpURLConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);){
                    String line;
                    StringBuilder result = new StringBuilder();

                    while ((line = bufferedReader.readLine()) != null) {
                        result.append(line);
                    }

                    return result.toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            httpURLConnection.disconnect();
        }
    }
}
