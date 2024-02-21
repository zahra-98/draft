package com.example.isustain_app.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.isustain_app.R;
import com.example.isustain_app.personalInfo;
import com.example.isustain_app.view_cart;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import HelperClasses.HelperClasses.NewProdAdapter;
import HelperClasses.HelperClasses.NewProdModel;
import HelperClasses.HelperClasses.PopularProductAdapter;
import HelperClasses.HelperClasses.PopularProdModel;


public class ProductMain extends Fragment {

    RecyclerView newProdrecycler , popularRecyclerview;

    Button shopBag;

    //New Products
    NewProdAdapter newProdAdapter;
    List<NewProdModel> newProdModels;

    //popular products
    PopularProductAdapter popularProductAdapter;
    List<PopularProdModel>popularProdModelList;


    FirebaseFirestore store;


    public ProductMain() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root =  inflater.inflate(R.layout.fragment_product_main, container, false);
        //new product recycler

        newProdrecycler = root.findViewById(R.id.new_product_rec);
        popularRecyclerview = root.findViewById(R.id.popular_rec);
        shopBag = root.findViewById(R.id.shoBag_btn);

        store = FirebaseFirestore.getInstance();


        //image slider

        ImageSlider imageSlider = root.findViewById(R.id.image_slider);
        List<SlideModel> slideModels = new ArrayList<>();

        slideModels.add(new SlideModel(R.drawable.banner_one, ScaleTypes.CENTER_CROP));
        slideModels.add(new SlideModel(R.drawable.banner_two, ScaleTypes.CENTER_CROP));
        slideModels.add(new SlideModel(R.drawable.banner_three, ScaleTypes.CENTER_CROP));

        imageSlider.setImageList(slideModels);

        //New products
        newProdrecycler.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
        newProdModels = new ArrayList<>();
        newProdAdapter = new NewProdAdapter(getContext(),newProdModels);
        newProdrecycler.setAdapter(newProdAdapter);

        store.collection("NewProducts")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            for (QueryDocumentSnapshot document : task.getResult()){
                                NewProdModel newProdModel = document.toObject(NewProdModel.class);
                                newProdModels.add(newProdModel);
                                newProdAdapter.notifyDataSetChanged();


                            }

                        }else{
                            Toast.makeText(getActivity(),""+task.getException(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        //Popular Products

        popularRecyclerview.setLayoutManager(new GridLayoutManager(getActivity(),2));
        popularProdModelList = new ArrayList<>();
        popularProductAdapter = new PopularProductAdapter(getContext(),popularProdModelList);
        popularRecyclerview.setAdapter(popularProductAdapter);

        store.collection("AllProducts")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            for (QueryDocumentSnapshot document : task.getResult()){
                                 PopularProdModel popularProdModel = document.toObject(PopularProdModel.class);
                                popularProdModelList.add(popularProdModel);
                                popularProductAdapter.notifyDataSetChanged();


                            }

                        }else{
                            Toast.makeText(getActivity(),""+task.getException(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });


        shopBag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity = (AppCompatActivity) getActivity();
                activity.startActivity(new Intent((getActivity()), view_cart.class));

            }
        });


        return root;

    }
}