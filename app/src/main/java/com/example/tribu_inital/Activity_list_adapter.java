package com.example.tribu_inital;

import static androidx.core.content.ContentProviderCompat.requireContext;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.shape.CornerFamily;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class Activity_list_adapter extends RecyclerView.Adapter<Activity_list_holder> {

    Context context;

    List<Activity_item> items;

    List <String> existingUids;

    FirebaseUser user;

    String postUUID;


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
        existingUids = new ArrayList<>();

        holder.title.setText(items.get(position).getTitle());
        holder.imageView.setImageBitmap(
                (Bitmap.createScaledBitmap(items.get(position).getBpm()
                        ,items.get(position).getBpm().getWidth(),
                                items.get(position).getBpm().getHeight(),
                                false)));
        Log.d("activity-adapter", "image loaded ?");


        user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("Groups");
        String groupKey = items.get(position).getConnectedgroup();



        holder.checkUserExits(groupKey,user.getUid());

        holder.joinButton.setOnClickListener(view -> {

            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    Log.d("activity-adapter","");
                    Log.d("activity-adapter",""+snapshot.child(groupKey).child("memberUids").getValue().toString().replace("[","").replace("]",""));

                    for (DataSnapshot dataSnapshot: snapshot.child(groupKey).child("memberUids").getChildren()){
                        existingUids.add((String) dataSnapshot.getValue());
                      }
                        if(existingUids.contains(user.getUid())){
                            Log.d("activity-adapter","already exists userid:"+user.getUid());
                        }else {
                            Group group = snapshot.child(groupKey).getValue(Group.class);

                            Log.d("activity-adapter", "notexist:" + group.getTitle());
                            group.setMemberUid(user.getUid());
                            ref.child(groupKey).child("memberUids").setValue(group.getMemberUids());
                        }
//                    if(snapshot.child(groupKey).child("memberUids").getValue().toString().replace("[", "").replace("]", "").equals(user.getUid())){
//                        Log.d("activity-adapter","already exists userid:"+user.getUid());
//                    }else {
//                        Group group = snapshot.child(groupKey).getValue(Group.class);
//
//                        Log.d("activity-adapter", "notexist:" + group.getTitle());
//                        group.setMemberUid(user.getUid());
//                        ref.child(groupKey).child("memberUids").setValue(group.getMemberUids());
//                    }


                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        });

//            postUUID = items.get(position).getConnectedPost();
//
//            if(user != null){
//
//                FirebaseDatabase database = FirebaseDatabase.getInstance();
//                DatabaseReference ref = database.getReference("Groups");
//
//               ValueEventListener eventListener=ref.addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        for (DataSnapshot dataSnapshot: snapshot.getChildren()){
//                            //Log.d("activity-adapter",""+ dataSnapshot.getValue().toString());
//                            Group group = dataSnapshot.getValue(Group.class);
//                            assert group != null;
//                            if(!dataSnapshot.child("memberUids").hasChild(user.getUid())){
//                            if(Objects.equals(group.getOgPostUid(), postUUID)) {
//
//                                //Log.d("activity-adapter", "post uid"+ group.getOgPostUid()+" vs"+postUUID);
//                                Log.d("activity-adapter", "1:" + user.getUid() + "vs" + group.getOpUser() + "2");
//                                if (!user.getUid().equals(group.getOpUser())) {
//                                    group.setMemberUid(user.getUid());
//                                    Log.d("activity-adapter", "the same uuid is:" + group.getTitle());
//                                    ref.child(Objects.requireNonNull(dataSnapshot.getKey())).child("memberUids").setValue(group.getMemberUids());
//                                    Log.d("activity-adapter", "after");
//                                } else {
//                                    Toast.makeText(holder.itemView.getContext(), "you are the creator", Toast.LENGTH_SHORT).show();
//                                }
//                            }
//                                // todo finish the list of users
//                                //Todo: add the users to chat
//                                //TOdo: chat functionality
//                                //Todo: final clean up
//
//
//
//                            }else {
//                                Toast.makeText(holder.itemView.getContext(), "you already joined", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                });
//               ref.addValueEventListener(eventListener);
//                ref.removeEventListener(eventListener);
//



//                ref.get().addOnCompleteListener(task -> {
//                    if (!task.isSuccessful()) {
//                        Log.e("firebase21", "Error getting data", task.getException());
//                    }
//                    else {
//                        Log.d("firebase21", String.valueOf(task.getResult().getValue()));
//                        DA
//
//
//                        Log.d("firebase21",""+group.getTitle());
//
//                    }

                //});





    }


    @Override
    public int getItemCount() {
        return items.size();
    }

}
