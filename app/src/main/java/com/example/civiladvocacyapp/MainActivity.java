package com.example.civiladvocacyapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

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
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;




public class MainActivity extends AppCompatActivity {

    private RequestQueue mQueue;
    private TextView location;

    private RecyclerView rec;
    private ArrayList<MainRec> mr = new ArrayList<>();
    private MainRecAdapter mrAdapter = new MainRecAdapter(this,mr);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mQueue = Volley.newRequestQueue(this);
        location = findViewById(R.id.location);
        rec = findViewById(R.id.rec);

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
                            String s1 = normal.getString("line1");
                            address += s1+" ";
                            String s2 = normal.getString("city");
                            address += s2+",";
                            String s3 = normal.getString("state");
                            address += s3+" ";
                            String s4 = normal.getString("zip");
                            address += s4;
//                            if(normal.has("line1")){
//                                String s1 = normal.getString("line1");
//                                address += s1+",";
//                            }
//                            if(normal.has("city")){
//                                String s2 = normal.getString("city");
//                                address += s2+",";
//                            }
//                            if(normal.has("state")){
//                                String s3 = normal.getString("state");
//                                address += s3+",";
//                            }
//                            if(normal.has("zip")){
//                                String s4 = normal.getString("zip");
//                                address += s4;
//                            }
                            //System.out.println(address);
                            location.setText(address);

                            ArrayList<String> titles = new ArrayList<>();

                            JSONArray t = response.getJSONArray("offices");
                            ArrayList<Integer> ind = new ArrayList<>();
                            for(int i =0; i < t.length();i++){
                                JSONObject tEntry = t.getJSONObject(i);
                                String title = tEntry.getString("name");
                                titles.add(title);
                                JSONArray indices = tEntry.getJSONArray("officialIndices");
                                //ArrayList<Integer> ind = new ArrayList<>();
                                for(int j =0;j<indices.length();j++) {
                                    int placeholder = indices.getInt(j);
                                    ind.add(placeholder);
                                }
                            }
                            JSONArray officials = response.getJSONArray("officials");
                            for(int z= 0;z<officials.length();z++){
                                JSONObject oEntry = officials.getJSONObject(z);
                                String n = oEntry.getString("name");
                            }
                            for(String tit : titles){
                                MainRec obj = new MainRec(tit,"testname");
                                mr.add(obj);
                            }

                            rec.setAdapter(mrAdapter);
                            rec.setLayoutManager(new LinearLayoutManager(MainActivity.this,RecyclerView.VERTICAL,false));







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