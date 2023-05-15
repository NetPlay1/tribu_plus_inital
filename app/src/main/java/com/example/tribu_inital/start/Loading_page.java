package com.example.tribu_inital.start;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.widget.ProgressBar;

import com.example.tribu_inital.R;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class Loading_page extends AppCompatActivity {

    ProgressBar progressBar;

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_page);
        progressBar = findViewById(R.id.progressBar);
        /*
        thread that wait 3 seconds before opening the app
        while checking for internet connection
        if no internet connection if none shows alert dialog
         */
        Thread mSplashThread = new Thread() {
            @Override
            public void run() {
                super.run();
                try {

                    synchronized (this) {
                        //checking for internet connection if null it means there is none
                        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                        if (connectivityManager.getActiveNetwork() == null)
                            throw new Exception("no internet Connectivity");

                        // waiting for 3 seconds
                        wait(3000);
                    }

                    // if everything was successful load the next page
                    intent = new Intent(Loading_page.this, Start_page.class);
                    startActivity(intent);
                    finish();
                } catch (Exception e) {
                    //showing an alert dialog because user has no internet
                    Loading_page.this.runOnUiThread(() -> {
                        AlertDialog dialog = new MaterialAlertDialogBuilder(Loading_page.this)
                                .setTitle("" + e.getMessage())
                                .setMessage("Do you want to restart the app")

                                .setPositiveButton("YES", (dialogInterface, i) -> {

                                    intent = new Intent(Loading_page.this, Loading_page.class);
                                    startActivity(intent);
                                    finish();

                                    dialogInterface.dismiss();
                                })

                                .setNegativeButton("NO", (dialogInterface, i) -> {
                                    finish();
                                    System.exit(0);
                                })

                                .show();
                    });
                }
            }


        };
        //starting the loading
        mSplashThread.start();
    }
}