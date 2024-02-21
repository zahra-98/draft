package com.example.isustain_app;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import de.hdodenhof.circleimageview.CircleImageView;

import com.bumptech.glide.Glide;
import com.github.drjacky.imagepicker.ImagePicker;

//import com.google.android.gms.cast.framework.media.ImagePicker;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Objects;

public class personalInfo extends AppCompatActivity {

    CircleImageView profileimg;
    StorageReference storageReference;
    DatabaseReference databaseReference;
    ProgressBar progressBar;
    Button update_info;
    Toolbar toolbar;
    EditText name, email,phonenum;
    FloatingActionButton upload_pic;
    private Uri imageUri = null;

    FirebaseAuth auth;

    //Global variables to hold user data inside this activity
    String _NAME, _EMAIL, _PHONE , _IMAGE;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_info);

        auth = FirebaseAuth.getInstance();
        update_info = findViewById(R.id.update);
        profileimg = findViewById(R.id.icon_image);
        upload_pic = findViewById(R.id.upload_image);
        name =  findViewById(R.id.user_name);
        email =  findViewById(R.id.user_email);
        phonenum =  findViewById(R.id.user_phone);

        //upload_pic.setOnClickListener(this);
        storageReference = FirebaseStorage.getInstance().getReference("user files");
        databaseReference = FirebaseDatabase.getInstance().getReference("User");
        String id = auth.getCurrentUser().getUid();

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.child(id).child("imageUri").exists()) {
                    _IMAGE = snapshot.child(id).child("imageUri").getValue(String.class);
                    Glide.with(profileimg.getContext()).load(_IMAGE).into(profileimg);
                }
                    _NAME = Objects.requireNonNull(snapshot.child(id).child("fullName").getValue()).toString();
                    name.setText(_NAME);
                    name.setTextSize(16);
                    //wel.setTextSize(16);

                    _PHONE = Objects.requireNonNull(snapshot.child(id).child("phoneNum").getValue()).toString();
                    phonenum.setText(_PHONE);
                    phonenum.setTextSize(16);

                    _EMAIL = Objects.requireNonNull(snapshot.child(id).child("emailAdd").getValue()).toString();
                    email.setText(_EMAIL);
                    email.setTextSize(16);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        upload_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePicker.Companion.with(personalInfo.this)
                       .maxResultSize(1080,1080)
                        .start(20);

            }
        });

        update_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                updateProf();
            }
        });
       // showAllUserData();


    }

    private void showAllUserData(){
        Intent intent = getIntent();
        _NAME = intent.getStringExtra("FullName");
        _EMAIL = intent.getStringExtra("EmailAdd");
        _PHONE = intent.getStringExtra("PhoneNum");

        name.setText(_NAME);
        email.setText(_EMAIL);
        phonenum.setText(_PHONE);



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

//        if (requestCode ==12 && resultCode==RESULT_OK && data!=null && data.getData()!=null){
//            update_info.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    uploadFiletoStorage(data.getData());
//
//                }
//            });
//        }
//        else{

        if (requestCode ==20 && resultCode==RESULT_OK && data!=null && data.getData()!=null){
            imageUri = data.getData();
            profileimg.setImageURI(imageUri);
    }

}


    public void updateProf() {

        auth = FirebaseAuth.getInstance();
        String id = auth.getCurrentUser().getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference("User")
                .child(id);

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                final StorageReference filepath = storageReference.child("images").child(imageUri.getLastPathSegment());
                filepath.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        if (imageUri == null) {
                            Toast.makeText(getApplicationContext(), "Please upload an image", Toast.LENGTH_LONG).show();
                        } else {
                            filepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String url = uri.toString();
                                    if(!snapshot.child("imageUri").exists()){
                                        databaseReference.child(id).child("imageUri").setValue(url).toString();
                                    }
                                    else if(!_IMAGE.equals(profileimg.getContext().toString())){
                                        databaseReference.child(id).child("imageUri").setValue(url).toString();
                                    }

                                    else{
                                        databaseReference.child("imageUri").setValue(url).toString();
                                    }
//                                    if(!snap)
//                                    //databaseReference.child("imageUri").setValue(url).toString();
//                                    if(!_IMAGE.equals(profileimg.getContext().toString())){
//
//                                        databaseReference.child(id).child("imageUri").setValue(url).toString();
//                                        // return true;
//                                    }
//                                    else{
//                                        databaseReference.child("imageUri").setValue(url).toString();
//                                    }
                                    Toast.makeText(personalInfo.this, "Profile updated successfully", Toast.LENGTH_LONG).show();
                                }
                            });



                        }


                    }

                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        if (isNameChanged() || isEmailChanged() || isPhoneChange() ) {
            Toast.makeText(this, "Data successfully updated", Toast.LENGTH_LONG).show();


        }
    }

    private boolean isNameChanged() {
        //storageReference = FirebaseStorage.getInstance().getReference("user files");
        databaseReference = FirebaseDatabase.getInstance().getReference("User");
        String id = auth.getCurrentUser().getUid();
        if (!_NAME.equals(name.getText().toString())){

            databaseReference.child(id).child("emailAdd").setValue(name.getText().toString());
            _NAME = name.getText().toString();
            return true;



        }else{
            return false;

        }
        }

    private boolean isEmailChanged(){
        databaseReference = FirebaseDatabase.getInstance().getReference("User");
        String id = auth.getCurrentUser().getUid();
        if (!_EMAIL.equals(email.getText().toString())){

            databaseReference.child(id).child("fullName").setValue(email.getText().toString());
            _EMAIL = email.getText().toString();
            return true;



        }else{
            return false;

        }


    }

    private boolean isPhoneChange(){
        databaseReference = FirebaseDatabase.getInstance().getReference("User");
        String id = auth.getCurrentUser().getUid();
        if(!_PHONE.equals(phonenum.getText().toString())){
            databaseReference.child(id).child("phoneNum").setValue(phonenum.getText().toString());
            _PHONE = phonenum.getText().toString();
            return true;
        }else{
            return false;
        }

    }

    private boolean isImageChanges(){
        String id = auth.getCurrentUser().getUid();
        if (!_IMAGE.equals(profileimg.getContext().toString())){

            databaseReference.child(id).child("imageUri").setValue(imageUri).toString();
            return true;
        }else {
            return false;
        }


    }
}