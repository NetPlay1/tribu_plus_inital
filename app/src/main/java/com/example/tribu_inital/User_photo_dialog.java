package com.example.tribu_inital;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class User_photo_dialog extends AppCompatActivity implements View.OnClickListener {


    Button cameraButton, galleryButton, skipButton;

    ActivityResultLauncher<String> mTakePhoto;

    Intent intent;

    //Uri uri;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_photo_picker_dialog);

        cameraButton=findViewById(R.id.cameraButton);
        galleryButton=findViewById(R.id.galleryButton);
        skipButton=findViewById(R.id.skipButton);

        cameraButton.setOnClickListener(this);
        galleryButton.setOnClickListener(this);
        skipButton.setOnClickListener(this);

        mTakePhoto = registerForActivityResult(
                new ActivityResultContracts.GetContent(),
                result -> {
                    intent = new Intent();
                    intent.putExtra("uri",result);
                    setResult(RESULT_OK,intent);
                    this.finish();
                }
        );

    }
    @Override
    public void onClick(View view) {
        if(view == skipButton) finish();

        if(view == galleryButton){
            intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, 1);
        }

        if(view == cameraButton){
            mTakePhoto.launch("image/*");

        }


    }



}


