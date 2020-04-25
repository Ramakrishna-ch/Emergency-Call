package com.androidtutorialshub.Emergency_call.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.androidtutorialshub.Emergency_call.R;
import com.androidtutorialshub.Emergency_call.model.User;
import com.androidtutorialshub.Emergency_call.sql.DatabaseHelper;

public class UserDetails extends AppCompatActivity implements View.OnClickListener {
    private final AppCompatActivity activity = UserDetails.this;
    private TextView id;
    private TextView name;
    private TextView email;
    private TextView password;
    private TextView age;
    private TextView gender;
    private TextView phone;
    private TextView address;
    private TextView relative;
    private DatabaseHelper databaseHelper;
    private User user;
    private String str;
    //private int value1;
    private Button logoutbut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);
        getSupportActionBar().hide();
        Intent intent = getIntent();
        str = intent.getStringExtra("message-key");
        id = (TextView) findViewById(R.id.id);
        name = (TextView) findViewById(R.id.name);
        email = (TextView) findViewById(R.id.email);
        password = (TextView) findViewById(R.id.password);
        age = (TextView) findViewById(R.id.age1);
        gender = (TextView) findViewById(R.id.gender1);
        phone = (TextView) findViewById(R.id.phone);
        address = (TextView) findViewById(R.id.address1);
        logoutbut = (Button) findViewById(R.id.logout);
        relative = (TextView) findViewById(R.id.relativeid);

        databaseHelper = new DatabaseHelper(activity);
        user = databaseHelper.getUser(str, 2);
        logoutbut.setOnClickListener(this);
        triggerout();
    }

    public void triggerout() {

        id.setText("ID: " + user.getId());
        name.setText("NAME: " + user.getName());
        email.setText("EMAIL: " + user.getEmail());
        password.setText("PASSWORD: " + user.getPassword());
        age.setText("AGE: " + user.getAge());
        gender.setText("GENDER: " + user.getGender());
        phone.setText("CONTACT: " + user.getNumber());
        address.setText("ADDRESS: " + user.getAddress());
        relative.setText("RELATIVE NO: " + user.getRelative());

    }

    @Override
    public void onClick(View view) {

        Intent intentRegister = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intentRegister);
    }
}
