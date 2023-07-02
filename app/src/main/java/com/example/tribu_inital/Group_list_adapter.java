package com.example.tribu_inital;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class Group_list_adapter extends RecyclerView.Adapter<Group_list_holder> {


    Context context;

    List<Group> items;


    public  Group_list_adapter(Context context, List items){
        this.context = context;
        this.items = items;
    }



    @NonNull
    @Override
    public Group_list_holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      return new Group_list_holder(LayoutInflater.from(context).inflate(R.layout.group_item, parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull Group_list_holder holder, int position) {
        holder.title.setText(items.get(position).getTitle());
        holder.description.setText(items.get(position).getDescription());

    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
