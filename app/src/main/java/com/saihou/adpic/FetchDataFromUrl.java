package com.saihou.adpic;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by saihou on 2/25/16.
 */
public class FetchDataFromUrl extends AsyncTask<String, String, JSONArray> {

    Activity callingActivity;
    Fragment callingFragment;
    String TAG = "FetchDataFromUrlInBackground";

    public FetchDataFromUrl(Activity callingActivity, Fragment callingFragment) {
        this.callingActivity = callingActivity;
        this.callingFragment = callingFragment;
    }

    @Override
    protected JSONArray doInBackground(String... params) {

        Log.d(TAG, "Calling Activity: " + callingActivity.toString());
        Log.d(TAG, "Calling Fragment: " + callingFragment.toString());
        Log.d(TAG, "Connecting to " + params[0]);

        JSONArray result = null;
        try {
            URL url = new URL(params[0]);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String inputLine;
            String concatResponse = "";
            while ((inputLine = in.readLine()) != null)
                concatResponse += inputLine;
            in.close();

            result = new JSONArray(concatResponse);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    protected void onPostExecute(JSONArray jsonArray) {
        System.out.println(jsonArray);
        super.onPostExecute(jsonArray);
    }
}
