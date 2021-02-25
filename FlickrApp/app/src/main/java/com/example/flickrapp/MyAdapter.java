package com.example.flickrapp;

import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.toolbox.ImageRequest;

import org.w3c.dom.Text;

import java.util.Vector;

public class MyAdapter extends BaseAdapter {

    private Vector<String> vector;

    public MyAdapter(){
        this.vector = new Vector<String>();
    }

    @Override
    public int getCount() {
        return vector.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
//        ConvertView qui permet d'afficher tous les urls sur l'écran
//        if (convertView == null) {
//            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
//            convertView = inflater.inflate(R.layout.textviewlayout, parent, false);
//            ((TextView)convertView.findViewById(R.id.textView2)).setText(vector.get(position));
//        }

        // loading layouts in advance to prevent an eventual scroll
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            convertView = inflater.inflate(R.layout.bitmaplayout, parent, false);
        }

        // Creation of the imageView to use it within the lambda expression.
        // If this imageView is instanciate in the lambda expression, it doesn't work
        ImageView img = (ImageView)convertView.findViewById(R.id.imageView);

        // Creation of the bitmap used to load images
        Response.Listener<Bitmap> rep_listener = response -> {
            img.setImageBitmap(response);
        };

        // Cration of an ImageRequest containing image url position in the vector and use of the bitmap created with the response listener
        ImageRequest request = new ImageRequest(vector.get(position), rep_listener,  0, 0, ImageView.ScaleType.CENTER_CROP, null, null);

        // Adding to the singleton image data we want to load
        MySingleton.getInstance(parent.getContext()).addToRequestQueue(request);

        return convertView;
    }

    public void dd(String str){
        this.vector.add(str);
    }
}
