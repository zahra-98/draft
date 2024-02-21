package com.example.isustain_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.isustain_app.paymentSystem.AddressActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import HelperClasses.HelperClasses.MyCartAdapter;
import HelperClasses.HelperClasses.MyCartModel;

public class view_cart extends AppCompatActivity {

    int overAllTotalAmount;
    TextView total;
    Toolbar toolbar;
    RecyclerView recyclerView;
    List<MyCartModel> cartModels;
    Button checkout;
    MyCartAdapter cartAdapter;
    private FirebaseAuth auth;
    private FirebaseFirestore firestore;
    static int totalbill;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_cart);

        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() { //navigation to go back to the homepage
            @Override
            public void onClick(View view) { onBackPressed();}
        });




        LocalBroadcastManager.getInstance(this)
                .registerReceiver(mMessageReciever,new IntentFilter("MyTotalAmount"));

        checkout = findViewById(R.id.check_out);

        total = findViewById(R.id.textView12);
        recyclerView = findViewById(R.id.cart_rec);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        cartModels = new ArrayList<>();
        cartAdapter = new MyCartAdapter(this,cartModels);
        recyclerView.setAdapter(cartAdapter);

        firestore.collection("AddToCart").document(auth.getCurrentUser().getUid())
                .collection("User").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    for (DocumentSnapshot doc:task.getResult().getDocuments()){
                        MyCartModel myCartModel = doc.toObject(MyCartModel.class);
                        cartModels.add(myCartModel);
                        cartAdapter.notifyDataSetChanged();
                    }
                }
            }
        });

    }
    public BroadcastReceiver mMessageReciever = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            totalbill = intent.getIntExtra("totalAmount",0);
            total.setText("Total " + "RM" + totalbill);

            checkout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(view_cart.this,AddressActivity.class);
                    intent.putExtra("TOTAL",totalbill);
                    startActivity(intent);
                }
            });


        }
    };
}