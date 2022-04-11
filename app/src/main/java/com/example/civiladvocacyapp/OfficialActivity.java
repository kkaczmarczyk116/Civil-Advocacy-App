package com.example.civiladvocacyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Paint;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.text.util.Linkify;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class OfficialActivity extends AppCompatActivity {

    private TextView official_staff;
    private TextView official_name;
    private TextView official_loc;
    private TextView official_party;
    private ImageView official_pic;
    private ImageView official_logo;
    private TextView official_address;
    private  TextView official_phone;
    private TextView officials_website;
    private TextView official_addy_holder;
    private ImageView official_fb;
    private ImageView official_twitter;
    private ImageView official_yt;
    private Picasso picasso;
    private String imgLink;
    private String location;
    private String party;
    private String title;
    private String name;
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
        official_logo = findViewById(R.id.official_logo);
        official_address = findViewById(R.id.official_address);
        official_phone = findViewById(R.id.official_phone);
        officials_website = findViewById(R.id.officials_website);
        official_addy_holder = findViewById(R.id.official_addy_holder);
        official_addy_holder.setText("Address:");
        official_fb = findViewById(R.id.official_fb);
        official_twitter =findViewById(R.id.official_twitter);
        official_yt = findViewById(R.id.official_yt);
        ArrayList<String> ugly = new ArrayList<>();
        official_fb.setVisibility(View.GONE);
        official_twitter.setVisibility(View.GONE);
        official_yt.setVisibility(View.GONE);



        Intent intent = getIntent();
        if(intent != null) {
            title = intent.getStringExtra("title");
            name = intent.getStringExtra("name");
            party = intent.getStringExtra("party");
            if(party.equals("Democratic Party")){
                getWindow().getDecorView().setBackgroundColor(Color.rgb(0,0,225));
                official_logo.setImageResource(R.drawable.dem_logo);
            }
            else if (party.equals("Republican Party")) {
                getWindow().getDecorView().setBackgroundColor(Color.rgb(225, 0, 0));
                official_logo.setImageResource(R.drawable.rep_logo);
            }else{
                getWindow().getDecorView().setBackgroundColor(Color.rgb(0, 0, 0));
                official_logo.setVisibility(View.GONE);
            }
            imgLink = intent.getStringExtra("img");
            location = intent.getStringExtra("location");
            official_loc.setText(location);
            String tele = intent.getStringExtra("tele");
            String addy = intent.getStringExtra("addy");
            String web = intent.getStringExtra("web");
            official_address.setText(addy);
            official_address.setTextColor(official_address.getLinkTextColors().getDefaultColor());
            official_address.setPaintFlags(official_address.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            String[] locc = getLatLon(addy);

            if(!addy.equals("address not found")){
                official_address.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent mapIntent = new Intent(Intent.ACTION_VIEW,Uri.parse("geo: "+locc[0]+" "+locc[1]));
                        if (intent.resolveActivity(getPackageManager()) != null){
                            startActivity(mapIntent);
                        }else{
                            return;
                        }
                    }
                });
            }else{
                official_address.setVisibility(View.GONE);
                official_addy_holder.setVisibility(View.GONE);
            }

            if(!tele.equals("none")){
                official_phone.setText("Phone: " +tele);
                Linkify.addLinks(official_phone,Linkify.ALL);
            }else{
                official_phone.setVisibility(View.GONE);
            }

            if(!web.equals("none")){
                officials_website.setText("Website: " +web);
                Linkify.addLinks(officials_website,Linkify.ALL);
            }else{
                officials_website.setVisibility(View.GONE);
            }
            String ch1Type = intent.getStringExtra("ch1Type");
            ugly.add(ch1Type);
            String ch1Id=intent.getStringExtra("ch1Id");
            ugly.add(ch1Id);
            String ch2Type=intent.getStringExtra("ch2Type");
            ugly.add(ch2Type);
            String ch2Id=intent.getStringExtra("ch2Id");
            ugly.add(ch2Id);
            String ch3Type=intent.getStringExtra("ch3Type");
            ugly.add(ch3Type);
            String ch3Id=intent.getStringExtra("ch3Id");
            ugly.add(ch3Id);

            for(int e =0;e< ugly.size();e++){
                String id;
                if(ugly.get(e).equals("Facebook")){
                    official_fb.setVisibility(View.VISIBLE);
                    official_fb.setImageResource(R.drawable.facebook);
                    id = ugly.get(e+1);
                    official_fb.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            clickFacebook(view,id);
                        }
                    });
                }
                else if(ugly.get(e).equals("Twitter")){
                    official_twitter.setVisibility(View.VISIBLE);
                    official_twitter.setImageResource(R.drawable.twitter);
                    id = ugly.get(e+1);
                    official_twitter.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            clickTwitter(view,id);
                        }
                    });

                }
                else if(ugly.get(e).equals("YouTube")){
                    official_yt.setVisibility(View.VISIBLE);
                    official_yt.setImageResource(R.drawable.youtube);
                    id = ugly.get(e+1);
                    official_yt.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            youTubeClicked(view,id);
                        }
                    });

                }

            }



            setOfficials(title,name,party,imgLink);

        }
        official_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!imgLink.equals("none")){
                    onPhototClicked();
                }
                else{
                    return;
                }
            }
        });



    }
    private void onPhototClicked(){
        Intent intent = new Intent(OfficialActivity.this,PhotoDetail.class);
        intent.putExtra("photo",imgLink);
        intent.putExtra("loc",location);
        intent.putExtra("party",party);
        intent.putExtra("title",title);
        intent.putExtra("name",name);
        startActivity(intent);
    }


    private String[] getLatLon(String userProvidedLocation) {
        Geocoder geocoder = new Geocoder(this);
        List<Address> addresses;

        try {
            addresses = geocoder.getFromLocationName(userProvidedLocation,1);

            if (addresses == null || addresses.isEmpty()) {
                return null;
            }

            double lat = addresses.get(0).getLatitude();
            double lon = addresses.get(0).getLongitude();
            String loc = addresses.get(0).getAddressLine(0);

            return new String[] {Double.toString(lat), Double.toString(lon),loc};

        } catch (IOException e) {
            System.out.println("in catch of getLatlong");
            return null;
        }
    }

    private void setOfficials(String t,String n,String p,String img){
        official_staff.setText(t);
        official_name.setText(n);
        official_party.setText("("+ p+" )");
        String s1 = img.replace("http","https");
        if(img.equals("none")) {
            official_pic.setImageResource(R.drawable.blank);
        }
        else{
            picasso.load(s1)
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.brokenimage)
                    .into(official_pic);
        }

    }

    public boolean isPackageInstalled(String packageName) {
        try {
            return getPackageManager().getApplicationInfo(packageName, 0).enabled;
        }
        catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }
    private void makeErrorAlert(String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage(msg);
        builder.setTitle("No App Found");

        AlertDialog dialog = builder.create();
        dialog.show();
    }
    public void clickTwitter(View v,String user) {
        String twitterAppUrl = "twitter://user?screen_name=" + user;
        String twitterWebUrl = "https://twitter.com/" + user;

        Intent intent;
        // Check if Twitter is installed, if not we'll use the browser
        if (isPackageInstalled("com.twitter.android")) {
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse(twitterAppUrl));
        } else {
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse(twitterWebUrl));
        }

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            makeErrorAlert("No Application found that handles ACTION_VIEW (twitter/https) intents");
        }
    }

    public void clickFacebook(View v,String user) {
        // You need the FB user's id for the url
        String FACEBOOK_URL = "https://www.facebook.com/"+user;

        Intent intent;
        if (isPackageInstalled("com.facebook.katana")) {
            String urlToUse = "fb://facewebmodal/f?href=" + FACEBOOK_URL;
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlToUse));
        } else {
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse(FACEBOOK_URL));
        }

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            makeErrorAlert("No Application found that handles ACTION_VIEW (fb/https) intents");
        }

    }
    public void youTubeClicked(View v,String idName) {
        String name = idName;
        Intent intent = null;
        try {
            intent = new Intent(Intent.ACTION_VIEW);
            intent.setPackage("com.google.android.youtube");
            intent.setData(Uri.parse("https://www.youtube.com/" + name));
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://www.youtube.com/" + name)));
        }
    }




}