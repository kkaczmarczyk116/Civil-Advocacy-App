package com.example.civiladvocacyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class OfficialActivity extends AppCompatActivity {

    private TextView official_staff;
    private TextView official_name;
    private TextView official_loc;
    private TextView official_party;
    private ImageView official_pic;
    private Picasso picasso;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_official);

        picasso = picasso.get();
        picasso.setLoggingEnabled(true);

        official_staff = findViewById(R.id.official_staff);
        official_name = findViewById(R.id.official_name);
        official_loc = findViewById(R.id.official_loc);
        official_party = findViewById(R.id.official_party);
        official_pic = findViewById(R.id.official_pic);


//        picasso.load("https://bioguide.congress.gov/bioguide/photo/D/D000563.jpg")
//                //.resize(225,275)
//                //.centerCrop()
//                .into(official_pic);
        SharedPreferences sp = getSharedPreferences("prefs",MODE_PRIVATE);
        String s1 = sp.getString("picUrl","");

        Intent intent = getIntent();
        if(intent != null) {
            String title = intent.getStringExtra("title");
            String name = intent.getStringExtra("name");
            String party = intent.getStringExtra("party");
            String imgLink = intent.getStringExtra("img");
            official_loc.setText(imgLink);
            setOfficials(title,name,party,imgLink);
            //String test = String.valueOf(intent.getStringExtra("img"));
//            if(test.equals("none")){
//                setOfficials(title,name,party);
//                picasso.load("https://bioguide.congress.gov/bioguide/photo/D/D000563.jpg").into(official_pic);
//            }else{
//                setOfficials(title, name, party);
//                official_loc.setText(s1);
//                picasso.load(s1).into(official_pic);
//                //picasso.load(imgLink).into(official_pic);
//            }
            //setOfficials(title, name, party, imgLink);


        }
    }

    private void setOfficials(String t,String n,String p,String img){
        official_staff.setText(t);
        official_name.setText(n);
        official_party.setText("("+ p+" )");
        String s1 = img.replace("http","https");
        picasso.load(s1)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.brokenimage)
                .into(official_pic);
//        picasso.load(img)
//                .placeholder(R.drawable.placeholder)
//                .into(official_pic);
//        if(i != "none"){
//            Picasso.get()
//                .load(i)
//                .placeholder(R.drawable.placeholder)
//                .error(R.drawable.brokenimage)
//                .into(official_pic);
//        }
//        picasso.load(img)
//                .error(R.drawable.brokenimage)
//                .placeholder(R.drawable.placeholder)
//                .fit().centerCrop()
//                .into(official_pic);
//        Picasso.get()
//                .load(img)
//                .placeholder(R.drawable.placeholder)
//                .error(R.drawable.brokenimage)
//                .into(official_pic);
    }
}