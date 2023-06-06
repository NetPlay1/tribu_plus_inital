package com.example.tribu_inital;

import android.app.Dialog;
import android.os.Bundle;

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

import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class Add_Activity_page_fragment extends DialogFragment implements View.OnClickListener {

    Button dissmisButton, postButton;
    TextInputLayout titleInput, descripionInput;
    public Add_Activity_page_fragment() {
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
        View view = inflater.inflate(R.layout.fragment_add__activity_page_fragment, container, false);

        dissmisButton = view.findViewById(R.id.dismissdButton);
        postButton = view.findViewById(R.id.postButton);
        descripionInput = view.findViewById(R.id.descriptionInput);
        titleInput = view.findViewById(R.id.titleInput);

        dissmisButton.setOnClickListener(view1 -> dismiss());

        postButton.setOnClickListener(this);

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
        if(title.getText().toString().isEmpty()) {
            titleInput.setError("title string is empty!");
            titleInput.setFocusable(true);
            throw new Exception("title string is empty!");
        }
        assert description != null;
        if(description.getText().toString().isEmpty()) {
            descripionInput.setError("Description string is empty!");
            descripionInput.setFocusable(true);
            throw new Exception("Description string is empty!");
        }

        //TODO: uploading the posts to fire base







    }

    catch (Exception e){
        Log.d("addActivityFragment", ""+e.getMessage());
    }











    }
}