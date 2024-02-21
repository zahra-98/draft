package com.example.isustain_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

public class HomePage extends AppCompatActivity {
    TextView log , name;
    DatabaseReference databaseReference;
    FirebaseAuth auth;
    CardView cardView , cardView2 , cardView3,cardView4;
    CircleImageView prof;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        name = findViewById(R.id.txtwelcome);
        prof = findViewById(R.id.prof_img);

        auth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("User");
        String id = auth.getCurrentUser().getUid();

        cardView = findViewById(R.id.profile_card); //user_profile
        cardView2 = findViewById(R.id.tasks_card);  //tasks
        cardView3 = findViewById(R.id.materials_card); //resources
        cardView4 = findViewById(R.id.prod_card); //products


        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String namee = Objects.requireNonNull(snapshot.child(id).child("fullName").getValue()).toString();
                name.setText("Hello " + namee);

                if(snapshot.child(id).child("imageUri").exists()){
                    String imgg = snapshot.child(id).child("imageUri").getValue(String.class);
                    Glide.with(prof.getContext()).load(imgg).into(prof);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.getContext().startActivity(new Intent(view.getContext(),userProf.class));
            }
        });

        cardView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.getContext().startActivity(new Intent(view.getContext(),tasks.class));
            }
        });

        cardView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.getContext().startActivity(new Intent(view.getContext(),resources.class));
            }
        });

        cardView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.getContext().startActivity(new Intent(view.getContext(),products_homepage.class));
            }
        });

//        log = (TextView) findViewById(R.id.logout);
//        log.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                logout();
//            }
//        });

    }





    public void logout(){
        FirebaseAuth.getInstance().signOut(); //user logouts
        Intent intent = new Intent(this,login.class);
        startActivity(intent);

    }




}