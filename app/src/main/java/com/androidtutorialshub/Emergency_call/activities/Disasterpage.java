package com.androidtutorialshub.Emergency_call.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.androidtutorialshub.Emergency_call.GPSTracker;
import com.androidtutorialshub.Emergency_call.R;
import com.androidtutorialshub.Emergency_call.model.User;
import com.androidtutorialshub.Emergency_call.sql.DatabaseHelper;

public class Disasterpage extends AppCompatActivity implements View.OnClickListener {
    private final AppCompatActivity activity = Disasterpage.this;
    GPSTracker gps;
    private TextView firebut;
    private TextView accidentbut;
    private DatabaseHelper databaseHelper;
    private User user;
    private double latitude;
    private double longitude;
    private String number;
    private String number2;
    private String sendingMessage;
    private String str;
    private String url = "https://www.cnet.com/pictures/prepare-for-a-natural-disaster-with-these-tips-wildfire-flood-hurricane-tornado/7/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disasterpage);
        getSupportActionBar().hide();


        firebut = (TextView) findViewById(R.id.disastertrigger);
        accidentbut = (TextView) findViewById(R.id.disasterlinkview);
        gps = new GPSTracker(Disasterpage.this);
        Intent intent = getIntent();
        str = intent.getStringExtra("message-key");
        databaseHelper = new DatabaseHelper(activity);
        user = databaseHelper.getUser(str, 1);
        number = user.getRelative();
        number2 = user.getNumber();


        firebut.setOnClickListener(this);
        accidentbut.setOnClickListener(this);


        if (gps.canGetLocation()) {

            latitude = gps.getLatitude();
            longitude = gps.getLongitude();

            // \n is for new line} else {
            // Can't get location.
            // GPS or network is not enabled.
            // Ask user to enable GPS/network in settings.
        }
        sendingMessage = "Natural Disaster Emergency At:" + "\t" + "\n" + number2 + "\n" + "Location:" + "\n" + latitude + "\t" + longitude;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.disastertrigger: {
                SmsManager sms = SmsManager.getDefault();
                sms.sendTextMessage(number, null, sendingMessage, null, null);
                Toast.makeText(getApplicationContext(), "Message Sent successfully!", Toast.LENGTH_LONG).show();
                break;
            }
            case R.id.disasterlinkview: {
                Uri webpage = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                    break;
                }
            }
        }
    }
}
