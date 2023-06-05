package com.example.tribu_inital.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import android.util.Log;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.PopupMenu;


import com.example.tribu_inital.R;
import com.example.tribu_inital.start.Start_page;
import com.example.tribu_inital.user_page_fragment;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Objects;

public class search_bar_fragment extends Fragment {

    ImageView imageButton;

    PopupMenu popupMenu;

    FirebaseDatabase database;

    StorageReference storeRef;


    DatabaseReference ref;

    Intent intent;

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;


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

        fragmentManager = requireActivity().getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        imageButton = view.findViewById(R.id.user_image);

        imageButton.setOnClickListener(view1 -> setUpMenu());


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        View view1 = inflater.inflate(R.layout.fragment_home_fragment, container, false);

        database = FirebaseDatabase.getInstance();

        String uuid = Objects.requireNonNull(user).getUid();

        ref = database.getReference("Users");

        ref = ref.child(uuid);


        ref.get().addOnCompleteListener(task -> {
            if(!task.isSuccessful()){
                Log.e("firebase","error getting the data"+ task.getException());

            }

            //TODO: clean code

            // getting a the photo name from current user
            String uri_name = Objects.requireNonNull
                    (task.getResult().child("uri").getValue()).toString();

            //pointing to storage folders
            storeRef = FirebaseStorage.getInstance().getReference("Images/Users/"+uuid);
            //getting image bytes
            storeRef.child(uri_name).getBytes(Long.MAX_VALUE).addOnSuccessListener(bytes -> {
                //decoding bytes
                Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

                imageButton.setImageBitmap
                        (Bitmap.createScaledBitmap(bmp, 30,30,false));
            });



        }).addOnFailureListener(e -> {
            Log.d("not working",""+e.getMessage());
        });



        return view;

    }



    //setting-up the account menu
    private void setUpMenu(){
        popupMenu = new PopupMenu(getContext(),getView());
        popupMenu.getMenuInflater().inflate(R.menu.account_menu,popupMenu.getMenu());

        //forcing the the menu to show icons
        popupMenu.setForceShowIcon(true);

        popupMenu.setOnMenuItemClickListener(menuItem -> {
            if(menuItem.getItemId() == R.id.logout_icon) {
                FirebaseAuth.getInstance().signOut();

                Intent intent= new Intent(getActivity(), Start_page.class);
                startActivity(intent);
            }
            fragmentTransaction.addToBackStack(null);
            FragmentTransaction fragmentTransaction1 = fragmentManager.beginTransaction();
            switch (menuItem.getItemId()) {
                case R.id.account_setting_page:
                    //TODO: add the page
                    break;

                case R.id.account_info_icon:
                    fragmentTransaction1.replace
                            (R.id.fragment_container, new user_page_fragment()).commit();

                    break;

                case R.id.logout_icon:
                    FirebaseAuth.getInstance().signOut();

                    Intent intent= new Intent(getActivity(), Start_page.class);
                    startActivity(intent);
                    break;
            }


            return false;
        });



        popupMenu.show();
    }



}