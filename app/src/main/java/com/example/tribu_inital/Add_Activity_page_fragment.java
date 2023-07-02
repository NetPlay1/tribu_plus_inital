package com.example.tribu_inital;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.tribu_inital.auth.Sign_up_page;
import com.example.tribu_inital.fragments.chat_page_fragment;
import com.example.tribu_inital.fragments.home_fragment;
import com.example.tribu_inital.start.Loading_page;
import com.example.tribu_inital.start.Start_page;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Add_Activity_page_fragment extends Fragment implements View.OnClickListener {

    Button dissmisButton, postButton;
    TextInputLayout titleInput, descripionInput;

    FirebaseDatabase database;

    StorageReference storeRef;


    DatabaseReference ref;

    FirebaseUser user;

    ImageView ChangeImage;

    ActivityResultLauncher<Intent> dialogResult;

    String uri;

    String picName;

    byte[] bytes;


    RelativeLayout layout;

    ProgressBar progressBar;

    public Add_Activity_page_fragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dialogResult = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                        Intent data = result.getData();

                    // checking for no nulls / that the task was completed successfully
                    if(result.getResultCode() !=-1 || data == null) return;

                    if((Bitmap) data.getExtras().get("image") != null) {


                        Bitmap bitmap = (Bitmap) data.getExtras().get("image");

                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG,100, baos);
                        bytes = baos.toByteArray();
                    }else {
                        uri = String.valueOf(data.getData());
                    }

                });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add__activity_page_fragment, container, false);

        dissmisButton = view.findViewById(R.id.dismissdButton);
        postButton = view.findViewById(R.id.postButton);
        descripionInput = view.findViewById(R.id.descriptionInput);
        titleInput = view.findViewById(R.id.titleInput);
        ChangeImage = view.findViewById(R.id.changeImage);

        layout = view.findViewById(R.id.Loading);

        progressBar = new ProgressBar(getContext(), null, android.R.attr.progressBarStyleLarge);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(100, 100);
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        layout.addView(progressBar, params);

        progressBar.setVisibility(View.GONE);


        //default uri
        uri = "android.resource://com.example.tribu_inital/"+R.mipmap.ic_launcher;

        picName = System.currentTimeMillis() + ".jpg";



        dissmisButton.setOnClickListener(view1 -> {
            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.replace
                    (R.id.fragment_container, new home_fragment()).commit();
        });

        postButton.setOnClickListener(this);


        database = FirebaseDatabase.getInstance();

        user = FirebaseAuth.getInstance().getCurrentUser();


        ChangeImage.setOnClickListener(view1 -> {
            Intent intent = new Intent(getActivity(), User_photo_dialog.class);
            dialogResult.launch(intent);
        });


        return view;
    }



    @Override
    public void onClick(View view) {
        //getting the Edit-text from TextInput layout
        EditText title = titleInput.getEditText();
        EditText description = descripionInput.getEditText();

        titleInput.setError(null);
        descripionInput.setError(null);

        try {

            //gates

            assert title != null;
            if (title.getText().toString().isEmpty()) {
                titleInput.setError("title string is empty!");
                titleInput.setFocusable(true);
                throw new Exception("title string is empty!");
            }
            assert description != null;
            if (description.getText().toString().isEmpty()) {
                descripionInput.setError("Description string is empty!");
                descripionInput.setFocusable(true);
                throw new Exception("Description string is empty!");
            }


            //TODO: finish adding the categories
            String uuid2 = String.valueOf(UUID.randomUUID());
            Post post = new Post(title.getText().toString(), description.getText().toString(), picName, user.getUid(),"default",uuid2);
            DatabaseReference ref2 = ref;
            ref = database.getReference("Posts");


            String uuid = String.valueOf(UUID.randomUUID());

            ref.child(uuid).setValue(post);


            storeRef = FirebaseStorage.getInstance().getReference("Images/Posts/"+picName);

            ArrayList<String> UsersList = new ArrayList<>();

            UsersList.add(user.getUid());
            Group group = new Group(title.getText().toString(), description.getText().toString(), picName, uuid, UsersList, user.getUid(),uuid2);

            ref2 = database.getReference("Groups");


            ref2.child(uuid2).setValue(group);

            if(bytes == null) {
                storeRef.putFile(Uri.parse(uri))
                        .addOnSuccessListener(taskSnapshot -> {
                            Log.d("addActivityFragment", "post was added");
                        }).addOnFailureListener(e -> {
                            Log.d("addActivityFragment", "Error" + e.getMessage());

                        });

                loadThread.start();

            }

            UploadTask uploadTask = (UploadTask) storeRef.putBytes(bytes).addOnSuccessListener(taskSnapshot -> {
                Log.d( "addActivityFragment","post uploaded successfully");
            }).addOnFailureListener(e ->{
                Log.d("addActivityFragment","failed to post"+e.getMessage());
            });



            loadThread.start();


        } catch (Exception e) {
            Log.d("addActivityFragment", "" + e.getMessage());
        }


    }


    //TODO: fix gallery image

    Thread loadThread = new Thread() {
        @Override
        public void run() {
            super.run();

            synchronized (this) {
                try {
                    requireActivity().runOnUiThread(() -> {
                        progressBar.setVisibility(View.VISIBLE);
                    });

                    sleep(2000);

                    requireActivity().runOnUiThread(() -> {
                        progressBar.setVisibility(View.GONE);
                    });

                }

                catch (Exception e){
                    Log.e("home_fragment","error in thread "+e.getMessage());
                }

                finally {
                    FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.replace
                            (R.id.fragment_container, new home_fragment()).commit();
                }


            }

        }
    };



}