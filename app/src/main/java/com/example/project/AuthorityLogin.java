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

public class AuthorityLogin extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    EditText aemail,apassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.authority_login);


        firebaseAuth = FirebaseAuth.getInstance();
    }

    public void HomeA(View view)
    {
//        if (firebaseAuth.getCurrentUser() != null) {
//            startActivity(new Intent(AuthorityLogin.this, AuthorityHome.class));
//            finish();
//        }
        aemail = findViewById(R.id.editText2);
        apassword = findViewById(R.id.editText3);
        final ProgressBar progressBar = findViewById(R.id.progressBar2);
//        firebaseAuth.getInstance();
        boolean flag = false;

        String email = aemail.getText().toString().trim();
        String password = apassword.getText().toString().trim();
        if(TextUtils.isEmpty(email)){
            aemail.setError("Email is Empty");
            return;
        }
        if(TextUtils.isEmpty(password)){
            apassword.setError("Password is Empty");
            return;
        }
        if(password.length() < 8){
            apassword.setError("Password is too short");
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(getApplicationContext(),"Login Successfully",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), AuthorityHome.class));
                }
                else {
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(getApplicationContext(),"Error"+task.getException().getMessage(),Toast.LENGTH_SHORT ).show();
                }
            }
        });
//            startActivity(new Intent(AuthorityLogin.this, AuthorityHome.class));
//            finish();
        }



    public void rega(View view) {
        startActivity(new Intent(AuthorityLogin.this, RegisterActivityAuthority.class));
    }
    public void menu(View view) {
        startActivity(new Intent(AuthorityLogin.this, page2.class));
    }
}
