package com.example.civiladvocacyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class PhotoDetail extends AppCompatActivity {

    private TextView details_location;
    private TextView details_staff;
    private TextView details_name;
    private ImageView details_img;
    private ImageView details_logo;
    private Picasso picasso;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_detail);

        details_location =findViewById(R.id.details_location);
        details_staff = findViewById(R.id.details_staff);
        details_name = findViewById(R.id.details_name);
        details_img = findViewById(R.id.details_img);
        details_logo = findViewById(R.id.details_logo);


        picasso = picasso.get();
        picasso.setLoggingEnabled(true);

        Intent intent = getIntent();
        if(intent != null){
            String location = intent.getStringExtra("loc");
            String party = intent.getStringExtra("party");
            String img = intent.getStringExtra("photo");
            String title = intent.getStringExtra("title");
            String name = intent.getStringExtra("name");

            setOfficials(title,name,location,img,party);

        }
    }
    private void setOfficials(String t,String n,String l,String img,String p){
        details_staff.setText(t);
        details_name.setText(n);
        details_location.setText(l);
        String s1 = img.replace("http","https");
        if(img.equals("none")) {
            details_img.setImageResource(R.drawable.blank);
        }
        else{
            picasso.load(s1)
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.brokenimage)
                    .into(details_img);
        }
        if(p.equals("Democratic Party")){
            getWindow().getDecorView().setBackgroundColor(Color.rgb(0,0,225));
            details_logo.setImageResource(R.drawable.dem_logo);
        }
        else if (p.equals("Republican Party")) {
            getWindow().getDecorView().setBackgroundColor(Color.rgb(225, 0, 0));
            details_logo.setImageResource(R.drawable.rep_logo);
        }else{
            getWindow().getDecorView().setBackgroundColor(Color.rgb(0, 0, 0));
            details_logo.setVisibility(View.GONE);
        }

    }


}