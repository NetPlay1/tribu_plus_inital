package com.example.tribu_inital.fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
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


public class home_fragment extends Fragment {


    FloatingActionButton addButton;



    FirebaseUser user;

    FirebaseDatabase database;

    FirebaseStorage firebaseStorage;

    StorageReference storeRef;

    DatabaseReference ref;
    List<Activity_item> posts;

    Activity_list_adapter adapter;

    Bitmap bmp;
    public home_fragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("homefragment", "on create" );

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("homefragment", "createView" );

        View view = inflater.inflate(R.layout.fragment_home_fragment, container, false);

        addButton = view.findViewById(R.id.addButton);



        posts= new ArrayList<>();




        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        /*
        examples for list item
                                        title               image (old version now you need bitmap)
        items.add(new Activity_item("john's bakery",R.drawable.baseline_add_circle_24));
        */
//setting up recyclerView
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new Activity_list_adapter(getContext(),posts);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);



        addButton.setOnClickListener(view1 -> addNewPost());


        database = FirebaseDatabase.getInstance();

        ref = database.getReference("Posts");

        //delay because firebase is a bit slow

        ValueEventListener eventListener = ref.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                //clearing the whole list
                posts.clear();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    Post post = dataSnapshot.getValue(Post.class);
                    assert post != null;
                    Log.d("homefragment", "" + post.getTitle());

                    storeRef = FirebaseStorage.getInstance().getReference("Images/Posts/");

                    storeRef.child(post.getUri()).getBytes(Long.MAX_VALUE).addOnSuccessListener(bytes -> {
                        //decoding bytes
                        bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                        Log.d("homefragment", "found image");

                        posts.add(new Activity_item(post.getTitle(), bmp, dataSnapshot.getKey(),post.getConnectedgroup()));



                        // reloading the whole list (i know it's not efficient)
                        adapter.notifyDataSetChanged();


                    }).addOnFailureListener(e -> {
                        Log.e("homefragment", "" + e.getMessage());

                    });

                }

                Log.d("homefragment", "--adapter change--");


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }


        });


        return view;
    }

    public void addNewPost() {

        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.replace
                (R.id.fragment_container, new Add_Activity_page_fragment()).commit();
    }

}