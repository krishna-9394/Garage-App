package com.example.garageapp.screen;
import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.garageapp.MainActivity;
import com.example.garageapp.R;
import com.example.garageapp.database.LoginDBHandler;

public class SignIn_Page extends AppCompatActivity {

    private EditText email,password;
    private TextView signupHyperLink,forgotPasswordHyperLink;
    private Button login;
    private ProgressBar progressBar;
    private LoginDBHandler loginDB;
//    private PreferenceManager preferenceManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        preferenceManager = new PreferenceManager(getApplicationContext());

//        if(preferenceManager.getBoolean(Constants.KEY_IS_SIGNED_IN)){
//            Intent intent = new Intent(SignIn_Page.this, MainActivity.class);
//            startActivity(intent);
//            finish();
//        }  // checking out weather the person has already signed in or not? using preferences manager
        setContentView(R.layout.activity_sign_in_page);
        initialization();  // initialising the view Items
        setListeners(); //various operations list
    }
    private void initialization() {
        email = findViewById(R.id.sign_in_email_input);
        password = findViewById(R.id.sign_in_password_input);
        signupHyperLink = findViewById(R.id.signUp_option);
        forgotPasswordHyperLink = findViewById(R.id.forgot_password_option);
        login = findViewById(R.id.Sign_in_button);
        progressBar = findViewById(R.id.progress_bar);
        loginDB = new LoginDBHandler(this);
    }  // initialization of views and viewGroups
    private void setListeners() {
        login.setOnClickListener(view -> {
            if (validateCredentials()) Login();
        });
        signupHyperLink.setOnClickListener(view -> {
            startActivity(new Intent(SignIn_Page.this, SignUp_Page.class));
        });
        forgotPasswordHyperLink.setOnClickListener(view -> {
            showToast("work in Progress");
        }); // currently not completed
    }  // initializing signUp link, forgotPassword and submit button
    private void Login() {
        loading(true);
        String _email = email.getText().toString().trim();
        String _password = password.getText().toString().trim();
        Log.i(TAG, "Login: "+_email+" "+_password);
        if(loginDB.checkusernamepassword(_email,_password)){
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            showToast("LoginSuccessful");
            loading(false);
            return;
        } else {
            showToast("Step 4");
        }
        loading(false);
//        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//        startActivity(intent);
        return;
    }  //checked it again
    private void loading(boolean isLoading) {
        if(isLoading) {
            progressBar.setVisibility(View.VISIBLE);
            login.setVisibility(View.INVISIBLE);
        } else {
            progressBar.setVisibility(View.INVISIBLE);
            login.setVisibility(View.VISIBLE);
        }
    } // checked it again
    private boolean validateCredentials() {
        if(TextUtils.isEmpty(email.getText().toString())) {
            email.setError("email is required");
            showToast("email is required...");
            return false;
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()){
            email.setError("invalid email");
            showToast("invalid email...");
            return false;
        }
        else if(TextUtils.isEmpty(password.getText().toString())){
            password.setError("password is required");
            showToast("password is required...");
            return false;
        }
        else return true;
    } // validating Login credentials
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }  //showing the toast message
}