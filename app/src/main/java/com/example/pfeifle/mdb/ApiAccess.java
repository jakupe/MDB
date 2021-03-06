package com.example.pfeifle.mdb;

import android.os.AsyncTask;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;


/**
 * Created by Pfeifle on 15.03.2018.
 */

public class ApiAccess extends AsyncTask<String, Void, JSONObject> {
    private JSONObject jo = null;
    public ApiResponse ar;

    public ApiAccess(ApiResponse ar) {
        this.ar = ar;
    }

    @Override
    protected JSONObject doInBackground(String... strings) {
        try {
            // get Request-Factory
            HttpTransport ht = new NetHttpTransport();
            HttpRequestFactory rf = ht.createRequestFactory();

            // generate URL
            GenericUrl url = new GenericUrl(strings[0]);

            // setting down the request
            HttpRequest req = rf.buildGetRequest(url);
            HttpResponse hr = req.execute();

            // get String
            String js = hr.parseAsString();

            // parse JSON
            jo = new JSONObject(js);
        }
        catch (IOException e)   { e.printStackTrace(); }
        catch (JSONException e) { e.printStackTrace(); }
        return jo;
    }

    @Override
    protected void onPostExecute(JSONObject jo) {
        ar.finish(jo);
    }

}
