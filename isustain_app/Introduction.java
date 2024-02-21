package com.example.isustain_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class Introduction extends AppCompatActivity {

    private ViewPager screenPager;
    Adapter Adapterr;
    LinearLayout sliderdots;
    Button nextbtn , btnstart;
    int posistion = 0;
    Animation btnanim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduction);




        //indicators n button
        sliderdots = findViewById(R.id.SliderDots);
        nextbtn = findViewById(R.id.buttonNext);
        btnstart = findViewById(R.id.start_btn);
        btnanim = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.button_anim);





        //fill list screen
        List<ScreenItem> mList = new ArrayList<>();
        mList.add(new ScreenItem("REDUCE", "Everyday waste and keep the earth clean", R.drawable.reduce));
        mList.add(new ScreenItem("REUSE", "Products and sustain them for a long time", R.drawable.capture));
        mList.add(new ScreenItem("RECYCLE", "Take action everyday and build sustainable habits", R.drawable.recycle));

        //set ViewPager
        screenPager = findViewById(R.id.screen_viewpager);
        Adapterr = new Adapter(this,mList);
        screenPager.setAdapter(Adapterr);

        //setup tablayout
        //tabIndicator.setupWithViewPager();

        new sliderDots().dots(Adapterr, this,sliderdots,screenPager);

        //next button on click listener
        nextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                posistion = screenPager.getCurrentItem();
                if (posistion < mList.size()){
                    posistion++;
                    screenPager.getCurrentItem();


                }
                if (posistion==mList.size()-1) { //reaching the end screen
                    // TODO: show the GETSTARTED Button and hide the slider dots and next button
                    loadLastScreen();
                }



            }
        });

        //slider dots change listener
//        sliderdots.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
//            @Override
//            public void onLayoutChange(View view, int i, int i1, int i2, int i3, int i4, int i5, int i6, int i7) {
//                 if (view.get)
//            }
//        });

        //get started button click listener
        btnstart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),login.class));
                //savePrefsData();
                finish();

            }
        });


    }

//    private boolean restorePrefData(){
//        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefs",MODE_PRIVATE);
//        Boolean isIntroDone = pref.getBoolean("isDone", false);
//        return isIntroDone;
//    }
//
//    //User experience onboarding once
//    private void savePrefsData(){
//        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefs", MODE_PRIVATE);
//        SharedPreferences.Editor editor = pref.edit();
//        editor.putBoolean("isDone",true);
//        editor.commit();
//
//    }

    //show get started button and hide the sliderdots and the next button
    private void loadLastScreen(){
        nextbtn.setVisibility(View.INVISIBLE);
        btnstart.setVisibility(View.VISIBLE);
        sliderdots.setVisibility(View.INVISIBLE);
        //TODO: creating button animation
        btnstart.setAnimation(btnanim);

    }
}