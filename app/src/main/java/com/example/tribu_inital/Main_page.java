package com.example.tribu_inital;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Main_page extends AppCompatActivity {

    TextView userName;

    Intent intent;


    FirebaseUser user;

    @Override

    // Todo : this is just a test need to research more

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content, new Tool_bar());
        fragmentTransaction.replace(R.id.content2, new search_bar_fragment());
        fragmentTransaction.commit();

        userName = findViewById(R.id.userName);


         user = FirebaseAuth.getInstance().getCurrentUser();

         if(savedInstanceState == null){
             Fragment bottom_tool_bar = new Tool_bar();
             FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
             ft.add(0, new search_bar_fragment());
             ft.add(0, bottom_tool_bar).commit();

         }

        assert user != null;
        userName.setText(user.getEmail());

    }
}