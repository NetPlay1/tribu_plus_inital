package com.example.tribu_inital;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.provider.SyncStateContract;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ThemedSpinnerAdapter;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import java.io.File;
import java.net.URI;

public class User_photo_dialog extends AppCompatActivity implements View.OnClickListener {


    Button cameraButton, galleryButton, skipButton;

    ActivityResultLauncher mTakePhoto;

    ActivityResultLauncher<Intent> mPhotoFromGallery;

    Intent intent;

    Uri uri;




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
                new ActivityResultContracts.TakePicture(),
                result -> {
                    if(!result) finish();
                    intent = new Intent();
                    intent.setData(uri);
                    finish();



                }

        );

        mPhotoFromGallery = registerForActivityResult(
          new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if(result.getResultCode() != -1) return;
                    intent = new Intent();
                    Intent data = result.getData();
                    assert data != null;
                    intent.setData(data.getData());
                    getBaseContext().getContentResolver().takePersistableUriPermission(data.getData(), Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                    setResult(RESULT_OK,intent);
                    finish();
                    }
        );

//
//        if (ActivityCompat.checkSelfPermission(User_photo_dialog.this,
//                Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED)
//            finish();

    }
    @Override
    public void onClick(View view) {
        if(view == skipButton) finish();

        if(view == galleryButton){
            intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.setType("image/*");
            intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            mPhotoFromGallery.launch(intent);
            //mPhotoFromGallery.launch("image/*");
        }

        if(view == cameraButton){

          mTakePhoto.launch(uri);

        }


    }



}


