package com.example.tribu_inital;

import android.net.Uri;

import java.io.Serializable;

public class Activity_item implements Serializable {
    private String title;
    private String uri;

    public Activity_item (String title,String uri){
        this.title = title;
        this.uri = uri;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUri(){
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}
