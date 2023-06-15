package com.example.tribu_inital.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tribu_inital.Activity_item;
import com.example.tribu_inital.Activity_list_adapter;
import com.example.tribu_inital.Add_Activity_page_fragment;
import com.example.tribu_inital.Post;
import com.example.tribu_inital.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.List;


public class home_fragment extends Fragment {


    FloatingActionButton addButton;

    FirebaseUser user;

    FirebaseDatabase database;

    FirebaseStorage firebaseStorage;

    DatabaseReference ref;

    public home_fragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home_fragment, container, false);

        addButton = view.findViewById(R.id.addButton);

        List<Post> items = new ArrayList<>();

        List<Activity_item> posts= new  ArrayList<>();

//        items.add(new Activity_item("john's bakery",R.drawable.baseline_add_circle_24));
//        items.add(new Activity_item("Bob's shop",R.drawable.baseline_add_circle_24));
//        items.add(new Activity_item("cabbage Was here",R.drawable.baseline_add_circle_24));

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);


        addButton.setOnClickListener(view1 -> showDialog());

        database = FirebaseDatabase.getInstance();

        ref = database.getReference("Posts");



        ref.addValueEventListener(new ValueEventListener(){


            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                // 
                List<Activity_item> posts= new  ArrayList<>();

                for(DataSnapshot dataSnapshot : snapshot.getChildren()) {

                        Log.d("homefragment", "" + dataSnapshot.getValue());
                        Post post = dataSnapshot.getValue(Post.class);
                        //Todo: learn to cash posts
                        //Todo: getting only the new posts
                        //Todo: getting all post when first loading the page
                        assert post != null;
                        posts.add(new Activity_item(post.getTitle(), post.getUri()));

                }
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                Activity_list_adapter adapter = new  Activity_list_adapter(getContext(),posts);
                recyclerView.setHasFixedSize(true);
                recyclerView.setAdapter(adapter);

                adapter.notifyDataSetChanged();

            }

            @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }


            });

        return view;
    }

    public void showDialog() {
        FragmentManager fragmentManager = getParentFragmentManager();
        Add_Activity_page_fragment newFragment = new Add_Activity_page_fragment();
        newFragment.show(fragmentManager, "dialog");

    }

}