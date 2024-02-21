package com.example.isustain_app.paymentSystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.isustain_app.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.paypal.android.sdk.payments.PaymentActivity;

import java.util.ArrayList;
import java.util.List;

import HelperClasses.HelperClasses.NewProdModel;
import HelperClasses.HelperClasses.PopularProdModel;

public class AddressActivity extends AppCompatActivity implements AddressAdapter.SelectedAddress{

    Button add_address , paymentBtn;
    Toolbar toolbar;
    RecyclerView recyclerView;
    private List<AddressModel> addressModelList;
    FirebaseFirestore firestore;
    FirebaseAuth auth;
    private AddressAdapter addressAdapter;
    String mAddress = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);

        toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() { //navigation to go back to the homepage
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        //getting data from details of items

        Object obj = getIntent().getSerializableExtra("item");

        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        recyclerView = findViewById(R.id.rec_add);
        paymentBtn = findViewById(R.id.paymentProceed);

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        addressModelList = new ArrayList<>();
        addressAdapter = new AddressAdapter(addressModelList, getApplicationContext(),this);
        recyclerView.setAdapter(addressAdapter);

        firestore.collection("CurrentUser").document(auth.getCurrentUser().getUid())
                .collection("Address").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    for (DocumentSnapshot doc:task.getResult().getDocuments()){
                        AddressModel addressModel = doc.toObject(AddressModel.class);
                        addressModelList.add(addressModel);
                        addressAdapter.notifyDataSetChanged();
                    }
                }
            }
        });

        paymentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                double amount = 0.0;
                if (obj instanceof NewProdModel){
                    NewProdModel newProdModel = (NewProdModel) obj;
                    amount = newProdModel.getPrice();
                }

                if (obj instanceof PopularProdModel){
                    PopularProdModel popularProdModel = (PopularProdModel) obj;
                    amount = popularProdModel.getPrice();
                }
                Intent intent = new Intent(AddressActivity.this,Payment.class);
                intent.putExtra("amount",amount);
                startActivity(intent);
            }
        });

        add_address = findViewById(R.id.add_address_btn);
        add_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AddressActivity.this,AddAddress.class));



            }
        });
    }

    @Override
    public void setAddress(String address) {

        mAddress = address;
    }
}