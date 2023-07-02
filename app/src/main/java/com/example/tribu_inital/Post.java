package com.example.tribu_inital;

import java.io.Serializable;

public class Post implements Serializable{

    private String title;

    private String description;

    private String uri;

    private String ownerUid;


    private String category;

    private String connectedgroup;




    public Post(String title, String description, String uri , String ownerUid,String Category,String connectedgroup) {

        this.title = title;
        this.description = description;
        this.uri = uri;
        this.ownerUid = ownerUid;
        this.category = category;
        this.connectedgroup = connectedgroup;

    }

    public Post(){

    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getOwnerUid() {
        return ownerUid;
    }

    public void setOwnerUid(String ownerUid) {
        this.ownerUid = ownerUid;
    }

    public String getConnectedgroup() {
        return connectedgroup;
    }

    public void setConnectedgroup(String connectedgroup) {
        this.connectedgroup = connectedgroup;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
