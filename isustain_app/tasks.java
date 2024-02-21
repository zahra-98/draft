package com.example.isustain_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class tasks extends AppCompatActivity {
    ImageView img;
    CircleImageView icon;
    TextView task;
    DatabaseReference databaseReference;
    ImageView BacktoHome;
    RelativeLayout task1,task2,task3,task4,task5,task6,task7,task8;
    SearchView searchView;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);

        //HOOKS

        task1 = findViewById(R.id.task1);
        task2 = findViewById(R.id.task2);
        task4 = findViewById(R.id.task4);
        task5 =findViewById(R.id.task5);
        task8 = findViewById(R.id.task8);
        task3 = findViewById(R.id.task3);
        task6 = findViewById(R.id.task6);
        task7 = findViewById(R.id.task7);
        BacktoHome= findViewById(R.id.back_press);

        BacktoHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tasks.super.onBackPressed();
            }
        });


        //OnClick Listeners for each task
        task1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),brush.class));

            }
        });

        task2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),reuse_bottle.class));

            }
        });
        task4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),farm_direct.class));
            }
        });
        task5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),waste_rec.class));

            }
        });
        task8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),compost_food.class));
            }
        });
        task3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),reuse_bag.class));
            }

        });
        task6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),local_transport.class));
            }

        });
        task7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),e_bills.class));
            }

        });


    }
}