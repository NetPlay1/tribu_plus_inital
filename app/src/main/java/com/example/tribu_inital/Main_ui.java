package com.example.tribu_inital;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tribu_inital.fragments.Tool_bar;
import com.example.tribu_inital.fragments.search_bar_fragment;
import com.example.tribu_inital.start.Start_page;
import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.StorageReference;

public class Main_ui extends AppCompatActivity implements View.OnClickListener {

    TextView userName;

    Intent intent;
    FirebaseUser user;


    @Override

    // Todo : this is just a test need to research more

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_ui);


        try {
            user = FirebaseAuth.getInstance().getCurrentUser();

            if(user == null) throw new Exception("no user connected");

            FragmentManager fragmentManager=getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();



            fragmentTransaction.replace(R.id.content2, new search_bar_fragment());
            fragmentTransaction.replace(R.id.content, new Tool_bar());
            fragmentTransaction.commit();

            if(savedInstanceState == null){
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.add(0, new Tool_bar());
                ft.add(0, new search_bar_fragment()).commit();

            }

        }
        catch (Exception e){
            Toast.makeText(this,""+e.getMessage(),Toast.LENGTH_SHORT).show();

            intent = new Intent(Main_ui.this, Start_page.class);
            startActivity(intent);
            finish();
            
        }






    }


    @Override
    public void onClick(View view) {

    }

}