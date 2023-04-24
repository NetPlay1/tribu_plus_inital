package com.example.tribu_inital;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class Login_page extends AppCompatActivity implements View.OnClickListener {

    TextInputLayout emailInput, passwordInput;

    String email, password;

    Button loginButton;

    RelativeLayout layout;

    ProgressBar progressBar;

    Toast toast;

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        emailInput=findViewById(R.id.emailInput2);
        passwordInput = findViewById(R.id.passwordInput);
        loginButton=findViewById(R.id.loginButton);
        layout=findViewById(R.id.display);

        // creating the progress bar
        progressBar = new ProgressBar(Login_page.this, null, android.R.attr.progressBarStyleLarge);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(100, 100);
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        layout.addView(progressBar, params);

        progressBar.setVisibility(View.GONE);

        loginButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        email= Objects.requireNonNull(emailInput.getEditText()).getText().toString();
        password= Objects.requireNonNull(passwordInput.getEditText()).getText().toString();

        progressBar.setVisibility(View.VISIBLE);
        try {
            if (isValidated()) {

                final FirebaseAuth Auth = FirebaseAuth.getInstance();

                Auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser firebaseUser = Auth.getCurrentUser();
                        toast = Toast.makeText(this, "Welcome Back!", Toast.LENGTH_SHORT);
                        toast.show();
                        progressBar.setVisibility(View.GONE);

                        intent = new Intent(Login_page.this, Main_page.class);
                        startActivity(intent);

                    }
                }).addOnFailureListener(e -> {
                    toast = Toast.makeText(this, "" + e.getMessage(), Toast.LENGTH_LONG);
                    toast.show();
                    progressBar.setVisibility(View.GONE);
                });

            }
        }

        catch (Exception e){
            progressBar.setVisibility(View.GONE);
            toast = Toast.makeText(this,e.getMessage(),Toast.LENGTH_SHORT);
            toast.show();
        }

    }

        //checking if emailInput and passwordInput aren't empty
    public boolean isValidated(){

        //removing all previous errors
        emailInput.setError(null);
        passwordInput.setError(null);

        if(email.isEmpty()){
            emailInput.setError("Email cannot be empty!");
            emailInput.setFocusable(true);
            progressBar.setVisibility(View.GONE);
            return false;
        }

        if(password.isEmpty()){
            passwordInput.setError("password cannot be empty!");
            passwordInput.setFocusable(true);
            progressBar.setVisibility(View.GONE);
            return false;
        }
        return true;
    }
}