package com.example.isustain_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private Button button; //start button
   // private ProgressBar progressBar1; //progressor to login page
    //ProgressBar progressBar2 =(ProgressBar) findViewById(R.id.progressBar2);

//    ProgressBar progressBar2;
//    int counter = 0;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);


        //Button
//        button = (Button) findViewById(R.id.button); //get started button on the welcome page goes to login page
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                openload();
//            }
//        });


    }
//    public void openload(){ //BUTTON
//        Intent intent = new Intent(this, load.class);
//        startActivity(intent);
//
//    }



}