package eu.ase.ro.damapp.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpManager {
    private HttpURLConnection httpURLConnection;
    private InputStream inputStream;
    private InputStreamReader inputStreamReader;
    private BufferedReader bufferedReader;
    private final String url;

    public HttpManager(String url) {
        this.url = url;
    }

    // Deschidem conexiunea si citim informatia
    public String call() {
        try {
            httpURLConnection = (HttpURLConnection) new URL(url).openConnection();
            inputStream = httpURLConnection.getInputStream();
            inputStreamReader = new InputStreamReader(inputStream);
            bufferedReader = new BufferedReader(inputStreamReader);

            //citim linie cu linie de la endpoint
            String line;
            StringBuilder result = new StringBuilder();
            while( (line = bufferedReader.readLine()) != null) {
                result.append(line);
            }

            return result.toString();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // putem si try with resources

            try {
                bufferedReader.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            try {
                inputStreamReader.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            try {
                inputStream.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            httpURLConnection.disconnect();
        }

        return null;
    }
}
