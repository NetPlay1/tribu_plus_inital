package com.example.tribu_inital;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
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
import android.widget.Toast;

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

    ActivityResultLauncher<Intent> mTakePhoto;

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
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    try {
                        Intent data = result.getData();

                        // checking for no nulls / that the task was completed successfully
                        if(result.getResultCode() != -1 || data == null) throw new Exception("Camera Intent wasn't successful");

                        Bundle extras = data.getExtras();
                        Bitmap imageBitmap = (Bitmap) extras.get("data");

                        intent = new Intent();

                        intent.putExtra("image", imageBitmap);
                        intent.putExtra("photo","photo");
                        setResult(RESULT_OK,intent);
                        finish();
                    }

                    catch (Exception e){
                        Toast.makeText(this,""+e.getMessage(),Toast.LENGTH_SHORT).show();
                        setResult(RESULT_CANCELED,intent);
                        finish();
                    }


                }

        );

        mPhotoFromGallery = registerForActivityResult(
          new ActivityResultContracts.StartActivityForResult(),
                result -> {
                try {
                    Intent data = result.getData();

                    // checking for no nulls / that the task was completed successfully
                    if(result.getResultCode() != -1 || data == null) throw new Exception("Gallery Intent wasn't successful");


                    /*
                         if everything was successful
                         creating new intent setting the data the gallery intent data
                         and setting the result of this Activity result as successful
                     */
                    intent = new Intent();
                    intent.setData(data.getData());
                    getBaseContext().getContentResolver().takePersistableUriPermission(data.getData(), Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                    setResult(RESULT_OK,intent);
                    finish();
                }
                catch (Exception e) {
                    Toast.makeText(this,""+e.getMessage(),Toast.LENGTH_LONG).show();
                    setResult(RESULT_CANCELED,intent);
                    finish();
                }
          }
        );

        Permissions.requestPermission(this);
    }
    @Override
    public void onClick(View view) {
        if(view == skipButton) finish();

        if(view == galleryButton){
            intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.setType("image/*");
            intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            mPhotoFromGallery.launch(intent);
        }

        if(view == cameraButton){
            try {
                if(!Permissions.hasCameraPermission(getApplicationContext())) throw new Exception("No camera permission");

                intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                mTakePhoto.launch(intent);
            }

            catch (Exception e){
                Toast.makeText(this,""+e.getMessage(),Toast.LENGTH_SHORT).show();
                setResult(RESULT_CANCELED);
                finish();
            }
        }


    }





}


