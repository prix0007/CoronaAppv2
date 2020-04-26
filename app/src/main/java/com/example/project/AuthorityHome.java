package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;


public class AuthorityHome extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.authority_home);
        firebaseAuth  = FirebaseAuth.getInstance();
    }
    public void vd(View view){
        startActivity(new Intent(AuthorityHome.this,Data.class));
    }
    public void mc(View view){
        startActivity(new Intent(AuthorityHome.this, Map.class));
    }
    public void co(View view){ startActivity(new Intent(AuthorityHome.this, Organizations.class));}
    public void stat(View view){ startActivity(new Intent(AuthorityHome.this, AuthStatus.class));}
    public void signout(View view){
        firebaseAuth.signOut();
        finish();
    }
}