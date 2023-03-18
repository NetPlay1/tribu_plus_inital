package com.example.tribu_inital;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link User_photo_dialog_fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
//public class User_photo_dialog_fragment extends DialogFragment implements View.OnClickListener{
//
//    // TODO: Rename parameter arguments, choose names that match
//    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
//    // TODO: Rename and change types of parameters
//
//
//
//
//    private String mParam1;
//    private String mParam2;
//
//    Sign_up_page callBackActivity;
//
//    private Button cameraButton, galleryButton, skipButton, quitButton;
//
//
//    public interface onSomeEventListener {
//        public void someEvent(String s);
//    }
//
//    onSomeEventListener someEventListener;
//
//
//    public User_photo_dialog_fragment() {
//        // Required empty public constructor
//    }
//
//
//
//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment User_photo_dialog_fragment.
//     */
//    // TODO: Rename and change types and number of parameters
//    public static User_photo_dialog_fragment newInstance(String param1, String param2) {
//        User_photo_dialog_fragment fragment = new User_photo_dialog_fragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
//
//    }
//
//    @Override
//    public void onResume() {
//        Objects.requireNonNull(getDialog()).getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT,1300);
//        super.onResume();
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        Objects.requireNonNull(getDialog()).getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//
//       ViewGroup root = (ViewGroup) inflater.inflate(R.layout.user_photo_picker_dialog, container, false);
//        //callBackActivity = new Sign_up_page();
//        quitButton = root.findViewById(R.id.quitButton);
//        skipButton = root.findViewById(R.id.skipButton);
//        galleryButton = root.findViewById(R.id.galleryButton);
//        cameraButton = root.findViewById(R.id.cameraButton);
//        quitButton.setOnClickListener(this);
//        skipButton.setOnClickListener(this);
//        galleryButton.setOnClickListener(this);
//        cameraButton.setOnClickListener(this);
//
//        // Inflate the layout for this fragment
//        return root;
//    }
//
//
//    @Override
//    public void onAttach(@NonNull Context context) {
//        super.onAttach(context);
//        try {
//            someEventListener = (onSomeEventListener) context;
//            //close fragment
//        } catch (ClassCastException e) {
//            throw new ClassCastException(context.toString() + " must implement onSomeEventListener");
//        }
//    }
//
//    @Override
//    public void onClick(View view) {
//
//        if (view == cameraButton) {
//            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//            startActivityForResult(intent, 1);
//        }
//
//
//        if (view == quitButton || view == skipButton) {
//            //close fragment
//            requireActivity().getSupportFragmentManager().beginTransaction().remove(User_photo_dialog_fragment.this).commit();
//
//        }
//    }
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == 1888 && resultCode == Activity.RESULT_OK) {
//            Bitmap photo = (Bitmap) data.getExtras().get("data");
//            someEventListener.someEvent(photo.toString());
//            requireActivity().getSupportFragmentManager().beginTransaction().remove(User_photo_dialog_fragment.this).commit();
//        }
//
//
//    }
//
//
//
//}