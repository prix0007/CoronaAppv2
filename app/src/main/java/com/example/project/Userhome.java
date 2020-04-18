package com.example.project;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

public class Userhome extends AppCompatActivity {
    private static final int MY_PERMISSIONS_REQUEST_ACCESS_COURSE_LOCATION =1 ;
    TextView fetch;

    private FusedLocationProviderClient fusedLocationClient;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.userhome);



        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);



    }



    private void fetchLocation() {

        if (ContextCompat.checkSelfPermission(Userhome.this,
                Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {


            if (ActivityCompat.shouldShowRequestPermissionRationale(Userhome.this,
                    Manifest.permission.ACCESS_COARSE_LOCATION)) {

                //user denied [permission for 2nd 3rd time, then show him why you need it

                new AlertDialog.Builder(this)
                .setTitle("Required Location Permission")
                        .setMessage("You have to give permission in order to access this feature.")
                        .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                           //when user finally says yes
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCompat.requestPermissions(Userhome.this,
                                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_ACCESS_COURSE_LOCATION);
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            //when user clicks cancel
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .create()
                        .show();


            } else {
                // User denied permission for first time
                ActivityCompat.requestPermissions(Userhome.this,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                        MY_PERMISSIONS_REQUEST_ACCESS_COURSE_LOCATION);


            }
        }


        else {
            //Location permission is granted here for very first time
            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {

                            if (location != null) {
                                //User ka longitude and latitude are stored here

                                Double latitude=location.getLatitude();
                                Double longitude=location.getLongitude();

                                Toast.makeText(getApplicationContext(),"longitude="+longitude+"latitude="+latitude,Toast.LENGTH_LONG).show();

                            }
                        }
                    });
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==MY_PERMISSIONS_REQUEST_ACCESS_COURSE_LOCATION)
        {
            if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
            {
                Intent ii1=new Intent(getApplicationContext(),Sympnext.class);
                startActivity(ii1);
            }
            else
            {
                Toast.makeText(getApplicationContext(),"Permission was not granted. Fail.",Toast.LENGTH_LONG).show();
            }
        }
    }

    public void check(View view) {
        Intent ch=new Intent(getApplicationContext(),Sympcheck.class);
        startActivity(ch);
    }

    public void preventhome(View view) {
        Intent ph=new Intent(getApplicationContext(),Prevent.class);
        startActivity(ph);
    }

    public void signout(View view) {
        Intent sg=new Intent(getApplicationContext(),page2.class);
        startActivity(sg);
    }
    public void ma(View view) {
        Intent sg=new Intent(getApplicationContext(),Map.class);
        startActivity(sg);
    }
}
