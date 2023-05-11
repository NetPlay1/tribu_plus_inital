package com.example.tribu_inital.fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.tribu_inital.R;

import com.google.android.material.bottomnavigation.BottomNavigationView;


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
        View view = inflater.inflate(R.layout.fragment_tool_bar, container, false);
        bottomNavigation = view.findViewById(R.id.buttom_tool_bar);

        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace
                (R.id.fragment_container, new home_fragment()).commit();


        bottomNavigation.setOnItemSelectedListener(item -> {
            fragmentTransaction.addToBackStack(null);
            FragmentTransaction fragmentTransaction1 = fragmentManager.beginTransaction();

            switch (item.getItemId())
            {
                case R.id.explore_page_icon:{
                    fragmentTransaction1.replace
                            (R.id.fragment_container, new explore_page_fragment()).commit();
                    return true;
                }
                case R.id.chat_page_icon:{
                    fragmentTransaction1.replace
                            (R.id.fragment_container, new chat_page_fragment()).commit();
                    return true;
                }
                case R.id.Home_page_icon:{
                    fragmentTransaction1.replace
                            (R.id.fragment_container, new home_fragment()).commit();
                    return true;
                }
            }

            return false;
        });

        return view;
    }


}