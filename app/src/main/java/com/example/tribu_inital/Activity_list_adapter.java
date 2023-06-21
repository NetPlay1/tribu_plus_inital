package com.example.tribu_inital;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.shape.CornerFamily;
import com.google.android.material.shape.ShapeAppearanceModel;

import java.util.List;

public class Activity_list_adapter extends RecyclerView.Adapter<Activity_list_holder> {

    Context context;

    List<Activity_item> items;


    public  Activity_list_adapter(Context context, List items){
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public Activity_list_holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //getting new Activity_list holder and linking it with the Item layout
        return new Activity_list_holder(LayoutInflater.from(context).inflate(R.layout.activity_item, parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull Activity_list_holder holder, int position) {
        //Todo: build the whole activity system and get the image view from the data base
        //this is just a test


        holder.title.setText(items.get(position).getTitle());
        holder.imageView.setImageBitmap(
                (Bitmap.createScaledBitmap(items.get(position).getBpm()
                        ,items.get(position).getBpm().getWidth(),
                                items.get(position).getBpm().getHeight(),
                                false)));
        Log.d("activity-adapter", "image loaded ?");


    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
