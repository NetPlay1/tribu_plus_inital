package com.example.tribu_inital;

import android.net.Uri;

import java.io.Serializable;
import java.util.UUID;

public class User implements Serializable {
    private String name;
    private String email;
    private String password;

    // uri is the user photo path
    private String uri;

    private String key;

    public User(String name,String email, String password,String uri){
        this.name =name;
        this.email=email;
        this.password=password;
        this.uri=uri;
        //photo = "/mipmap-hdpi/user.png";

        //generating the user key / path
        key = UUID.randomUUID().toString();

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
