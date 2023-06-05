package com.example.tribu_inital;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Objects;


public class user_page_fragment extends Fragment {

    FirebaseDatabase database;

    StorageReference storeRef;


    DatabaseReference ref;


    ShapeableImageView userImage;

    TextView userName;

    public user_page_fragment() {
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
        View view = inflater.inflate(R.layout.user_page_fragment, container, false);

        userImage = view.findViewById(R.id.bigUserImage);
        userName = view.findViewById(R.id.nameText);


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


        String uuid = Objects.requireNonNull(user).getUid();

        database = FirebaseDatabase.getInstance();

        ref = database.getReference("Users");

        ref = ref.child(uuid);

        ref.get().addOnCompleteListener(task -> {
            if(!task.isSuccessful()){
                Log.e("firebase","error getting the data"+ task.getException());
            }

            String name = Objects.requireNonNull
                    (task.getResult().child("name").getValue()).toString();




            userName.setText(name);



            String uri_name = Objects.requireNonNull
                    (task.getResult().child("uri").getValue()).toString();

            //pointing to storage folders
            storeRef = FirebaseStorage.getInstance().getReference("Images/Users/"+uuid);
            //getting image bytes
            storeRef.child(uri_name).getBytes(Long.MAX_VALUE).addOnSuccessListener(bytes -> {
                //decoding bytes
                Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

                userImage.setImageBitmap
                        (Bitmap.createScaledBitmap(bmp, 250,250,false));
            });


        });


        return view;
    }
}