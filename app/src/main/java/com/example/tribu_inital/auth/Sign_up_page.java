package com.example.tribu_inital.auth;



import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.tribu_inital.Main_ui;
import com.example.tribu_inital.R;
import com.example.tribu_inital.User;
import com.example.tribu_inital.User_photo_dialog;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.Objects;
import java.util.UUID;

public class Sign_up_page extends AppCompatActivity implements View.OnClickListener {

    TextInputLayout name,pass,email;

    //for creating the user profile
    FirebaseAuth firebaseAuth;

    //data base connection
    FirebaseDatabase database = FirebaseDatabase.getInstance();

    //pointing to the Users table
    DatabaseReference ref;

    //storage pointer
    StorageReference mStorageRef;

    Button submit;

    RelativeLayout layout;

    ProgressBar progressBar;

    Intent intent;
    Uri uri;

    byte[] bytes;

    String picName;

    String uuid;

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
                    Intent data = result.getData();

                    // checking for no nulls / that the task was completed successfully
                    if(result.getResultCode() !=-1 || data == null){
                        //using the default
                        picName = System.currentTimeMillis() + ".jpg";
                        uri = Uri.parse("android.resource://com.example.tribu_inital/"+R.mipmap.user);
                        continueCreatingUser("gallery");
                        return;
                    }

                    if(data.getStringExtra("photo") == null) {
                        uri = data.getData();
                        picName = System.currentTimeMillis() + ".jpg";
                        continueCreatingUser("gallery");
                        return;
                    }

                    Bitmap bitmap = (Bitmap) data.getExtras().get("image");
                    picName = System.currentTimeMillis() + ".jpg";

                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG,100, baos);
                    bytes = baos.toByteArray();
                    continueCreatingUser("camera");

                }
       );
    }

    public void createUser(){
        progressBar.setVisibility(View.VISIBLE);

        if(isValidate()){
            //default user photo
            uri = Uri.parse("android.resource://com.example.tribu_inital/"+R.mipmap.user);

            intent = new Intent(Sign_up_page.this, User_photo_dialog.class);
            mStartDialog.launch(intent);

        }

    }

    private void continueCreatingUser(String camera_or_gallery) {
        firebaseAuth.createUserWithEmailAndPassword(
                        Objects.requireNonNull(email.getEditText()).getText().toString(),
                        Objects.requireNonNull(pass.getEditText()).getText().toString()
                ).addOnCompleteListener(this, task -> {
                    //creating user with name and password
                    User user= new User(
                            Objects.requireNonNull(name.getEditText()).getText().toString(),
                            picName
                    );

                    if (task.isSuccessful()){
                        //creating uuid aka the session key ? (i think need to check)
                        uuid = UUID.randomUUID().toString();

                        //creating the group / pointing to it
                        ref = database.getReference("Users");

                        //setting the user-key/path and adding the user as the value
                        ref.child(uuid).setValue(user);

                        //setting the current user of the app
                        FirebaseUser fireUser = firebaseAuth.getCurrentUser();

                        progressBar.setVisibility(View.GONE);

                        /*
                        looking for a /images/Users + uuid of user. in fireStorage if none existent makes the path and
                        then creates the photo file
                         */
                        mStorageRef = FirebaseStorage.getInstance().getReference("Images/Users/"+ uuid);
                        mStorageRef = mStorageRef.child(picName);

                        //checking where the photo came from and uploading file accordingly and then intents us to main page
                        if(camera_or_gallery.equals("gallery")) {
                            mStorageRef.putFile(uri).addOnSuccessListener(taskSnapshot ->
                                            Toast.makeText(this, "user photo uploaded Successfully!", Toast.LENGTH_SHORT).show())
                                            .addOnFailureListener(e -> Toast.makeText(this, "" + e, Toast.LENGTH_LONG).show());
                        }
                        else {
                            UploadTask uploadTask = mStorageRef.putBytes(bytes);
                            Toast.makeText(this, "user photo uploaded Successfully!", Toast.LENGTH_SHORT).show();

                        }
                        Toast.makeText(getApplicationContext(),"Account created!",Toast.LENGTH_SHORT).show();
                        intent = new Intent(Sign_up_page.this, Main_ui.class);
                        startActivity(intent);
                    }

                }).addOnFailureListener(e -> {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(this,""+e.getMessage(),Toast.LENGTH_SHORT).show();

                    // material dialog of the error
                    new MaterialAlertDialogBuilder(this)
                            .setTitle("Error")
                            .setMessage(e.getMessage())
                            .setPositiveButton("OK",
                                    (dialogInterface, i) -> dialogInterface.dismiss())
                            .show();
                    });
        }

    //Basic validation for the user
    public boolean isValidate(){

        //removing all previous errors
        pass.setError(null);
        email.setError(null);
        name.setError(null);

        String nameTmp = Objects.requireNonNull(name.getEditText()).getText().toString();
        String passTmp = Objects.requireNonNull(pass.getEditText()).getText().toString();
        String emailTmp = Objects.requireNonNull(email.getEditText()).getText().toString();


        if (nameTmp.length() < 3 ){
            name.setError("name must be at list 3 characters long");
            name.setFocusable(true);
            progressBar.setVisibility(View.GONE);
            return false;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(emailTmp).matches()){
            email.setError("Invalid email");
            email.setFocusable(true);
            progressBar.setVisibility(View.GONE);
            return false;
        }
        if (passTmp.length()<6) {
            pass.setError("password need to be at least 6 characters long");
            pass.setFocusable(true);
            progressBar.setVisibility(View.GONE);
            return false;
        }
        return true;
    }

    @Override
    public void onClick(View view){
        createUser();
//        intent = new Intent(Sign_up_page.this, User_photo_dialog.class);
//        mStartDialog.launch(intent);
    }

}