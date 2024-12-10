package eu.ase.ro.damapp.network;

import androidx.annotation.NonNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public class HttpManager implements Callable<String> {

    private HttpURLConnection httpURLConnection;
    private InputStream inputStream;
    private InputStreamReader inputStreamReader;
    private BufferedReader bufferedReader;

    private final String url;

    public HttpManager(String url) {
        this.url = url;
    }

    @Override
    public String call() {
        try {
            String res = getContentFromURL();
           return res;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeConnections();
        }

        return null;
    }

    @NonNull
    private String getContentFromURL() throws IOException {
        httpURLConnection = (HttpURLConnection) new URL(url).openConnection();
        inputStream = httpURLConnection.getInputStream();
        inputStreamReader = new InputStreamReader(inputStream);
        bufferedReader = new BufferedReader(inputStreamReader);

        String line;
        StringBuilder result = new StringBuilder();

        while ((line = bufferedReader.readLine()) != null){
            result.append(line);
        }

        return result.toString();
    }

    private void closeConnections() {
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
            throw new RuntimeException(e);
        }
        httpURLConnection.disconnect();
    }
}
