package com.example.project;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Userhome extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_ACCESS_COURSE_LOCATION =1 ;

    private static final int REQUEST_CHECK_SETTINGS = 0x1;


    private FusedLocationProviderClient fusedLocationClient;



    private Button symptoms, mapcheck, preventioncontrol, signout, userName,button3;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.userhome);
        initializeItems();

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        DocumentReference documentReference = firebaseFirestore.collection("users").document(firebaseAuth.getCurrentUser().getUid());

        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();
                    userName.setText("Hello, \n"+document.getString("fullname"));
                }
            }
        });

        symptoms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ch=new Intent(getApplicationContext(),Sympcheck.class);
                startActivity(ch);
            }
        });

        mapcheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent map=new Intent(getApplicationContext(),Map.class);
                startActivity(map);
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent b=new Intent(getApplicationContext(), UserStatus.class);
                startActivity(b);
            }
        });

        preventioncontrol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ph=new Intent(getApplicationContext(),Prevent.class);
                startActivity(ph);
            }
        });

        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                finish();
            }
        });

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        fetchLocation();

    }

    private void initializeItems(){
        symptoms = findViewById(R.id.symptoms);
        mapcheck = findViewById(R.id.mapcheck);
        preventioncontrol = findViewById(R.id.preventioncontrol);
        signout = findViewById(R.id.signout);
        userName = findViewById(R.id.userName);
        button3 = findViewById(R.id.button3);
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
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, MY_PERMISSIONS_REQUEST_ACCESS_COURSE_LOCATION);
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
                Intent ii1=new Intent(getApplicationContext(),Map.class);
                startActivity(ii1);
            }
            else
            {
                Toast.makeText(getApplicationContext(),"Permission was not granted. Fail.",Toast.LENGTH_LONG).show();
            }
        }
    }
}
