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
import com.google.firebase.auth.FirebaseUser;

public class user_login extends AppCompatActivity implements View.OnClickListener{

    private Button register, loginBtn;
    private EditText mEmail,mPassword;
    private ProgressBar progressBar;
    private FirebaseAuth fAuth;
    private TextView forgotPassLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        register = findViewById(R.id.createAccBtn);
        register.setOnClickListener(this);

        mEmail = findViewById(R.id.EmailAdd);
        mPassword = findViewById(R.id.password);

        progressBar = findViewById(R.id.progressLogin);

        loginBtn = findViewById(R.id.signin);
        loginBtn.setOnClickListener(this);

        fAuth = FirebaseAuth.getInstance();

        forgotPassLink = findViewById(R.id.forgotpass);
        forgotPassLink.setOnClickListener(this);

        //forgotPassLink = findViewById(R.id.forgotpass)


    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.createAccBtn:
                startActivity(new Intent(this, user_reg.class));
                break;

            case R.id.signin:
                userLogin();
                break;

            case R.id.forgotpass:
                startActivity(new Intent(this, forgot_pass.class));
                break;
        }



    }

    private void userLogin(){

        String email = mEmail.getText().toString().trim();
        String pass = mPassword.getText().toString().trim();

        if(email.isEmpty()){
            mEmail.setError("Email is required");
            mEmail.requestFocus();
            return;

        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            mEmail.setError("Please enter a valid email");
            mEmail.requestFocus();
            return;

        }

        if(pass.isEmpty()){
            mPassword.getText().toString().trim();
            mPassword.setError("Password is required");
            mPassword.requestFocus();
            return;
        }

        if(pass.length()<6){
            mPassword.getText().toString().trim();
            mPassword.setError("Password length is short, atleast 6 characters is required");
            mPassword.requestFocus();
            return;

        }

        progressBar.setVisibility(View.VISIBLE);

        fAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


                        Toast.makeText(user_login.this,"Login Successfull",Toast.LENGTH_LONG).show();
                        startActivity(new Intent(getApplicationContext(),HomePage.class));


                }else{
                    //Toast.makeText(user_login.this,"Check your email to verify your account",Toast.LENGTH_LONG).show();
                    Toast.makeText(user_login.this,"Login not successful",Toast.LENGTH_SHORT).show();

                }
            }
        });


    }
}
