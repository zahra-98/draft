package com.example.isustain_app;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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

public class register2 extends AppCompatActivity {
    EditText mFullName, mEmail, mPhone, mPassword, mRetypePass;
    Button mRegisterBtn;
    TextView mLoginBtn;
    FirebaseAuth fAuth;
    ProgressBar progressReg;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register2);
        setVisible(true);

        mFullName = findViewById(R.id.fullname);
        mEmail    = findViewById(R.id.Emailadd);
        mPhone    = findViewById(R.id.phonenum);
        mPassword = findViewById(R.id.passwordreg);
        mRetypePass = findViewById(R.id.retypepass);
        mRegisterBtn = findViewById(R.id.signup);
        mLoginBtn = findViewById(R.id.signinReg);

        fAuth = FirebaseAuth.getInstance();
        progressReg = findViewById(R.id.progressReg);

        //checking if the account exists

        if(fAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(),HomePage.class ));
            finish();

        }

        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();
                String name = mFullName.getText().toString().trim();
                String phoneNum = mPhone.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    mEmail.setError("Oh no, Email is Required");
                    return;

                }

                if(TextUtils.isEmpty(password)){
                    mPassword.setError("Oh no, Password is Required");
                    return;
                }

                if (password.length() < 6){
                    mPassword.setError("Password too short!");
                    return;

                }

                progressReg.setVisibility(View.VISIBLE);

                //registering the user into firebase
                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task){
                        if (task.isSuccessful()){
                            Users user = new Users(name,email,phoneNum);
                            FirebaseDatabase.getInstance().getReference("User").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(register2.this, "YAY! Account have created.", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(getApplicationContext(),HomePage.class ));
                                        progressReg.setVisibility(View.GONE);
                                        startActivity(new Intent(getApplicationContext(), login.class));
                                    }else{
                                        Toast.makeText(register2.this, "Failed To Register, Please Try Again!" +task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                        progressReg.setVisibility(View.GONE);

                                    }


                                }


                            });
                        }
                    }

                });
            }
        });
        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),login.class));
            }
        });
    }
}


