package com.example.flickrapp;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class AsyncFlickrJSONDataForList extends AsyncTask<String, Void, JSONObject> {

    private String httpUrl;
    private JSONObject myJSONObject;
    private MyAdapter myAdapter;

    public AsyncFlickrJSONDataForList(String httpUrl, MyAdapter myAdapter){
        this.httpUrl = httpUrl;
        this.myAdapter = myAdapter;
    }

    @Override
    protected JSONObject doInBackground(String... strings) {
        URL url =null;
        try {
            url = new URL(this.httpUrl);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            try {
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                String s = readStream(in);
                myJSONObject = new JSONObject(s);
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                urlConnection.disconnect();
            }
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return myJSONObject;
    }

    @Override
    protected void onPostExecute(JSONObject myJSONObject) {
        try {
            JSONArray items = myJSONObject.getJSONArray("items");
            for(int i = 0; i < items.length(); i++) {
                String urlImage = items.getJSONObject(i).getJSONObject("media").getString("m");
                Log.i("TE", urlImage);
                myAdapter.dd(urlImage);
                Log.i("JFL", "Adding to adapter url : " + urlImage);
//                AsyncBitmapDownloaderForList bmdownloader = new AsyncBitmapDownloaderForList(urlImage);
//                bmdownloader.execute();
            }
            myAdapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private String readStream(InputStream is) {
        try {
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            int i = is.read();
            while(i != -1) {
                bo.write(i);
                i = is.read();
            }
            return bo.toString().replace("jsonFlickrFeed(","");
        } catch (IOException e) {
            return "";
        }
    }
}
