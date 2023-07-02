package com.example.tribu_inital;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class Group_list_holder extends RecyclerView.ViewHolder{

    ImageView groupImage;

    TextView title, description;

    public Group_list_holder(@NonNull View itemView) {
        super(itemView);

        groupImage = itemView.findViewById(R.id.groupImage);
        title = itemView.findViewById(R.id.groupTitle);
        description = itemView.findViewById(R.id.groupDescription);

    }
}
