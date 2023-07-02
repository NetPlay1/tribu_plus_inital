package com.example.tribu_inital.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tribu_inital.Activity_item;
import com.example.tribu_inital.Activity_list_adapter;
import com.example.tribu_inital.Group;
import com.example.tribu_inital.Group_list_adapter;
import com.example.tribu_inital.Post;
import com.example.tribu_inital.R;
import com.example.tribu_inital.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class chat_page_fragment extends Fragment {


    FirebaseDatabase database;

    FirebaseStorage firebaseStorage;

    StorageReference storeRef;

    DatabaseReference ref;
    List<Group> groups;  //TODO: Chang Group here to something else

    ArrayList<Group> myGroups;

    Group_list_adapter adapter;




    public chat_page_fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chat_page_fragment, container, false);

        groups = new ArrayList<>();
        myGroups = new ArrayList<>();


        RecyclerView recyclerView = view.findViewById(R.id.GroupList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new Group_list_adapter(getContext() , myGroups);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);


        database = FirebaseDatabase.getInstance();

        ref = database.getReference("Groups");

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


        ValueEventListener eventListener = ref.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //clearing the whole list
                groups.clear();
                myGroups.clear();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Group group = dataSnapshot.getValue(Group.class);




                    groups.add(group);
                    if(group.getMemberUids().contains(user.getUid())) {
                        myGroups.add(group);
                    }

                    adapter.notifyDataSetChanged();
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });





        return view;
    }
}