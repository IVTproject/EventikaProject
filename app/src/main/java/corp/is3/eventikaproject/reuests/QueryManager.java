package corp.is3.eventikaproject.reuests;


import android.os.Handler;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/* Класс производящий запрос к серверу*/
public class QueryManager {

    public QueryManager() {

    }

    @Deprecated
    public void query(final String url, final CallbackFunction callbackFunction) {
        query(url, callbackFunction, new Handler());
    }

    public void query(QueryDesigner queryDesigner, CallbackFunction callbackFunction) {
        query(queryDesigner.getURL(), callbackFunction, new Handler());
    }

    private void query(final String url, final CallbackFunction callbackFunction, final Handler handler) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                queryToServer(url, callbackFunction, handler);
            }
        }).start();
    }

    private void queryToServer(final String url, final CallbackFunction callbackFunction, final Handler h) {
        URL u;
        HttpURLConnection conn;
        BufferedReader rd;
        String line;
        String result = "";
        try {
            u = new URL(url);
            conn = (HttpURLConnection) u.openConnection();
            conn.setRequestMethod("GET");
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            while ((line = rd.readLine()) != null) {
                result += line;
            }
            rd.close();
        } catch (Exception e) {
            result = null;
        }
        final String response = result;
        h.post(new Runnable() {
            @Override
            public void run() {
                callbackFunction.callable(response);
            }
        });
    }

}
