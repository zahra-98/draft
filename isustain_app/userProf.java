package com.example.isustain_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class userProf extends AppCompatActivity {
    ImageView personal , points , rate , logout;
    TextView email,pointdisplay;
    Toolbar toolbar;
    CircleImageView img;
    DatabaseReference databaseReference,pointRef;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        email = findViewById(R.id.email);
        personal = findViewById(R.id.info_for);
        rate = findViewById(R.id.rate_for);
        logout = findViewById(R.id.logoutfor);
        toolbar = findViewById(R.id.app_bar);
        img = findViewById(R.id.profile_image_t);
        pointdisplay = findViewById(R.id.points);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() { //navigation to go back to the homepage
            @Override
            public void onClick(View view) { onBackPressed();}
        });
        //displaying current user name/email in profile card
        auth = FirebaseAuth.getInstance();
        String id = auth.getCurrentUser().getUid();

        databaseReference = FirebaseDatabase.getInstance().getReference("User");
        pointRef = FirebaseDatabase.getInstance().getReference("points").child(id);

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String emaill = Objects.requireNonNull(snapshot.child(id).child("emailAdd").getValue()).toString();
                email.setText(emaill);

                if(snapshot.child(id).child("imageUri").exists()){
                    String imgg = snapshot.child(id).child("imageUri").getValue(String.class);
                    Glide.with(img.getContext()).load(imgg).into(img);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity( new Intent(getApplicationContext(),user_login.class));
            }
        });

        personal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),personalInfo.class));
            }
        });


        pointRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists()){
                    int getPoints = snapshot.child("user_points").getValue(Integer.class);
                    pointdisplay.setText("Your current points: " + getPoints );
                }else{
                    pointdisplay.setText("no current points");
                }
//
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),rate_exp.class));
            }
        });

    }
}