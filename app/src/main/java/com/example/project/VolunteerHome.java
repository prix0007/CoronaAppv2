package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class VolunteerHome extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.volunteer_home);

        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_navigation);

        bottomNavigationView.setSelectedItemId(R.id.homeVol);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {


                switch (item.getItemId())
                {
                    case R.id.profileVol:
                        startActivity(new Intent(getApplicationContext(),VolunteerProfile.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.statsVol:
                        startActivity(new Intent(getApplicationContext(),VolunteerStats.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.homeVol:
                        return true;

                }
                return false;
            }
        });


    }
    public void onYes(View view)
    {
        Intent yes=new Intent(getApplicationContext(),Organizations.class);
        startActivity(yes);

    }

    public void onNo(View view)
    {
        Intent no=new Intent(getApplicationContext(),VolunteerProfile.class);
        startActivity(no);

        Toast.makeText(getApplicationContext(),"Thank you for considering to volunteer.\nLogged Out Successfully.",Toast.LENGTH_SHORT).show();

    }
}