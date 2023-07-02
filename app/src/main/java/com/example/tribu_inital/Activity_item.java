package com.example.tribu_inital;



import android.graphics.Bitmap;

import java.io.Serializable;
import java.util.UUID;

public class Activity_item implements Serializable {
    private String title;
    private Bitmap bpm;

    private String connectedPost;

    public String getConnectedgroup() {
        return connectedgroup;
    }

    public void setConnectedgroup(String connectedgroup) {
        this.connectedgroup = connectedgroup;
    }

    private String connectedgroup;

    public Activity_item (String title, Bitmap bpm, String connectedPost,String connectedgroup){
        this.title = title;
        this.bpm = bpm;
        this.connectedPost = connectedPost;
        this.connectedgroup = connectedgroup;
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

    public String getConnectedPost() {
        return connectedPost;
    }

    public void setConnectedPost(String connectedPost) {
        this.connectedPost = connectedPost;
    }
}
