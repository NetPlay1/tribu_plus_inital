package com.example.tribu_inital;

public class Activity_item {
    private String title;
    private int image;

    public Activity_item (String title,int Image){
        this.title = title;
        this.image = Image;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
