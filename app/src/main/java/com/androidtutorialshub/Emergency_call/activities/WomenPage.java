package com.androidtutorialshub.Emergency_call.activities;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
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

public class WomenPage extends AppCompatActivity implements View.OnClickListener {
    private final AppCompatActivity activity = WomenPage.this;
    GPSTracker gps;
    private String str;
    private DatabaseHelper databaseHelper;
    private User user;
    private double latitude;
    private double longitude;
    private String name;
    private String number;
    private String number2;
    private String sendingMessage;
    private SensorManager mSensorManager;
    private float mAccel;
    private float mAccelCurrent;
    private float mAccelLast;
    private final SensorEventListener mSensorListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];
            mAccelLast = mAccelCurrent;
            mAccelCurrent = (float) Math.sqrt((double) (x * x + y * y + z * z));
            float delta = mAccelCurrent - mAccelLast;
            mAccel = mAccel * 0.9f + delta;
            if (mAccel > 12) {
                Toast.makeText(getApplicationContext(), "Shake event detected", Toast.LENGTH_SHORT).show();
                SmsManager sms = SmsManager.getDefault();
                sms.sendTextMessage(number, null, sendingMessage, null, null);
                Toast.makeText(getApplicationContext(), "Message Sent successfully!", Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    };
    private TextView link11;
    private TextView link22;
    private String url = "https://youtu.be/T7aNSRoDCmg";
    private String url2 = "https://www.youtube.com/watch?v=KVpxP3ZZtAc";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_women_page);
        getSupportActionBar().hide();
        link11 = (TextView) findViewById(R.id.linkview1);
        link22 = (TextView) findViewById(R.id.linkview2);
        link22.setOnClickListener(this);
        link11.setOnClickListener(this);
        Intent intent = getIntent();
        str = intent.getStringExtra("message-key");

        databaseHelper = new DatabaseHelper(activity);
        user = databaseHelper.getUser(str, 1);
        name = user.getName();
        number = user.getRelative();
        number2 = user.getNumber();

        gps = new GPSTracker(WomenPage.this);


        if (gps.canGetLocation()) {

            latitude = gps.getLatitude();
            longitude = gps.getLongitude();

            // \n is for new line} else {
            // Can't get location.
            // GPS or network is not enabled.
            // Ask user to enable GPS/network in settings.
        }
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensorManager.registerListener(mSensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);
        mAccel = 10f;
        mAccelCurrent = SensorManager.GRAVITY_EARTH;
        mAccelLast = SensorManager.GRAVITY_EARTH;
        sendingMessage = "Emergency for" + "\t" + name + "\n" + number2 + "\n" + "Location:" + "\n" + latitude + "\t" + longitude;


    }

    @Override
    protected void onResume() {
        mSensorManager.registerListener(mSensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);
        super.onResume();
    }

    @Override
    protected void onPause() {
        mSensorManager.unregisterListener(mSensorListener);
        super.onPause();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.linkview1: {
                Uri webpage = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                    break;
                }
            }
            case R.id.linkview2: {
                Uri webpage = Uri.parse(url2);
                Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                    break;
                }
            }
        }
    }
}
