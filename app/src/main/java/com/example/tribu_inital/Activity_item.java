package com.example.tribu_inital;



import android.graphics.Bitmap;

import java.io.Serializable;

public class Activity_item implements Serializable {
    private String title;
    private Bitmap bpm;

    public Activity_item (String title, Bitmap bpm){
        this.title = title;
        this.bpm = bpm;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Bitmap getBpm(){
        return bpm;
    }

    public void setBpm(Bitmap bpm) {

        this.bpm = bpm;
    }
}
