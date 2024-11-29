package eu.ase.ro.andreitest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpManager {
    private static final String URL = "YEss";

    public static String getCall() throws IOException {
        HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(URL).openConnection();

        try (InputStream inputStream = httpURLConnection.getInputStream();
             InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
             BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {

            String line;
            StringBuilder result = new StringBuilder();
            while ((line = bufferedReader.readLine()) != null) {
                result.append(line);
            }

            return result.toString();
        } finally {
            httpURLConnection.disconnect();
        }
    }
}
