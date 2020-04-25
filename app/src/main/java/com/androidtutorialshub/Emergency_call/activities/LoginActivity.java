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

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private final AppCompatActivity activity = LoginActivity.this;

    private TextView Messagelogin;

    private EditText textInputEditTextEmail;
    private EditText textInputEditTextPassword;

    private Button appCompatButtonLogin;

    private TextView textViewLinkRegister;

    private InputValidation inputValidation;
    private DatabaseHelper databaseHelper;
    private User user;
    private boolean checkemail;
    private boolean checkmail;
    private boolean checkpass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        initViews();
        initListeners();
        initObjects();
    }

    /**
     * This method is to initialize views
     */
    private void initViews() {
        textInputEditTextEmail = (EditText) findViewById(R.id.textInputEditTextEmail);
        textInputEditTextPassword = (EditText) findViewById(R.id.textInputEditTextPassword);
        Messagelogin = (TextView) findViewById(R.id.messageloginView);
        appCompatButtonLogin = (Button) findViewById(R.id.appCompatButtonLogin);

        textViewLinkRegister = (TextView) findViewById(R.id.textViewLinkRegister);

    }

    /**
     * This method is to initialize listeners
     */
    private void initListeners() {
        appCompatButtonLogin.setOnClickListener(this);
        textViewLinkRegister.setOnClickListener(this);
    }

    /**
     * This method is to initialize objects to be used
     */
    private void initObjects() {
        databaseHelper = new DatabaseHelper(activity);
        inputValidation = new InputValidation(activity);

    }

    /**
     * This implemented method is to listen the click on view
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.appCompatButtonLogin:
                verifyFromSQLite();
                break;
            case R.id.textViewLinkRegister:
                // Navigate to RegisterActivity
                Intent intentRegister = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intentRegister);
                break;
        }
    }

    /**
     * This method is to validate the input text fields and verify login credentials from SQLite
     */
    private void verifyFromSQLite() {
        checkemail = inputValidation.isInputEditTextFilled(textInputEditTextEmail, "email");
        checkmail = inputValidation.isInputEditTextEmail(textInputEditTextEmail);
        checkpass = inputValidation.isInputEditTextFilled(textInputEditTextPassword, "password");

        if (!checkemail) {
            Toast.makeText(LoginActivity.this, "Enter Email", Toast.LENGTH_SHORT).show();

        } else {
            if (!checkpass) {
                Toast.makeText(LoginActivity.this, "Enter password", Toast.LENGTH_SHORT).show();

            } else {
                if (!checkpass) {
                    Toast.makeText(LoginActivity.this, "Enter valid email", Toast.LENGTH_SHORT).show();

                } else {

                    if (databaseHelper.checkUser(textInputEditTextEmail.getText().toString().trim()
                            , textInputEditTextPassword.getText().toString().trim())) {
                        Toast.makeText(LoginActivity.this, "login Successful", Toast.LENGTH_SHORT).show();
                        Intent detailsint = new Intent(activity, MainPageApp.class);
                        detailsint.putExtra("message-key", textInputEditTextEmail.getText().toString().trim());
                        emptyInputEditText();
                        startActivity(detailsint);

                    } else {
                        // Snack Bar to show success message that record is wrong
                        Toast.makeText(LoginActivity.this, "Wrong Email or password", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }

    /**
     * This method is to empty all input edit text
     */
    private void emptyInputEditText() {
        textInputEditTextEmail.setText(null);
        textInputEditTextPassword.setText(null);
    }
}
