package com.example.tribu_inital;

import static androidx.core.app.ActivityCompat.startActivityForResult;

import android.app.Activity;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class User_photo_dialog extends AppCompatActivity implements View.OnClickListener {


    private static final int PICK_IMAGE = 1;
    private Context context;


    Button quitButton,skipButton, cameraButton, gallaryButton;

    Intent intent=new Intent();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_photo_picker_dialog);
        quitButton=findViewById(R.id.quitButton);
        skipButton=findViewById(R.id.skipButton);
        cameraButton=findViewById(R.id.cameraButton);
        gallaryButton=findViewById(R.id.galleryButton);

        skipButton.setOnClickListener(this);
        cameraButton.setOnClickListener(this);
        gallaryButton.setOnClickListener(this);






    }
    @Override
    public void onClick(View view) {
        //if pressed will dismiss the dialog
        if(view == skipButton && quitButton == skipButton)
            finish();



        //on press opens the camera TODO: camera is a work in progress
        if(view == cameraButton){
            intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);


        }
        //on press opens gallery
        if(view == gallaryButton){
             intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

             startActivityForResult(intent,PICK_IMAGE);






        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Match the request 'pic id with requestCode
        if (requestCode == PICK_IMAGE) {
            // BitMap is data structure of image file which store the image in memory
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            // Set the image in imageview for display

        }
    }


}



