package com.example.tribu_inital;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import androidx.annotation.NonNull;

public class User_photo_dialog extends Dialog implements View.OnClickListener {

    public User_photo_dialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_photo_picker_dialog);


    }
    @Override
    public void onClick(View view) {

    }
}


