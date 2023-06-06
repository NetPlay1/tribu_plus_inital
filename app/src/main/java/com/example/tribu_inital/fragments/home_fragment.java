package com.example.tribu_inital.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.tribu_inital.Activity_item;
import com.example.tribu_inital.Activity_list_adapter;
import com.example.tribu_inital.Add_Activity_page_fragment;
import com.example.tribu_inital.Main_ui;
import com.example.tribu_inital.R;
import com.example.tribu_inital.User;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.dialog.MaterialDialogs;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;


public class home_fragment extends Fragment {


    FloatingActionButton addButton;

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

        List<Activity_item> items = new ArrayList<Activity_item>();
        items.add(new Activity_item("john's bakery",R.drawable.baseline_add_circle_24));
        items.add(new Activity_item("Bob's shop",R.drawable.baseline_add_circle_24));
        items.add(new Activity_item("cabbage Was here",R.drawable.baseline_add_circle_24));

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new Activity_list_adapter(getContext(),items));

            addButton.setOnClickListener(view1 -> showDialog());

            return view;
    }

    public void showDialog() {
        FragmentManager fragmentManager = getParentFragmentManager();
        Add_Activity_page_fragment newFragment = new Add_Activity_page_fragment();
        newFragment.show(fragmentManager, "dialog");

    }
}