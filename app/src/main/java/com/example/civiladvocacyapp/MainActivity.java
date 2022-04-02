package com.example.civiladvocacyapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;




public class MainActivity extends AppCompatActivity {

    private RequestQueue mQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mQueue = Volley.newRequestQueue(this);

        currentWeather();

    }


    private void currentWeather(){

        String key = "AIzaSyCP09Gqz8IH7nHZeI7FGigeWNyvhUQrXwk";
        String url ="https://www.googleapis.com/civicinfo/v2/representatives?key="+key+"&address=Chicago";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            String address = "";
                            JSONObject normal = response.getJSONObject("normalizedInput");
                            if(normal.has("line1")){
                                String s1 = normal.getString("line1");
                                address += s1+",";
                            }
                            if(normal.has("city")){
                                String s2 = normal.getString("city");
                                address += s2+",";
                            }
                            if(normal.has("state")){
                                String s3 = normal.getString("state");
                                address += s3+",";
                            }
                            if(normal.has("zip")){
                                String s4 = normal.getString("zip");
                                address += s4;
                            }
                            System.out.println(address);







                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();

            }
        });

        mQueue.add(request);

    }
}