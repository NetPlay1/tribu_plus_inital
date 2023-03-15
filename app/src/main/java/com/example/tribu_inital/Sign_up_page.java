package com.example.tribu_inital;


import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.AsyncTaskLoader;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Timer;
import java.util.TimerTask;

public class Sign_up_page extends AppCompatActivity implements View.OnClickListener{


    EditText name,pass,email;

    //for creating the user profile
    FirebaseAuth firebaseAuth;

    //data base connection
    FirebaseDatabase database=FirebaseDatabase.getInstance();

    //pointing to the Users table
    DatabaseReference ref=database.getReference("Users");

    Button submit;

    RelativeLayout layout;

    ProgressBar progressBar;

    //User_photo_dialog dialog;

    String photo;

    Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up_page);
        name=findViewById(R.id.NameInput);
        email=findViewById(R.id.emailInput);
        pass=findViewById(R.id.passwordText);
        submit=findViewById(R.id.submitButton);
        layout=findViewById(R.id.display);



        // creating the progress bar
        progressBar = new ProgressBar(Sign_up_page.this, null, android.R.attr.progressBarStyleLarge);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(100, 100);
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        layout.addView(progressBar, params);

        progressBar.setVisibility(View.GONE);

        //write a message to data base
        firebaseAuth = FirebaseAuth.getInstance();

        //dialog stuff



       submit.setOnClickListener(this);
    }

    public void createUser(){
        progressBar.setVisibility(View.VISIBLE);

        //handler.postDelayed(() -> progressBar.setVisibility(View.GONE),3000);

        if(isValidate()){
            firebaseAuth.createUserWithEmailAndPassword(
                    email.getText().toString(),
                    pass.getText().toString())
                    .addOnCompleteListener(this, task -> {

                            getPhotoFromDialog();


                        //creating a normal user with default photo
                        User user= new User(
                                name.getText().toString(),
                                email.getText().toString(),
                                pass.getText().toString(),
                                photo);
                        if (task.isSuccessful()){

                            ref.push().setValue(user);
                            progressBar.setVisibility(View.GONE);

                            Toast toast=Toast.makeText(getApplicationContext(),"Account created!",Toast.LENGTH_SHORT);
                            toast.show();

//                            intent =new Intent(Sign_up_page.this,User_photo_dialog.class);
//                            startActivity(intent);




                        }

                    }).addOnFailureListener(e -> {
                        progressBar.setVisibility(View.GONE);
                        Toast toast=Toast.makeText(getApplicationContext(),""+e.getMessage(),Toast.LENGTH_SHORT);
                        toast.show();
                    });
        }

    }

    public void getPhotoFromDialog() {
        User_photo_dialog_v2 dialogV2 =new User_photo_dialog_v2(Sign_up_page.this);
        dialogV2.show();

    }


    public boolean isValidate(){
        if(name.getText().toString().length() < 3){
            name.setError("name must be at list 3 characters long");
            name.setFocusable(true);
            progressBar.setVisibility(View.GONE);
            return false;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()){
            email.setError("Invalid email");
            email.setFocusable(true);
            progressBar.setVisibility(View.GONE);
            return false;
        }
        if(pass.getText().toString().length()<6){
            pass.setError("password need to be at least 6 characters long");
            pass.setFocusable(true);
            progressBar.setVisibility(View.GONE);
            return false;
        }
        return true;
    }


    @Override
    public void onClick(View view) {
        //TODO: create user is imprortent
        createUser();

//        User_photo_dialog_fragment dialogFragment = new User_photo_dialog_fragment();
//        dialogFragment.show(getSupportFragmentManager(), "MyFragment");


//        intent =new Intent(Sign_up_page.this,User_photo_dialog.class);
//        startActivity(intent);

 }









}