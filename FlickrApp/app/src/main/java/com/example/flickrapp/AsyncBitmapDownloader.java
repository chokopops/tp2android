package com.example.flickrapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class AsyncBitmapDownloader extends AsyncTask<String, Void, Bitmap> {

    private String httpUrl;
    private Bitmap bm;

    // Constructor with the url as a parameter

    public AsyncBitmapDownloader(String str){
        this.httpUrl = str;
    }

    // Task done in background of the app

    @Override
    protected Bitmap doInBackground(String... strings) {

        URL url = null;

        try {
            // We will use the url and transform tha data get int oa bitmap
            url = new URL(this.httpUrl);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            bm = BitmapFactory.decodeStream(in);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bm;
    }

    // We will set the imageView within the mainActivity with the bitmap
    // get from the doInBackground
    @Override
    protected void onPostExecute(Bitmap bitmap) {
        MainActivity.setRes(bm);
    }
}
