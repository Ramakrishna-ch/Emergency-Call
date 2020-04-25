package com.androidtutorialshub.Emergency_call.activities;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidtutorialshub.Emergency_call.GPSTracker;
import com.androidtutorialshub.Emergency_call.R;
import com.androidtutorialshub.Emergency_call.model.User;
import com.androidtutorialshub.Emergency_call.sql.DatabaseHelper;

public class MainPageApp extends AppCompatActivity implements View.OnClickListener {
    private final AppCompatActivity activity = MainPageApp.this;
    GPSTracker gps;
    private String str;
    private DatabaseHelper databaseHelper;
    private User user;
    private TextView namemessage;
    private ImageView women;
    private ImageView fire;
    private ImageView accident;
    private ImageView disaster1;
    private ImageView theftbut;
    private double latitude;
    private double longitude;
    private String name;
    private String number;
    private String sendingMessage;

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page_app);
        Intent intent = getIntent();
        str = intent.getStringExtra("message-key");
        gps = new GPSTracker(MainPageApp.this);

        namemessage = (TextView) findViewById(R.id.nameview);
        women = (ImageView) findViewById(R.id.womenicon);
        fire = (ImageView) findViewById(R.id.fireicon);
        disaster1 = (ImageView) findViewById(R.id.disastericon);
        accident = (ImageView) findViewById(R.id.accidenticon);
        theftbut = (ImageView) findViewById(R.id.theftview);
        databaseHelper = new DatabaseHelper(activity);
        user = databaseHelper.getUser(str, 1);
        name = user.getName();
        number = user.getNumber();
        initListener();


        namemessage.setText("WELCOME  " + name);
        gps.showSettingsAlert();


    }

    public void initListener() {
        women.setOnClickListener(this);
        fire.setOnClickListener(this);
        disaster1.setOnClickListener(this);
        accident.setOnClickListener(this);
        theftbut.setOnClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings: {
                Intent intent = new Intent(activity, UserDetails.class);
                intent.putExtra("message-key", str);
                startActivity(intent);
                break;
            }
            case R.id.second: {
                Intent intent = new Intent(activity, MainActivity.class);
                startActivity(intent);

            }


        }
        return true;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.womenicon: {
                Intent intent = new Intent(activity, WomenPage.class);
                intent.putExtra("message-key", str);
                startActivity(intent);
                break;
            }
            case R.id.fireicon: {
                Intent intent = new Intent(activity, FirePage.class);
                intent.putExtra("message-key", str);
                startActivity(intent);
                break;
            }
            case R.id.accidenticon: {
                Intent intent = new Intent(activity, AccidentPage.class);
                intent.putExtra("message-key", str);
                startActivity(intent);
                break;
            }

            case R.id.disastericon: {
                Intent intent = new Intent(activity, Disasterpage.class);
                intent.putExtra("message-key", str);
                startActivity(intent);
                break;
            }
            case R.id.theftview: {
                Intent intent = new Intent(activity, TheftPage.class);
                intent.putExtra("message-key", str);
                startActivity(intent);
                break;
            }

        }
    }
}
