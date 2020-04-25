package com.androidtutorialshub.Emergency_call.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.androidtutorialshub.Emergency_call.R;
import com.androidtutorialshub.Emergency_call.helpers.InputValidation;
import com.androidtutorialshub.Emergency_call.model.User;
import com.androidtutorialshub.Emergency_call.sql.DatabaseHelper;

/**
 * Created by lalit on 8/27/2016.
 */
public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private final AppCompatActivity activity = RegisterActivity.this;

    private TextView messageview;

    private EditText textInputEditTextName;
    private EditText textInputEditTextEmail;
    private EditText textInputEditTextPassword;
    private EditText textInputEditTextConfirmPassword;
    private EditText agebut;
    private EditText genderbut;
    private EditText phnumbut;
    private EditText addressbut;
    private EditText relative;

    private Button appCompatButtonRegister;
    private TextView appCompatTextViewLoginLink;

    private InputValidation inputValidation;
    private DatabaseHelper databaseHelper;
    private User user;

    private boolean checkname;
    private boolean checkemail;
    private boolean checkall;
    private boolean checkmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);
        getSupportActionBar().hide();
        initViews();
        initListeners();
        initObjects();


    }

    private void initViews() {
        messageview = (TextView) findViewById(R.id.messageView);
        textInputEditTextName = (EditText) findViewById(R.id.textInputEditTextName);
        textInputEditTextEmail = (EditText) findViewById(R.id.textInputEditTextEmail);
        textInputEditTextPassword = (EditText) findViewById(R.id.textInputEditTextPassword);
        textInputEditTextConfirmPassword = (EditText) findViewById(R.id.textInputEditTextConfirmPassword);
        agebut = (EditText) findViewById(R.id.age);
        genderbut = (EditText) findViewById(R.id.gender);
        phnumbut = (EditText) findViewById(R.id.phnumber);
        addressbut = (EditText) findViewById(R.id.address);
        relative = (EditText) findViewById(R.id.relativenu);

        appCompatButtonRegister = (Button) findViewById(R.id.appCompatButtonRegister);

        appCompatTextViewLoginLink = (TextView) findViewById(R.id.appCompatTextViewLoginLink);
    }


    /**
     * This method is to initialize views
     */


    /**
     * This method is to initialize listeners
     */
    private void initListeners() {
        appCompatButtonRegister.setOnClickListener(this);
        appCompatTextViewLoginLink.setOnClickListener(this);

    }

    /**
     * This method is to initialize objects to be used
     */
    private void initObjects() {
        inputValidation = new InputValidation(activity);
        databaseHelper = new DatabaseHelper(activity);
        user = new User();

    }


    /**
     * This implemented method is to listen the click on view
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.appCompatButtonRegister:
                postDataToSQLite();
                break;

            case R.id.appCompatTextViewLoginLink:
                Intent intentRegister = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intentRegister);
                break;
        }
    }

    /**
     * This method is to validate the input text fields and post data to SQLite
     */
    private void postDataToSQLite() {
        checkname = inputValidation.isInputEditTextFilled(textInputEditTextName, "name");
        checkemail = inputValidation.isInputEditTextFilled(textInputEditTextEmail, "email");
        checkmail = inputValidation.isInputEditTextEmail(textInputEditTextEmail);
        checkall = inputValidation.isInputEditTextMatches(textInputEditTextPassword, textInputEditTextConfirmPassword);
        if (!checkname) {
            Toast.makeText(RegisterActivity.this, "Enter full name", Toast.LENGTH_SHORT).show();

        }
        if (!checkemail) {
            Toast.makeText(RegisterActivity.this, "Enter full email", Toast.LENGTH_SHORT).show();

        }
        if (!checkmail) {
            Toast.makeText(RegisterActivity.this, "Enter vaild Email", Toast.LENGTH_SHORT).show();

        }
        if (!checkall) {
            Toast.makeText(RegisterActivity.this, "Password doesnt matches", Toast.LENGTH_SHORT).show();

        }
        if (checkemail) {

            if (!databaseHelper.checkUser(textInputEditTextEmail.getText().toString().trim())) {

                user.setName(textInputEditTextName.getText().toString().trim());
                user.setEmail(textInputEditTextEmail.getText().toString().trim());
                user.setPassword(textInputEditTextPassword.getText().toString().trim());
                user.setAge(agebut.getText().toString().trim());
                user.setGender(genderbut.getText().toString().trim());
                user.setNumber(phnumbut.getText().toString().trim());
                user.setAddress(addressbut.getText().toString().trim());
                user.setRelative(relative.getText().toString().trim());

                databaseHelper.addUser(user);

                // Snack Bar to show success message that record saved successfully
                Toast.makeText(RegisterActivity.this, "Registration Successfull", Toast.LENGTH_SHORT).show();
                emptyInputEditText();
                Intent detailsint = new Intent(activity, MainActivity.class);
                startActivity(detailsint);


            } else {
                // Snack Bar to show error message that record already exists
                Toast.makeText(RegisterActivity.this, "E mail Already Exist", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(RegisterActivity.this, "Please Enter the Email", Toast.LENGTH_SHORT).show();

        }



    }

    /**
     * This method is to empty all input edit text
     */
    private void emptyInputEditText() {
        textInputEditTextName.setText(null);
        textInputEditTextEmail.setText(null);
        textInputEditTextPassword.setText(null);
        textInputEditTextConfirmPassword.setText(null);
    }
}
