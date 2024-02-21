package com.example.isustain_app.paymentSystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.isustain_app.ProductDetails;
import com.example.isustain_app.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddAddress extends AppCompatActivity {

    EditText name, address,city,postalCode,phoneNumber;
    Toolbar toolbar;
    Button addAddress;

    FirebaseFirestore firestore;
    FirebaseAuth auth;
    DatabaseReference firebaseDatabase;
    String NAME, PHONE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);

        toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() { //navigation to go back to the homepage
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        String id = auth.getCurrentUser().getUid();
        firebaseDatabase = FirebaseDatabase.getInstance().getReference("User").child(id);

        firebaseDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                NAME = snapshot.child("fullName").getValue().toString();
                PHONE = snapshot.child("phoneNum").getValue().toString();
                name.setText(NAME);
                phoneNumber.setText(PHONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        name = findViewById(R.id.ad_name);
        address = findViewById(R.id.ad_address);
        city = findViewById(R.id.ad_city);
        postalCode= findViewById(R.id.ad_code);
        phoneNumber = findViewById(R.id.ad_phone);

        addAddress = findViewById(R.id.ad_add_address);




        addAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                String userName = name.getText().toString();
                String userCity = city.getText().toString();
                String userAddress = address.getText().toString();
                String userCode = postalCode.getText().toString();
                String userPhone = phoneNumber.getText().toString();

                String final_address = "";
                if (!userName.isEmpty()){
                    final_address+=userName;
                }
                if (!userCity.isEmpty()){
                    final_address+=userCity;
                }
                if (!userAddress.isEmpty()){
                    final_address+=userAddress;
                }
                if (!userCode.isEmpty()){
                    final_address+=userCode;
                }
                if (!userPhone.isEmpty()){
                    final_address+=userPhone;
                }

                if(!userName.isEmpty() && !userCity.isEmpty() && !userAddress.isEmpty() && !userCode.isEmpty() && !userPhone.isEmpty() ){
                    Map<String, String> map = new HashMap<>();
                    map.put("userAddress",final_address);

                    firestore.collection("CurrentUser").document(auth.getCurrentUser().getUid())
                            .collection("Address").add(map).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentReference> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(AddAddress.this,"Address Added",Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(AddAddress.this, AddressActivity.class));
                                finish();


                            }

                        }
                    });


                }else{
                    Toast.makeText(AddAddress.this,"Kindly Fill All Fields",Toast.LENGTH_SHORT).show();


                }





            }
        });
    }
}
