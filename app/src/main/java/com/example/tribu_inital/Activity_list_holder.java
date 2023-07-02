package com.example.tribu_inital;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.shape.CornerFamily;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Activity_list_holder extends RecyclerView.ViewHolder{

    ShapeableImageView imageView;

    TextView title;
    MaterialButton joinButton;

    FirebaseUser user;



    public Activity_list_holder(@NonNull View itemView) {
        super(itemView);
        title = itemView.findViewById(R.id.activity_title);
        imageView = itemView.findViewById(R.id.activity_image);
        joinButton = itemView.findViewById(R.id.joinActivity);


    }

    public void checkUserExits(final String postkey,final String userid){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("Groups");

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        ref.addValueEventListener(new ValueEventListener(){
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.child(postkey).child("memberUids").hasChild(userid)){
                    Log.d("activity-holder","account already found");
                }else {
                    Log.d("activity-holder","all clear uid="+userid);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}