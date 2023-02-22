package com.example.tribu_inital;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class Login_page extends AppCompatActivity implements View.OnClickListener {

    EditText emailInput, passwordInput;

    Button loginButton;

    RelativeLayout layout;

    ProgressBar progressBar;

    String email,password;

    Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        emailInput=findViewById(R.id.emailInput2);
        passwordInput=findViewById(R.id.passwordInput);
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
        email=emailInput.getText().toString();
        password=passwordInput.getText().toString();
        progressBar.setVisibility(View.VISIBLE);
        try {
            if(email == null){

                progressBar.setVisibility(View.GONE);
                emailInput.setError("you didn't fill your email!");
                emailInput.setFocusable(true);
                throw new Exception("you didn't fill your email!");
            }

            if(password == null) {

                progressBar.setVisibility(View.GONE);
                passwordInput.setError("you didn't fill you password!");
                passwordInput.setFocusable(true);
                throw new Exception("you didn't fill you password!");
            }

            final FirebaseAuth Auth =FirebaseAuth.getInstance();

            Auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(task -> {
                if(task.isSuccessful()){
                    toast = Toast.makeText(this,"Welcome Back!",Toast.LENGTH_SHORT);
                    toast.show();
                    progressBar.setVisibility(View.GONE);

                }
            }).addOnFailureListener(e ->{
                toast = Toast.makeText(this,""+e.getMessage(),Toast.LENGTH_LONG);
                toast.show();
                progressBar.setVisibility(View.GONE);
            });

        }

        catch (Exception e){
            progressBar.setVisibility(View.GONE);
            toast = Toast.makeText(this,e.getMessage(),Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}