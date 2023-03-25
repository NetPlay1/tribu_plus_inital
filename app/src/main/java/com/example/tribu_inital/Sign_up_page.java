package com.example.tribu_inital;


import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class Sign_up_page extends AppCompatActivity implements View.OnClickListener {

    EditText name,pass,email;

    //for creating the user profile
    FirebaseAuth firebaseAuth;

    //data base connection
    FirebaseDatabase database=FirebaseDatabase.getInstance();

    //pointing to the Users table
    DatabaseReference ref;

    //storage pointer
    StorageReference mStorageRef;

    Button submit;

    RelativeLayout layout;

    ProgressBar progressBar;

    Intent intent;
    Uri uri;

    String picName;

    ActivityResultLauncher<Intent> mStartDialog;



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



        submit.setOnClickListener(this);


        mStartDialog = registerForActivityResult(
               new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if(result.getResultCode() !=-1) return;
                    Intent data = result.getData();
                    assert data != null;
                    uri = data.getData();
                    picName = System.currentTimeMillis()+".image";
                    continueCreatingUser();

                }
       );
    }

    public void createUser(){
        progressBar.setVisibility(View.VISIBLE);

        //handler.postDelayed(() -> progressBar.setVisibility(View.GONE),3000);

        if(isValidate()){
            //default user photo
            uri = Uri.parse("android.resource://com.example.tribu_inital/"+R.mipmap.user);

            requestPermissions(new String[] {Manifest.permission.CAMERA},1);


            intent = new Intent(Sign_up_page.this, User_photo_dialog.class);
            mStartDialog.launch(intent);
            //startActivityForResult(intent,1);

        }

    }

    private void continueCreatingUser() {
        firebaseAuth.createUserWithEmailAndPassword(
                        email.getText().toString(),
                        pass.getText().toString())
                .addOnCompleteListener(this, task -> {

                    //calling user photo dialog

                    User user= new User(
                            name.getText().toString(),
                            email.getText().toString(),
                            pass.getText().toString(),
                            picName
                    );

                    if (task.isSuccessful()){
                        ref = database.getReference("Users").push();
                        //creating the group / pointing to it

                        //setting the user-key/path and adding the user as the value
                        ref.child(user.getKey()).setValue(user);
                        progressBar.setVisibility(View.GONE);

                        mStorageRef = FirebaseStorage.getInstance().getReference("Images/Users/"+ user.getEmail().replace('.',' '));
                        mStorageRef = mStorageRef.child(picName);
                        mStorageRef.putFile(uri).addOnSuccessListener(taskSnapshot ->
                                        Toast.makeText(this,"user photo uploaded Successfully!",Toast.LENGTH_SHORT).show())
                                        .addOnFailureListener(e -> Toast.makeText(this,""+e,Toast.LENGTH_LONG).show());

                        Toast toast=Toast.makeText(getApplicationContext(),"Account created!",Toast.LENGTH_SHORT);
                        toast.show();


                    }

                }).addOnFailureListener(e -> {
                    progressBar.setVisibility(View.GONE);
                    Toast toast=Toast.makeText(this,""+e.getMessage(),Toast.LENGTH_SHORT);
                    toast.show();
                });

    }


    /*
    *
    * Basic validation for the user
    *
    * */

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
      createUser();

//    intent = new Intent(Sign_up_page.this, User_photo_dialog.class);
//    startActivityForResult(intent,1);
 }

 //     getting result from user photo dialog


    /*
        getting file extension of the user image

        not my code!
     */

    //Todo StartActivityForResult was here fix it!!!

//    private String getFileExtension(Uri uri){
//        ContentResolver contentResolver = getContentResolver();
//        MimeTypeMap mime = MimeTypeMap.getSingleton();
//        return mime.getExtensionFromMimeType(contentResolver.getType(uri));
//    }


}