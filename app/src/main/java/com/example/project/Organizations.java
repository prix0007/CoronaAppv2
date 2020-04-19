package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Organizations extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.organizations);
    }

    public void site1(View view)
    {
        Intent i1 = new Intent(Intent.ACTION_VIEW, Uri.parse("http://ayush.gov.in/covid-19-ayush-volunteers-0"));
        startActivity(i1);

    }

    public void site2(View view)
    {

        Intent i2 = new Intent(Intent.ACTION_VIEW, Uri.parse("https://self4society.mygov.in/"));
        startActivity(i2);
    }
    public void site3(View view)
    {
        Intent i3 = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.mygov.in/task/join-war-against-covid-19-register-volunteer/"));
        startActivity(i3);
    }

    public void site4(View view)
    {
        Intent i4 = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.unv.org/Volunteers-for-Novel-Coronavirus-COVID-19-pandemic-response/"));
        startActivity(i4);

    }
}