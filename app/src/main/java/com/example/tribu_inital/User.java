package com.example.tribu_inital;

import android.net.Uri;

import java.io.Serializable;
import java.util.UUID;

// Todo: ask zehava why do we need to build user 2 times

public class User implements Serializable {
    private String name;

    private String uri;

    public User(String name, String uri){
        this.name =name;
        this.uri=uri;
        //photo = "/mipmap-hdpi/user.png";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

}
