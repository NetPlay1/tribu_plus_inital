package com.example.tribu_inital;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Main_page extends AppCompatActivity {

    TextView userName;

    Intent intent;

    FirebaseUser user;

    @Override

    // Todo : this is just a test need to research more

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        userName = findViewById(R.id.userName);

         user = FirebaseAuth.getInstance().getCurrentUser();

        Toast.makeText(this,""+user.getUid(),Toast.LENGTH_LONG).show();
        userName.setText(user.getEmail());

    }
}