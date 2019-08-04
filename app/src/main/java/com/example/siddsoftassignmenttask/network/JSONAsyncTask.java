package com.example.siddsoftassignmenttask.network;

import android.os.AsyncTask;

import com.example.siddsoftassignmenttask.interfaceasmt.AssignmentInerface;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class JSONAsyncTask extends AsyncTask<String, Void, JSONObject> {

    AssignmentInerface assignmentInerface;


    public JSONAsyncTask(AssignmentInerface assignmentInerface) {
        this.assignmentInerface = assignmentInerface;
        System.out.println("doInBackground JSONAsyncTask");
    }

    @Override
    public JSONObject doInBackground(String... strings) {
        System.out.println("doInBackground");
        try {
            HttpGet httppost = new HttpGet(strings[0]);
            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse response = httpclient.execute(httppost);
            // StatusLine stat = response.getStatusLine();
            int status = response.getStatusLine().getStatusCode();
            if (status == 200) {
                HttpEntity entity = response.getEntity();
                String data = EntityUtils.toString(entity);
                JSONObject jsono = new JSONObject(data);
                return jsono;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {

            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected void onPostExecute(JSONObject jsonObject) {
        System.out.println("doInBackground onPostExecute");
        if (jsonObject != null) {
            assignmentInerface.wikiCallBace(jsonObject);
        }
        super.onPostExecute(jsonObject);
    }
}