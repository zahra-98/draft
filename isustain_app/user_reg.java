package com.example.isustain_app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class user_reg extends AppCompatActivity implements View.OnClickListener{

    private FirebaseAuth mAuth;
    EditText mFullName, mEmail, mPhone, mPassword, mRetypePass;
    Button mRegisterBtn;
    TextView mLoginBtn;
    ProgressBar progressReg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register2);


        mAuth = FirebaseAuth.getInstance();

        mFullName = findViewById(R.id.fullname);
        mEmail    = findViewById(R.id.Emailadd);
        mPhone    = findViewById(R.id.phonenum);
        mPassword = findViewById(R.id.passwordreg);
        mRetypePass = findViewById(R.id.retypepass);

        mRegisterBtn = findViewById(R.id.signup);
        mRegisterBtn.setOnClickListener(this);

        mLoginBtn = findViewById(R.id.signinReg);
        progressReg = findViewById(R.id.progressReg);




    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.signup:
                registerUser();
                break;


        }
    }

    private void registerUser() {
        String email = mEmail.getText().toString().trim();
        String fullname = mFullName.getText().toString().trim();
        String password = mPassword.getText().toString().trim();
        String phone = mPhone.getText().toString().trim();

        if(fullname.isEmpty()){
            mFullName.setError("Name is required");
            mFullName.requestFocus();
            return;
        }

        if(email.isEmpty()){
            mEmail.setError("Email is required");
            mEmail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            mEmail.setError("Please Provide Valid Email");
            mEmail.requestFocus();
            return;
        }

        if(phone.isEmpty()){
            mPhone.setError("Phone Number is required");
            mPhone.requestFocus();
            return;
        }

        if(password.isEmpty()){
            mPassword.setError("Password is required");
            mPassword.requestFocus();
            return;
        }

        if(password.length()<6){
            mPassword.setError("Password length should be atleast 6 characters");
            mPassword.requestFocus();
            return;
        }

        progressReg.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Users user = new Users(fullname, email, phone);
                            FirebaseDatabase.getInstance().getReference("User")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(user_reg.this, "Registeration is Successfull", Toast.LENGTH_LONG).show();
                                        startActivity(new Intent(getApplicationContext(),user_login.class));
                                        progressReg.setVisibility(View.GONE);




                                    }
                                }
                            });


                        } else {
                            Toast.makeText(user_reg.this, "Registeration Failed! Please Try Again", Toast.LENGTH_LONG).show();
                            progressReg.setVisibility(View.GONE);
                        }
                    }


                });

        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),user_login.class));
            }
        });


    }
}