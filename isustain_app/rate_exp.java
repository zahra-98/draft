package com.example.isustain_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

public class rate_exp extends AppCompatActivity {
    Button btnrate;
    RatingBar rateStars;
    float myRating = 0;
    Toolbar toolbarr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_exp);

        btnrate = findViewById(R.id.submit);
        rateStars=findViewById(R.id.ratingBar);
        toolbarr =  findViewById(R.id.app_bar);
        
        setSupportActionBar(toolbarr);
        toolbarr.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        rateStars.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                String message = null;
                myRating =  ratingBar.getRating();
                switch ((int) v){
                    case 1:
                        message = "Oh no! We will work hard";
                        break;
                    case 2:
                          message = "We are always open for suggestions";
                          break;
                    case 3:
                        message = "Appreciate your input";
                        break;
                    case 4:
                        message = "Great! Thank You";
                        break;
                    case 5:
                        message = "Awesome! You are the best";
                        break;

                }
                Toast.makeText(rate_exp.this, "Your rating is: "+ message, Toast.LENGTH_SHORT).show();
            }
        });

        btnrate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(rate_exp.this,String.valueOf(myRating), Toast.LENGTH_SHORT).show();
            }
        });
    }



}