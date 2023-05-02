package com.example.tribu_inital.start;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.tribu_inital.Main_ui;
import com.example.tribu_inital.R;
import com.example.tribu_inital.auth.Login_page;
import com.example.tribu_inital.auth.Sign_up_page;
import com.google.firebase.auth.FirebaseAuth;

public class Start_page extends AppCompatActivity implements View.OnClickListener {

    Button signUpButton, loginButton;

    Intent intent;

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_page);
        signUpButton=findViewById(R.id.signUpButton);
        loginButton=findViewById(R.id.loginPageButton);

        signUpButton.setOnClickListener(this);
        loginButton.setOnClickListener(this);

        //Todo: remove this later need better solution (only testing)
        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() !=null){
            intent = new Intent(Start_page.this, Main_ui.class);
            startActivity(intent);
            finish();
        }

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