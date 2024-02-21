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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class farm_direct extends AppCompatActivity {
    Toolbar toolbar;
    Button buzz_btn;
    Dialog dialog;
    DatabaseReference databaseReference;
    FirebaseAuth auth;

    static int point = 5;
    static int increment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farm_direct);

        toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() { //navigation to go back to the homepage
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        buzz_btn = findViewById(R.id.buzz_btn_farm);
        dialog = new Dialog(this);

        buzz_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getPoints();
            }
        });


    }

    private void getPoints() {
        auth = FirebaseAuth.getInstance();
        String id = auth.getCurrentUser().getUid();

        dialog.setContentView(R.layout.congratz_layout_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        ImageView imageViewClose = dialog.findViewById(R.id.img_close);
        Button btnOk = dialog.findViewById(R.id.buttonOK);

        imageViewClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                //Toast.makeText(farm_direct.this, "Dialog Close",Toast.LENGTH_SHORT).show();
            }
        });

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
               // Toast.makeText(farm_direct.this, "Button OK",Toast.LENGTH_SHORT).show();
            }
        });

        dialog.show();



        databaseReference = FirebaseDatabase.getInstance().getReference("points").child(id);
        Intent i = getIntent();
        String key = i.getStringExtra("KEY");

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){

                    int getPoints = snapshot.child("user_points").getValue(Integer.class);
                    int p = point + getPoints;

                    databaseReference.child("user_points").setValue(p);


//                    String key = snapshot.getKey();
//                    String user_id = snapshot.child(key).child("user_id").getValue().toString();
//
//                    if(id.equals(user_id)){
//                        int take_points = snapshot.child(key).child("user_points").getValue(Integer.class);
//                        total = take_points+2;
//                        databaseReference.child(key).child("user_points").setValue(total);
//                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }}












