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
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.Objects;
import java.util.UUID;

public class Add_Activity_page_fragment extends DialogFragment implements View.OnClickListener {

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

                        //Todo: create func that look like thiscontinueCreatingUser("gallery");

                        Bitmap bitmap = (Bitmap) data.getExtras().get("image");

                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG,100, baos);
                        bytes = baos.toByteArray();
                    }else {
                        uri = String.valueOf(data.getData());
                        //Todo: create func that look like thiscontinueCreatingUser("gallery");
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

        //default uri
        uri = "android.resource://com.example.tribu_inital/"+R.mipmap.ic_launcher;

        picName = System.currentTimeMillis() + ".jpg";



        dissmisButton.setOnClickListener(view1 -> dismiss());

        postButton.setOnClickListener(this);


        database = FirebaseDatabase.getInstance();

        user = FirebaseAuth.getInstance().getCurrentUser();


        ChangeImage.setOnClickListener(view1 -> {
            Intent intent = new Intent(getActivity(), User_photo_dialog.class);
            dialogResult.launch(intent);
        });


        return view;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // The only reason you might override this method when using onCreateView() is
        // to modify any dialog characteristics. For example, the dialog includes a
        // title by default, but your custom layout might not need it. So here you can
        // remove the dialog title, but you must call the superclass to get the Dialog.
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
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

            //TODO: uploading the posts to fire base


            Post post = new Post(title.getText().toString(), description.getText().toString(), picName, user.getUid());

            ref = database.getReference("Posts");

            String uuid = String.valueOf(UUID.randomUUID());

            ref.child(uuid).setValue(post);


            storeRef = FirebaseStorage.getInstance().getReference("Images/Posts/"+picName);

            if(bytes == null) {
                storeRef.putFile(Uri.parse(uri))
                        .addOnSuccessListener(taskSnapshot -> {
                            Log.d("addActivityFragment", "post was added");
                        }).addOnFailureListener(e -> {
                            Log.d("addActivityFragment", "Error" + e.getMessage());

                        });
                dismiss();
                return;
            }

            UploadTask uploadTask = (UploadTask) storeRef.putBytes(bytes).addOnSuccessListener(taskSnapshot -> {
                Log.d( "addActivityFragment","post uploaded successfully");
            }).addOnFailureListener(e ->{
                Log.d("addActivityFragment","failed to post"+e.getMessage());
            });

            loadThread.start();

            dismiss();


        } catch (Exception e) {
            Log.d("addActivityFragment", "" + e.getMessage());
        }



    }
    Thread loadThread = new Thread() {
        @Override
        public void run() {
            super.run();

            synchronized (this) {


                // waiting for 3 seconds
                try{

                    wait(3000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }

    };

}