package com.example.tribu_inital;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class Activity_list_holder extends RecyclerView.ViewHolder{

    ImageView imageView;
    TextView title;
    Button joinButton;

    public Activity_list_holder(@NonNull View itemView) {
        super(itemView);
        title = itemView.findViewById(R.id.activity_title);
        //imageView = itemView.findViewById(R.id.activity_image);
    }
}