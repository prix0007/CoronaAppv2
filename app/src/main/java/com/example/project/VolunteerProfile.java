package com.example.project;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class VolunteerProfile extends AppCompatActivity {


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.volunteer_profile);




        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_navigation);

        bottomNavigationView.setSelectedItemId(R.id.profileVol);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId())
                {
                    case R.id.profileVol:
                        return true;
                    case R.id.statsVol:
                        startActivity(new Intent(getApplicationContext(),VolunteerStats.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.homeVol:
                        startActivity(new Intent(getApplicationContext(),VolunteerHome.class));
                        overridePendingTransition(0,0);
                        return true;

                }
                return false;
            }
        });


    }
    public void signO (View view)
    {
       finish();
    }
}