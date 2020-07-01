package com.example.phobiaquiz_app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.basgeekball.awesomevalidation.utility.custom.SimpleCustomValidation;

import java.util.HashMap;


/**
 * activity that controls sign up form
 */
public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {



    //defining AwesomeValidation object
    private AwesomeValidation awesomeValidation;
    private EditText userNameField;
    private EditText passwordField;
    private EditText validatePasswordField;
    private EditText emailField;
    private EditText phoneNumberField;
    private Button signUpButton;

    Context context;
    private HashMap<String,String> credentialsDictionary = null;        // init Hashmap



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        context = getApplicationContext();
        // -----------------------------------------------------------------------------------------
        // perform Hashmap data operations
        credentialsDictionary = new HashMap<String,String>();                                       // initialize the hashmap
        //credentialsDictionary.put("mark","123");                                                  //
        if((HashMap<String, String>) getIntent().getSerializableExtra("data")!= null) {       // attempt to retrieve an existing hashmap instance if it already exists or was modified in another activity
            credentialsDictionary = (HashMap<String, String>) getIntent().getSerializableExtra("data");
        }

        if(credentialsDictionary != null) {
            int s = credentialsDictionary.size();
        }

        // -----------------------------------------------------------------------------------------



        //initializing awesomevalidation object
        /*
         * The library provides 3 types of validation
         * BASIC
         * COLORATION
         * UNDERLABEL
         * */
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);   // init an instance of this API

        // initialize view objects
        userNameField = (EditText) findViewById(R.id.userNameField);
        passwordField = findViewById(R.id.passwordField);
        validatePasswordField = findViewById(R.id.validatePasswordField);
        emailField = findViewById(R.id.emailField);
        phoneNumberField = findViewById(R.id.phoneNumberField);
        signUpButton = findViewById(R.id.signUpButton);

        // add validation to form view objects
        awesomeValidation.addValidation(this, R.id.userNameField, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.nameerror);
        String regexPassword = "^(?=.*?[0-9]).{8,}$";    // include at least one number 0-9, and be at least of length 8
        awesomeValidation.addValidation(this, R.id.passwordField, regexPassword, R.string.nameerror);
        awesomeValidation.addValidation(this, R.id.validatePasswordField, regexPassword, R.string.nameerror);
        awesomeValidation.addValidation(this, R.id.emailField, Patterns.EMAIL_ADDRESS, R.string.nameerror);
        awesomeValidation.addValidation(this, R.id.phoneNumberField, RegexTemplate.TELEPHONE, R.string.nameerror);

        // validate the confirmation of a second field (password fields)
        // makes sure that both passwords match
        awesomeValidation.addValidation(this,R.id.validatePasswordField,R.id.passwordField, R.string.nameerror);

        // custom validation to check if name exists in the database already
        awesomeValidation.addValidation(this, R.id.userNameField, new SimpleCustomValidation() {
            @Override
            public boolean compare(String s) {
                if(credentialsDictionary.containsKey(s)) {
                    return false;
                }
                return true;
            }
        }, R.string.nameerror);




        signUpButton.setOnClickListener(this);
    }


    /*
    Validates user input in form via validation API, and then uses intents to send a hashmap object over to next
    activity
     */
    private void submitForm() {
        //first validate the form then move ahead
        //if this becomes true that means validation is successfull
        if (awesomeValidation.validate()) {
            Toast.makeText(this, "Validation Successfull", Toast.LENGTH_LONG).show();

            //process the data further
            //Intent intent = getIntent();
            //String userName = intent.getStringExtra("userInfo");
            String userName = userNameField.getText().toString();
            String password = passwordField.getText().toString();
            credentialsDictionary.put(userName,password);       // store the user info upon succesfull validation

            Intent signUpIntent = new Intent(getApplicationContext(),LoginActivity.class);
            signUpIntent.putExtra("data",credentialsDictionary);        // send user data over to next activity that needs it
            startActivity(signUpIntent);
        }
    }


    /*
    custom definition of the behavior of the signup button
     */
    @Override
    public void onClick(View v) {
        if (v == signUpButton) {
            submitForm();
        }
    }
}
