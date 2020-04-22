package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.net.Uri;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Organizations extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.organizations);

        TextView site1=findViewById(R.id.textView26);  //RAM CORPS
        TextView site2=findViewById(R.id.textView32);  //UN
        TextView site3=findViewById(R.id.textView28);  //iVOLUNTEER
        TextView site4=findViewById(R.id.textView31);  //WAR AGAINST COVID

    site1.setOnClickListener(v -> {
        Intent i1 = new Intent(Intent.ACTION_VIEW, Uri.parse("https://volunteer.ramusa.org/"));
        startActivity(i1);
    });

        site2.setOnClickListener(v -> {
            Intent i2 = new Intent(Intent.ACTION_VIEW, Uri.parse("https://self4society.mygov.in/"));
            startActivity(i2);
        });

        site3.setOnClickListener(v -> {
            Intent i3 = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.mygov.in/task/join-war-against-covid-19-register-volunteer/"));
            startActivity(i3);
        });

        site4.setOnClickListener(v -> {
            Intent i4 = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.unv.org/Volunteers-for-Novel-Coronavirus-COVID-19-pandemic-response/"));
            startActivity(i4);
        });

    }










    /////////////////


    public void site2(View view)
    {


    }
    public void site3(View view)
    {

    }

    public void site4(View view)
    {


    }

}