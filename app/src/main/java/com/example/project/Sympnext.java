package com.example.project;

import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Sympnext extends AppCompatActivity {

    private Location location;

    private FusedLocationProviderClient fusedLocationClient;

    Double latitude;
    Double longitude;

    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );

        setContentView( R.layout.sympnext );

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        TextView dis=(TextView) findViewById( R.id.next );
        Bundle extra = getIntent().getExtras();

        String[] user_symptoms;
        user_symptoms = (String[]) extra.get("sympdata");
        Map<String, Object> sympmap = new HashMap<>();
        for(int i = 0; i<6; ++i){
            sympmap.put(String.valueOf(i),user_symptoms[i]);
        }

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            latitude=location.getLatitude();
                            longitude=location.getLongitude();
                            Toast.makeText(getApplicationContext(),"longitude="+longitude+"latitude="+latitude,Toast.LENGTH_LONG).show();
                        } else {
                            latitude = 0.0;
                            longitude = 0.0;
                            Toast.makeText(getApplicationContext(),"Error Location not Found",Toast.LENGTH_LONG).show();
                        }
                    }
                });

        //Storing user Symtoms Data

        DocumentReference documentReference = firebaseFirestore.collection("users").document(firebaseAuth.getCurrentUser().getUid());
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            private static final String TAG = "From Sympnext" ;

            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getString("role"));
                        Map<String, Object> user = new HashMap<>();
                        Map<String, Object> loc = new HashMap<>();
                        loc.put("latitude",latitude);
                        loc.put("longitude",longitude);
                        user.put("symptoms", sympmap);
                        user.put("name",document.getString("fullname"));
                        user.put("loc",loc);
                        user.put("age",document.getString("age"));
                        DocumentReference docref = firebaseFirestore.collection("user_symptoms").document(firebaseAuth.getCurrentUser().getUid());
                        docref.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(getApplicationContext(), "SuccessFully Submitted your data", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getApplicationContext(), "Error in submitting data to server", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with " + task.getException());
                }
            }
        });


        int a = extra.getInt("total");

        if(a<=4){
            String display="Your Symptoms shows that you're healthy and fine. Keep On Social Distancing and Stay Healthy";
            dis.setText( display );
        }
        if((a>4) && (a<=9)){
            String display="Your Symptoms shows that you are at low risk of being affected. Keep On Social Distancing and Stay Healthy";
            dis.setText( display );
        }
        if((a>9) && (a<=14)){
            String display="Your Symptoms shows that might affected. Do Consult a Doctor and Keep On Social Distancing and Stay Healthy";
            dis.setText( display );
        }
        if(a>=14){
            String display="Your Symptoms shows that you have a high posiibility of being affected. Do Consult a Doctor and Keep On Social Distancing";
            dis.setText( display );
        }


        Log.d("From Symnext: ", "Gte Long and Lat here");

    }

    public void telephone(View view) {
        Intent tele = new Intent(Intent.ACTION_DIAL);
        tele.setData( Uri.parse("tel:+91-11-23978046"));
        startActivity(tele);
    }
    public void prevent(View view) {
        Intent pre= new Intent(getApplication(),Prevent.class);
        startActivity(pre);
    }

    public void Goo(View view) {
        Intent up = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.mygov.in/covid-19/?cbps=1"));
        startActivity(up);
    }

    public void back(View view) {
        Intent ba = new Intent(getApplication(),Userhome.class);
        startActivity(ba);
    }
}
