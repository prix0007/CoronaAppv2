package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;


public class AuthorityHome extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.authority_home);
    }
    public void vd(View view){
        startActivity(new Intent(AuthorityHome.this,Data.class));

    }
    public void mc(View view){
        startActivity(new Intent(AuthorityHome.this, Map.class));

    }
    public void co(View view){
        startActivity(new Intent(AuthorityHome.this, Organizations.class));

    }
    public void signout(View view){
        startActivity(new Intent(AuthorityHome.this, page2.class));

    }
}