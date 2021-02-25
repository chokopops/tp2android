package com.example.flickrapp;

import android.view.View;

public class GetImageOnClickListener implements View.OnClickListener {
    // Class created to override the onClick listener and redirect to an
    // asynchronous task
    @Override
    public void onClick(View v) {
        try{
            AsyncFlickrJSONData task = new AsyncFlickrJSONData("https://www.flickr.com/services/feeds/photos_public.gne?tags=trees&format=json");
            task.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
