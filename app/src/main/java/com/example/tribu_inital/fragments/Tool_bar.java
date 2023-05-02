package com.example.tribu_inital.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.Toast;

import com.example.tribu_inital.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

import java.util.Objects;
import java.util.Vector;

public class Tool_bar extends Fragment {

    BottomNavigationView bottomNavigation;


    public Tool_bar() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_tool_bar, container, false);
        bottomNavigation = view.findViewById(R.id.buttom_tool_bar);

        FragmentManager fragmentManager=getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.content, new home_fragment()).commit();


        if(savedInstanceState == null){
            FragmentTransaction ft = getChildFragmentManager().beginTransaction();
            ft.add(R.id.fragment_container, new home_fragment()).commit();
        }


        bottomNavigation.setOnItemSelectedListener(item -> {

            switch (item.getItemId())
            {
                // Todo: add all the page fragments

                case R.id.explore_page_icon:{

                }
                case R.id.chat_page_icon:{

                }
                case R.id.Home_page_icon:{

                }
            }


            return true;
        });

        // Inflate the layout for this fragment

        return view;
    }


}