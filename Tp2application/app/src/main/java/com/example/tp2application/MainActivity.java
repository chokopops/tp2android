package com.example.tp2application;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private TextView result;
    private Button login;
    private EditText username;
    private EditText password;
    private JSONObject myJSONObject;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Attributing to every variables the corresponding element in the layout

        login = (Button)findViewById(R.id.Login);
        username = (EditText)findViewById(R.id.Username);
        password = (EditText)findViewById(R.id.Password);
        result = (TextView)findViewById(R.id.Result);

        // Creation of the listener on the login button

        login.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                // When the button is pressed, it will create a new thread
                new Thread(){

                    @Override
                    public void run(){

                        URL url =null;
                        try {

                            // Creating the connection to the url and
                            // giving the credentials entered in the app

                            url = new URL("https://httpbin.org/basic-auth/bob/sympa");
                            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                            String credentials = username.getText()+":"+password.getText();
                            System.out.println(credentials);
                            String basicAuth = "Basic " + Base64.encodeToString(credentials.getBytes(),Base64.NO_WRAP);
                            urlConnection.setRequestProperty ("Authorization", basicAuth);
                            try {

                                // We use the readStream function to get the data from the url
                                // and to convert it to a JSON object

                                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                                String s = readStream(in);
                                Log.i("JFL", s);
                                myJSONObject = new JSONObject(s);
                                String res = myJSONObject.getString("authenticated");
                                setRes(res);
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
                    }
                }.start();
            }
        });
    }

    // Function used to modify the TextView because it is not accessible without
    // the runOnUiThread and the run() override

    private void setRes(String s){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                result.setText(s);
            }
        });
    }

    // This function will parse the data and return a stream with the data contained within the url

    private String readStream(InputStream is) {
        try {
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            int i = is.read();
            while(i != -1) {
                bo.write(i);
                i = is.read();
            }
            return bo.toString();
        } catch (IOException e) {
            return "";
        }
    }
}