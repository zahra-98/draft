
package com.example.isustain_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

            // ZAHRAH FAIROZ - TP052629
            // START DATE: 15 AUG 2021
            // END DATE: 1 OCT 2021
            // LAST UPDATE: 10 OCT 2021

public class introductory extends AppCompatActivity {
    ImageView logo;
    LottieAnimationView lottieAnimationView;
    private onBoardingAdapter onBoardingAdapter;
    private LinearLayout linearLayoutonBoarding;
    private MaterialButton button;
    ViewPager2 onBoardingViewpager2;
    Animation anim;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.introductory_activity);

        logo = findViewById(R.id.logo);
        lottieAnimationView = findViewById(R.id.lottie);

        linearLayoutonBoarding = findViewById(R.id.indicators);
        button = findViewById(R.id.buttonOnboarding);
        setupOnboardingItems();

        onBoardingViewpager2 = findViewById(R.id.onboardingViewPager);
        onBoardingViewpager2.setAdapter(onBoardingAdapter);

        setupOnboardingIndicators();
        setCurrentOnboardingIndicator(0);

        onBoardingViewpager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                setCurrentOnboardingIndicator(position);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onBoardingViewpager2.getCurrentItem() + 1 < onBoardingAdapter.getItemCount()){
                    onBoardingViewpager2.setCurrentItem(onBoardingViewpager2.getCurrentItem()+1);

                }else{
                    startActivity(new Intent(getApplicationContext(),user_login.class));

                }
            }
        });

        anim = AnimationUtils.loadAnimation(this,R.anim.o_b_anim);
        onBoardingViewpager2.startAnimation(anim);
        //lottieAnimationView.playAnimation();

        logo.animate().translationY(-1600).setDuration(1000).setStartDelay(4000);
        lottieAnimationView.playAnimation();
        lottieAnimationView.animate().translationY(1400).setDuration(1000).setStartDelay(4000);


    }

    private void setupOnboardingItems(){
        List<OnboardingItem> onboardingItemList = new ArrayList<>();
        OnboardingItem habitsItem = new OnboardingItem();
        habitsItem.setTitle("GREEN HABITS");
        habitsItem.setDescrip("Develop sustainable and eco-friendly\n" +
                "        habits to help protect our Planet Earth");
        habitsItem.setImage(R.drawable.habits);

        OnboardingItem earn_pointsItem = new OnboardingItem();
        earn_pointsItem.setTitle("EARN POINT");
        earn_pointsItem.setDescrip( " Get points after each task is completed\n" +
                "        successfully. The earth is rewarding you");
        earn_pointsItem.setImage(R.drawable.earn_points);

        OnboardingItem ecoShopItem = new OnboardingItem();
        ecoShopItem.setTitle("ECO SHOP");
        ecoShopItem.setDescrip(" Purchase top quality products that reduces waste\n" +
                "        and gets you to live a sustainable life easily");
        ecoShopItem.setImage(R.drawable.eco_shop);

        onboardingItemList.add(habitsItem);
        onboardingItemList.add(earn_pointsItem);
        onboardingItemList.add(ecoShopItem);

        onBoardingAdapter = new onBoardingAdapter(onboardingItemList);

    }
    private void setupOnboardingIndicators(){
        ImageView[] indicators = new ImageView[onBoardingAdapter.getItemCount()];
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMargins(8,0,8,0);
        for (int i = 0; i< indicators.length;i++){
            indicators[i] = new ImageView(getApplicationContext());
            indicators[i].setImageDrawable(ContextCompat.getDrawable(
                    getApplicationContext(),
                    R.drawable.indicator_inactive
            ));
            indicators[i].setLayoutParams(layoutParams);
            linearLayoutonBoarding.addView(indicators[i]);
        }
    }
    private void setCurrentOnboardingIndicator(int index){
        int childCount = linearLayoutonBoarding.getChildCount();
        for(int i =0; i <childCount;i++){
            ImageView imageView = (ImageView) linearLayoutonBoarding.getChildAt(i);
            if(i==index){
                imageView.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.indicator_active));
            }else{
                imageView.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.indicator_inactive));
            }
        }
        if(index == onBoardingAdapter.getItemCount()-1){
            button.setText("Start");
        }else{
            button.setText("Next");
        }

    }
}
