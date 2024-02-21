package com.example.isustain_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.isustain_app.models.points;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class brush extends AppCompatActivity {
    Button buzz_btn_1;
    DatabaseReference databaseReference,refpoints,refpoints2;
    FirebaseAuth auth;
    Toolbar toolbar;
    Dialog dialog;

    private int point = 3,total;
    String _POINTS,name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brush);

        buzz_btn_1 = findViewById(R.id.buzz_btn_brush);
        dialog = new Dialog(this);


        buzz_btn_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getPointss();
                updatePoints();
            }
        });

        toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() { //navigation to go back to the homepage
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


    }

    private void getPointss() {

        //method to save user points into the firebase database

        dialog.setContentView(R.layout.congratz_layout_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        ImageView imageViewClose = dialog.findViewById(R.id.img_close);
        Button btnOk = dialog.findViewById(R.id.buttonOK);

        imageViewClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();

            }
        });

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();

            }
        });

        dialog.show();

        auth = FirebaseAuth.getInstance();
        String id = auth.getCurrentUser().getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference("User").child(id);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                name = snapshot.child("fullName").getValue().toString();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        refpoints = FirebaseDatabase.getInstance().getReference("points").child(id);
        String key = refpoints.getKey();

        Intent i = new Intent(getApplicationContext(),reuse_bottle.class);
        i.putExtra("KEY",key).toString();

        refpoints.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(!snapshot.exists()) {

                    points p = new points(point, name, id);//setting database nodes

                    refpoints.setValue(p); } //set point value

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void updatePoints(){
        //updating user points in firebase
        auth = FirebaseAuth.getInstance();
        String id = auth.getCurrentUser().getUid();

        refpoints = FirebaseDatabase.getInstance().getReference("points").child(id);

        refpoints.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {

                    //getting existing user points and adding new user points

                    int getPoints = snapshot.child("user_points").getValue(Integer.class);
                    int p = point + getPoints;

                    refpoints.child("user_points").setValue(p);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });





    }
    }


