package com.example.tribu_inital;

import static androidx.core.app.ActivityCompat.startActivityForResult;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class User_photo_dialog_v2 extends Dialog implements View.OnClickListener {

    private static final int PICK_IMAGE = 1;
    private Context context;


    Button quitButton,skipButton, cameraButton, gallaryButton;

    Intent intent=new Intent();

    public User_photo_dialog_v2(@NonNull Context context) {
        super(context);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
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
            dismiss();
        //on press opens the camera TODO: camera is a work in progress
        if(view == cameraButton){
            intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            getOwnerActivity().startActivityForResult(intent,PICK_IMAGE);
            dismiss();


        }
        //on press opens gallery
        if(view == gallaryButton){

        }
    }

}
