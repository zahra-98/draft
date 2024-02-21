package com.example.isustain_app;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.isustain_app.models.Products;

import java.util.ArrayList;
import java.util.List;

import HelperClasses.HelperClasses.ProductAdapter;

public class prods extends AppCompatActivity {
    RecyclerView prodItemRecycler;
    ProductAdapter productAdapter;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.products_main_page);

        toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() { //navigation to go back to the homepage
            @Override
            public void onClick(View view) { onBackPressed();}
        });

        List<Products> productsList = new ArrayList<>();
        productsList.add(new Products(1, "Eco Flask","Hydro Flask","RM 89.90", R.drawable.prod_one_sn));
        productsList.add(new Products(2,"Eco Tote Bag","ECO BAGS","RM 37",R.drawable.prodtwo));
        productsList.add(new Products(3,"Counter Compost Bin","Chef'n","RM 133",R.drawable.prodthree));
        productsList.add(new Products(4,"Bamboo Toothbrush","Brush With Bamboo","RM 40",R.drawable.prodfour));
        productsList.add(new Products(5,"Reusable Snack Bags","Stasher","RM 80",R.drawable.prodfive));

        setProdItemRecycler(productsList);
    }

    private void setProdItemRecycler(List<Products> productsList) {
        prodItemRecycler = findViewById(R.id.product_recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL,false);
        prodItemRecycler.setLayoutManager(layoutManager);
        productAdapter = new ProductAdapter(this,productsList);
        prodItemRecycler.setAdapter(productAdapter);

    }
}
