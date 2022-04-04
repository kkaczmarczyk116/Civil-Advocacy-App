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

        getInfo();

    }

    //TODO: get more info from API and make obj?
    //TODO: picture handling
    //TODO: get current location
    //TODO: color coding based on party,check for "unknown party"

    private void getInfo(){

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
                            location.setText(address);

                            ArrayList<String> titles = new ArrayList<>();

                            JSONArray t = response.getJSONArray("offices");
                            ArrayList<Integer> ind = new ArrayList<>();


                            for(int i =0; i < t.length();i++){
                                JSONObject tEntry = t.getJSONObject(i);
                                String title = tEntry.getString("name");
                                titles.add(title);
                                JSONArray indices = tEntry.getJSONArray("officialIndices");
                                for(int j =0;j<indices.length();j++) {
                                    JSONArray mapOfficials = response.getJSONArray("officials");
                                    JSONObject officialObj = mapOfficials.getJSONObject(indices.getInt(j));
                                    String officialName = officialObj.getString("name");
                                    String officialParty = officialObj.getString("party");
                                    MainRec obj = new MainRec(title,officialName+ " ("+officialParty+") ");
                                    mr.add(obj);
                                }
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