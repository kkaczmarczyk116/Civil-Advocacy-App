package com.example.civiladvocacyapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
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
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class MainActivity extends AppCompatActivity implements SelectListener {

    private RequestQueue mQueue;
    private TextView locationTexeView;

    private RecyclerView rec;
    private ArrayList<MainRec> mr = new ArrayList<>();
    private MainRecAdapter mrAdapter = new MainRecAdapter(this,mr,this);
    private ArrayList<String> test = new ArrayList<>();
    private FusedLocationProviderClient mFusedLocationClient;
    private static final int LOCATION_REQUEST = 111000;
    private int checkError;
    private static String locationString = "Unspecified Location";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mQueue = Volley.newRequestQueue(this);
        locationTexeView = findViewById(R.id.location);
        rec = findViewById(R.id.rec);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        determineLocation();

        getSupportActionBar().setTitle("Know Your Government");



    }
    //TODO: Figure out error handling for entering bad location

    @SuppressLint("MissingPermission")
    private void determineLocation() {
        if (checkAppPermissions()) {
            mFusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, location -> {
                        if (location != null) {
                            locationString = getLoc(location);
                            locationTexeView.setText(locationString);
                            getInfo(locationString);
                        }
                    })
                    .addOnFailureListener(this, e -> Toast.makeText(MainActivity.this,
                            e.getMessage(), Toast.LENGTH_LONG).show());
        }

    }

    private String getLoc(Location location) {
        StringBuilder sb = new StringBuilder();

        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> addresses;

        try {
            addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            String city = addresses.get(0).getLocality();
            String state = addresses.get(0).getAdminArea();
            sb.append(String.format(
                    Locale.getDefault(),
                    "%s,%s",
                    city, state));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }


    private boolean checkAppPermissions() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{
                            Manifest.permission.ACCESS_FINE_LOCATION
                    }, LOCATION_REQUEST);
            return false;
        }
        return true;


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_REQUEST) {
            if (permissions[0].equals(Manifest.permission.ACCESS_FINE_LOCATION)) {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    determineLocation();
                } else {
                    locationTexeView.setText("Permission Was Not Granted");
                }
            }
        }
    }

    //TODO: picture handling
    //TODO: get current location
    //TODO: color coding based on party,check for "unknown party"


    private void getInfo(String l){
        checkError =0;
        String key = "AIzaSyCP09Gqz8IH7nHZeI7FGigeWNyvhUQrXwk";
        String url ="https://www.googleapis.com/civicinfo/v2/representatives?key="+key+"&address="+l;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {


                            String address = " ";

                            JSONObject normal = response.getJSONObject("normalizedInput");
                            String s1 = normal.getString("line1");
                            address += s1+" ";
                            String s2 = normal.getString("city");
                            address += s2+",";
                            String s3 = normal.getString("state");
                            address += s3+" ";
                            String s4 = normal.getString("zip");
                            address += s4;
                            locationTexeView.setText(address);

                            JSONArray t = response.getJSONArray("offices");
                            for(int i =0; i < t.length();i++){
                                JSONObject tEntry = t.getJSONObject(i);
                                String title = tEntry.getString("name");
                                JSONArray indices = tEntry.getJSONArray("officialIndices");
                                for(int j =0;j<indices.length();j++) {
                                    String pic = "none";
                                    String teleObj = "none";
                                    String website = "none";
                                    String addyObj = " ";
                                    JSONArray mapOfficials = response.getJSONArray("officials");
                                    JSONObject officialObj = mapOfficials.getJSONObject(indices.getInt(j));
                                    String officialName = officialObj.getString("name");
                                    String officialParty = officialObj.getString("party");
                                    if(officialObj.has("photoUrl")){
                                        pic = officialObj.getString("photoUrl");
                                    }

                                    if(officialObj.has("address")){
                                        JSONArray addy = officialObj.getJSONArray("address");

                                        JSONObject zero = (JSONObject) addy.get(0);

                                        if(zero.has("line1")){
                                            String line1 = zero.getString("line1");
                                            addyObj+=line1+ " ";
                                        }
                                        if(zero.has("city")){
                                            String city = zero.getString("city");
                                            addyObj+=city+ ", ";
                                        }
                                        if(zero.has("state")){
                                            String state = zero.getString("state");
                                            addyObj +=state+ " ";
                                        }
                                        if(zero.has("zip")){
                                            String zip = zero.getString("zip");
                                            addyObj+=zip;
                                        }

                                    }else{
                                        addyObj = "address not found";
                                    }


                                    if(officialObj.has("phones")){
                                        JSONArray tele = officialObj.getJSONArray("phones");
                                        if(tele.length() != 0){
                                            teleObj = tele.getString(0);
                                        }
                                    }else{
                                        teleObj = "none";
                                    }
                                    if(officialObj.has("urls")){
                                        JSONArray urls = officialObj.getJSONArray("urls");
                                        if(urls.length() != 0){
                                            website = urls.getString(0);
                                        }
                                    }else{
                                        website="none";
                                    }
                                    if(officialObj.has("channels")){
                                        JSONArray channels = officialObj.getJSONArray("channels");
                                        if(channels.length() != 0){
                                            for(int c=0;c< channels.length();c++){
                                                JSONObject ch = channels.getJSONObject(c);
                                                if(ch.has("type")){
                                                    String type = ch.getString("type");
                                                    test.add(type);
                                                }else{
                                                    String type = "none";
                                                    test.add(type);
                                                }
                                                if(ch.has("id")){
                                                    String id = ch.getString("id");
                                                    test.add(id);
                                                }else{
                                                    String id = "none";
                                                    test.add(id);
                                                }
                                            }

                                        }
                                        while(test.size() < 6){
                                            test.add("none");
                                        }

                                        String ch1Type = test.get(0);
                                        String ch1Id= test.get(1);
                                        String ch2Type= test.get(2);
                                        String ch2Id= test.get(3);
                                        String ch3Type= test.get(4);
                                        String ch3Id= test.get(5);
                                        test.clear();
                                        Channels chanObj = new Channels(ch1Type,ch1Id,ch2Type,ch2Id,ch3Type,ch3Id);
                                        MainRec obj = new MainRec(title,officialName,officialParty,pic,addyObj,teleObj,website,chanObj);
                                        mr.add(obj);
                                        test.clear();
                                    }


                                }
                            }
                            rec.setAdapter(mrAdapter);
                            rec.setLayoutManager(new LinearLayoutManager(MainActivity.this,RecyclerView.VERTICAL,false));

                        } catch (JSONException e) {
                            checkError = 1;
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                checkError=1;
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

        }
        else if (item.getItemId() == R.id.menu_info){
            Intent intent = new Intent(MainActivity.this,aboutActivity.class);
            startActivity(intent);

        }
        else{
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClicked(MainRec mr) {


        Intent intent = new Intent(MainActivity.this,OfficialActivity.class);
        intent.putExtra("title",mr.getTitle());
        intent.putExtra("name",mr.getName());
        intent.putExtra("party",mr.getParty());
        String link = mr.getPicurl();
        intent.putExtra("img",link);
        intent.putExtra("location",locationString);
        intent.putExtra("addy",mr.getAddress());
        intent.putExtra("tele",mr.getPhone());
        intent.putExtra("web",mr.getWebsite());
        intent.putExtra("ch1Type",mr.getCh1Type());
        intent.putExtra("ch1Id",mr.getCh1Id());
        intent.putExtra("ch2Type",mr.getCh2Type());
        intent.putExtra("ch2Id",mr.getCh2Id());
        intent.putExtra("ch3Type",mr.getCh3Type());
        intent.putExtra("ch3Id",mr.getCh3Id());

        startActivity(intent);
    }

    private void enterLocDialog(Context c) {
        final EditText taskEditText = new EditText(c);
        AlertDialog dialog = new AlertDialog.Builder(c)
                .setTitle("Enter Address\n"+"( City, State ) or Zip Code")
                .setView(taskEditText)
                .setPositiveButton("Okay ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String priorLoc = locationTexeView.getText().toString();
                        String task = String.valueOf(taskEditText.getText());
                        mr.clear();
                        getInfo(task);

//                            if(mr.isEmpty()){
//                                locationTexeView.setText(priorLoc);
//                                getInfo(priorLoc);
//                                Toast.makeText(c, "Location Not Found", Toast.LENGTH_SHORT).show();
//                                enterLocDialog(c);
//                            }else{
//                                getInfo(task);
//                            }
//                            locationTexeView.setText(task);
//                            mr.clear();
//                            getInfo(locationTexeView.getText().toString());



                    }
                })
                .setNegativeButton("Cancel", null)
                .create();
        dialog.show();

    }



    }