package com.example.isustain_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.isustain_app.paymentSystem.AddAddress;
import com.example.isustain_app.paymentSystem.AddressActivity;
import com.example.isustain_app.paymentSystem.Payment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import HelperClasses.HelperClasses.NewProdModel;
import HelperClasses.HelperClasses.PopularProdModel;

public class ProductDetails extends AppCompatActivity {

    ImageView backpress, additems, removeItems;
    ImageView detailedImg;
    TextView rating, name, brandName, description, price, quantity;
    Button addToCart, BuyNow;

    int totalQuantity = 1;
    int totalPrice = 0;


    //New Products
    NewProdModel newProdModel = null;
    //Popular Products
    PopularProdModel popularProdModel;
    FirebaseAuth auth;
    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_details);

        backpress = findViewById(R.id.backpress);
        backpress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProductDetails.super.onBackPressed();
            }
        });

        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        final Object obj = getIntent().getSerializableExtra("detailed");
        if (obj instanceof NewProdModel){
            newProdModel = (NewProdModel) obj;

        }else if (obj instanceof PopularProdModel){
            popularProdModel = (PopularProdModel) obj;
        }

        detailedImg = findViewById(R.id.prodimage);
        quantity = findViewById(R.id.quantity);
        name = findViewById(R.id.prodname);
        brandName = findViewById(R.id.brand_name);
        description = findViewById(R.id.prod_desc);
        price = findViewById(R.id.detail_price);
        rating = findViewById(R.id.prod_rating);
        addToCart = findViewById(R.id.addtocart);
        BuyNow = findViewById(R.id.buy_now);
        additems = findViewById(R.id.add_items);
        removeItems = findViewById(R.id.remove_items);

        //New Products
        if (newProdModel !=null){
            Glide.with(getApplicationContext()).load(newProdModel.getImg_url()).into(detailedImg);
            name.setText(newProdModel.getName());
            rating.setText(newProdModel.getRating());
            brandName.setText(newProdModel.getBrand());
            description.setText(newProdModel.getDescription());
            price.setText(String.valueOf(newProdModel.getPrice()));
            rating.setText(newProdModel.getRating());

            totalPrice = newProdModel.getPrice() * totalQuantity;



        }

        //Popular Products
        if (popularProdModel !=null){
            Glide.with(getApplicationContext()).load(popularProdModel.getImg_url()).into(detailedImg);
            name.setText(popularProdModel.getName());
            rating.setText(popularProdModel.getRating());
            brandName.setText(popularProdModel.getBrand());
            description.setText(popularProdModel.getDescription());
            price.setText(String.valueOf(popularProdModel.getPrice()));
            rating.setText(popularProdModel.getRating());

            totalPrice = popularProdModel.getPrice() * totalQuantity;
        }


        BuyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProductDetails.this, AddressActivity.class);
                if (newProdModel !=null){
                    intent.putExtra("item",newProdModel);
                }
                if (popularProdModel != null){
                    intent.putExtra("item",popularProdModel);

                }
                startActivity(intent);
            }
        });

        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addtocart();
            }
        });

        additems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(totalQuantity<10){
                    totalQuantity++;
                    quantity.setText(String.valueOf(totalQuantity));

                    if(newProdModel !=null){
                        totalPrice = newProdModel.getPrice() * totalQuantity;
                    }

                    if(popularProdModel != null){
                        totalPrice = popularProdModel.getPrice() * totalQuantity;
                    }
                }

            }
        });

        removeItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(totalQuantity>1){
                    totalQuantity--;
                    quantity.setText(String.valueOf(totalQuantity));
                }

            }
        });

    }

    private void addtocart() {
        String saveCurrentTime, saveCurrentDate;
        Calendar calForDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MM/dd/yyyy");
        saveCurrentDate = currentDate.format(calForDate.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm a");
        saveCurrentTime = currentTime.format(calForDate.getTime());

        final HashMap<String,Object> cartMap= new HashMap<>();

        cartMap.put("productName",name.getText().toString());
        cartMap.put("productPrice",price.getText().toString());
        cartMap.put("currentTime",saveCurrentTime);
        cartMap.put("currentDate",saveCurrentDate);
        cartMap.put("totalQuantity",quantity.getText().toString());
        cartMap.put("totalPrice",totalPrice);

        firestore.collection("AddToCart").document(auth.getCurrentUser().getUid())
                .collection("User").add(cartMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                Toast.makeText(ProductDetails.this,"Item Added To Cart",Toast.LENGTH_SHORT).show();
                finish();
            }
        });






    }

}
