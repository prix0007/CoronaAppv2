package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class VolunteerLogin extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    EditText vemail,vpassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.volunteer_login);

        firebaseAuth = FirebaseAuth.getInstance();


    }

    public void HomeV(View view)
    {
//        Intent vph= new Intent(getApplicationContext(),VolunteerHome.class);
//        startActivity( (getApplicationContext()),VolunteerHome.class);
//        startActivity( vph );
//        if (firebaseAuth.getCurrentUser() != null) {
//            startActivity(new Intent(VolunteerLogin.this, VolunteerHome.class));
//            finish();
//        }
        vemail = findViewById(R.id.editText2);
        vpassword = findViewById(R.id.editText3);
        final ProgressBar progressBar = findViewById(R.id.progressBar2);
//        firebaseAuth.getInstance();
        boolean flag = false;

        String email = vemail.getText().toString().trim();
        String password = vpassword.getText().toString().trim();
        if(TextUtils.isEmpty(email)){
            vemail.setError("Email is Empty");
            return;
        }
        if(TextUtils.isEmpty(password)){
            vpassword.setError("Password is Empty");
            return;
        }
        if(password.length() < 6){
            vpassword.setError("Password is too short");
            return;
        }
//        authenticate user
        progressBar.setVisibility(View.VISIBLE);
        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(getApplicationContext(),"Login Successfully",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), VolunteerHome.class));
                }
                else {
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(getApplicationContext(),"Error"+task.getException().getMessage(),Toast.LENGTH_SHORT ).show();
                }
            }
        });
    }

    public void RegV(View view)
    {
        Intent vr= new Intent(getApplicationContext(),RegisterActivityVolunteer.class);
        startActivity( vr );
    }
    public void menu(View view) {
        startActivity(new Intent(VolunteerLogin.this, page2.class));
    }



}