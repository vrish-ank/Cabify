package com.example.cabifyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class Home extends AppCompatActivity {
    Button bt_logout,bt_addrequest,bt_searchrequest,bt_myreq,bt_booking;
    FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        bt_logout = findViewById(R.id.bt_logout);
        bt_addrequest = findViewById(R.id.bt_addrequest);
        bt_searchrequest = findViewById(R.id.bt_searchrequest);
        bt_myreq = findViewById(R.id.bt_myreq);
        bt_booking=findViewById(R.id.bt_booking);
        bt_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent inToMain = new Intent(Home.this,MainActivity.class);
                startActivity(inToMain);
            }
        });

        bt_addrequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inToPost = new Intent(Home.this,cabshare_request.class);
                startActivity(inToPost);
            }
        });

        bt_searchrequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inToSearch = new Intent(Home.this,cabshare_details.class);
                startActivity(inToSearch);
            }
        });

        bt_myreq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inTomy = new Intent(Home.this,mycabshare_details.class);
                startActivity(inTomy);
            }
        });

        bt_booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inTomyb = new Intent(Home.this,mybooking_details.class);
                startActivity(inTomyb);
            }
        });
    }
}