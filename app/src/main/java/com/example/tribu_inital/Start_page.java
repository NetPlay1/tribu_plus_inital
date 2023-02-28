package com.example.tribu_inital;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Start_page extends AppCompatActivity implements View.OnClickListener {

    Button signUpButton, loginButton;

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_page);
        signUpButton=findViewById(R.id.signUpButton);
        loginButton=findViewById(R.id.loginPageButton);

        signUpButton.setOnClickListener(this);
        loginButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        if (view == loginButton)
            intent = new Intent(this, Login_page.class);


        if(view == signUpButton)
            intent = new Intent(this, Sign_up_page.class);

        startActivity(intent);
    }
}