package com.example.isustain_app;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.isustain_app.fragments.ProductMain;

public class products_homepage extends AppCompatActivity {
    Fragment homeFragment;
    Toolbar toolbar;



    @Override
    protected  void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.products_homepage);

        homeFragment = new ProductMain();
        loadFragment(homeFragment);

        toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() { //navigation to go back to the homepage
            @Override
            public void onClick(View view) { onBackPressed();}
        });

    }

    private void loadFragment(Fragment homeFragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.home_container,homeFragment);
        transaction.commit();
    }


}
