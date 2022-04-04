package com.example.civiladvocacyapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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




public class MainActivity extends AppCompatActivity implements SelectListener {

    private RequestQueue mQueue;
    private TextView location;

    private RecyclerView rec;
    private ArrayList<MainRec> mr = new ArrayList<>();
    private MainRecAdapter mrAdapter = new MainRecAdapter(this,mr,this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mQueue = Volley.newRequestQueue(this);
        location = findViewById(R.id.location);
        rec = findViewById(R.id.rec);

        getSupportActionBar().setTitle("Know Your Government");

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.menu_location){
            enterLocDialog(this);
            Toast.makeText(this, "Location menu opened", Toast.LENGTH_SHORT).show();
        }
        else if (item.getItemId() == R.id.menu_info){
            Intent intent = new Intent(MainActivity.this,aboutActivity.class);
            startActivity(intent);
            Toast.makeText(this, "Info menu opened", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClicked(MainRec mr) {
        Toast.makeText(this, "Item Selected" + mr.getName(), Toast.LENGTH_SHORT).show();
    }

    private void enterLocDialog(Context c) {
        final EditText taskEditText = new EditText(c);
        AlertDialog dialog = new AlertDialog.Builder(c)
                .setTitle("Enter Address")
                .setView(taskEditText)
                .setPositiveButton("Okay ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String task = String.valueOf(taskEditText.getText());
                        location.setText(task);

                    }
                })
                .setNegativeButton("Cancel", null)
                .create();
        dialog.show();
    }
}