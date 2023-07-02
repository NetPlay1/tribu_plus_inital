package com.example.tribu_inital;

import com.google.firebase.database.Exclude;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Group implements Serializable {

    private String title;

    private String imageUri;

    private String description;

    private String ogPostUid;

    private String OpUid;

    private List<String> memberUids;

    private String OpUser;
    private String groupuid;


    public Group(String title,String description,String image ,String ownerUid, List<String> list,String OpUser,String groupuid){
        this.title = title;
        this.description = description;
        this.imageUri = image;
        this.ogPostUid = ownerUid;
        this.memberUids = list;
        this.OpUid = OpUid;
        this.groupuid = groupuid;
    }

    public Group (){

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOgPostUid() {
        return ogPostUid;
    }

    public void setOgPostUid(String ogPostUid) {
        this.ogPostUid = ogPostUid;
    }

    public List<String> getMemberUids() {
        return memberUids;
    }

    public void setMemberUid(String memberUids) {
        this.memberUids.add(memberUids);
    }

    public String getOpUser() {
        return OpUser;
    }

    public void setOpUser(String opUser) {
        OpUser = opUser;
    }

    public String getGroupuid() {
        return groupuid;
    }

    public void setGroupuid(String groupuid) {
        this.groupuid = groupuid;
    }
}
