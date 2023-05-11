package com.example.tribu_inital.fragments;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;


import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Toast;


import com.example.tribu_inital.R;
import com.example.tribu_inital.User;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.io.File;
import java.util.Objects;

public class search_bar_fragment extends Fragment {

    ImageButton imageButton;

    PopupMenu popupMenu;

    FirebaseDatabase database;

    DatabaseReference ref;


    public search_bar_fragment() {
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
        View view = inflater.inflate(R.layout.search_bar_fragment, container, false);

        imageButton = view.findViewById(R.id.user_image);

        imageButton.setOnClickListener(view1 -> setUpMenu());


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        View view1 = inflater.inflate(R.layout.fragment_home_fragment, container, false);

        database = FirebaseDatabase.getInstance();

        ref = database.getReference("Users");

        ref = database.getReference(Objects.requireNonNull(user).getUid());






        return view;

    }



    //setting-up the account menu
    private void setUpMenu(){
        popupMenu = new PopupMenu(getContext(),getView());
        popupMenu.getMenuInflater().inflate(R.menu.account_menu,popupMenu.getMenu());

        //forcing the the menu to show icons
        popupMenu.setForceShowIcon(true);

        popupMenu.show();
    }


}