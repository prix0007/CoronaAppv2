package com.example.project;

import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;

public class Map extends FragmentActivity implements  OnMapReadyCallback {

    private static final String TAG = "Map.java";
    private double curr_long, curr_lat;
    private Location loc;
    private GoogleMap mMap;

    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;

    private FusedLocationProviderClient fusedLocationClient;


    public Map(){

    }
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.fragment_map);

        firebaseAuth  = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            loc = location;
                            curr_lat = location.getLatitude();
                            curr_long = location.getLongitude();
                            Log.d("Current Location",String.valueOf(location.getLongitude())+String.valueOf(location.getLatitude()));
                            Toast.makeText(getApplicationContext(), "Latitude: "+String.valueOf(location.getLatitude())+" | Longitude: "+String.valueOf(location.getLongitude()),Toast.LENGTH_LONG).show();
                        }
                    }
                });

        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);



//        fusedLocationClient.getLastLocation()
//                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
//                    @Override
//                    public void onSuccess(Location location) {
//                        // Got last known location. In some rare situations this can be null.
//                        if (location != null) {
//                            loc = location;
//                            curr_lat = location.getLatitude();
//                            curr_long = location.getLongitude();
//                            Log.d("Current Location",String.valueOf(location.getLongitude())+String.valueOf(location.getLatitude()));
//                            Toast.makeText(getApplicationContext(), "Latitude: "+String.valueOf(location.getLatitude())+" | Longitude: "+String.valueOf(location.getLongitude()),Toast.LENGTH_LONG).show();
//                        }
//                    }
//                });

    }



    public void onMapReady(GoogleMap map) {
        mMap = map;
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.d("In MAP","RETURNED");
            Toast.makeText(getApplicationContext(), "Please allow app location permissions", Toast.LENGTH_SHORT).show();
            return;
        }

        mMap.setMyLocationEnabled(true);

        if(loc != null){
            Log.d("Map.java","Location is Filled"+String.valueOf(curr_lat)+String.valueOf(curr_long));
            CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(loc.getLatitude(), loc.getLongitude()));
            CameraUpdate zoom = CameraUpdateFactory.zoomTo(11);
            mMap.moveCamera(center);
            mMap.animateCamera(zoom);
        } else {
            Log.d("Map.java","Location is Empty"+String.valueOf(curr_lat)+String.valueOf(curr_long));
        }

        mMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {
                CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(loc.getLatitude(), loc.getLongitude()));
                CameraUpdate zoom = CameraUpdateFactory.zoomTo(11);
                mMap.moveCamera(center);
                mMap.animateCamera(zoom);

//                mMap.clear();

                firebaseFirestore.collection("user_symptoms")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        HashMap locmap = (HashMap) document.get("loc");
                                        if(document.get("severity") != null) {
                                            Long severity = (Long) document.get("severity");
                                            if (severity <= 4) {
                                                MarkerOptions mp = new MarkerOptions();
                                                mp.position(new LatLng(Double.parseDouble(locmap.get("latitude").toString()), (Double) locmap.get("longitude")));
                                                mp.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                                                mp.title(document.getString("name"));
                                                mMap.addMarker(mp);
                                            } else if (severity > 4 && severity < 9) {
                                                MarkerOptions mp = new MarkerOptions();
                                                mp.position(new LatLng(Double.parseDouble(locmap.get("latitude").toString()), (Double) locmap.get("longitude")));
                                                mp.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));
                                                mp.title(document.getString("name"));
                                                mMap.addMarker(mp);
                                            } else if (severity > 8 && severity <= 14) {
                                                MarkerOptions mp = new MarkerOptions();
                                                mp.position(new LatLng(Double.parseDouble(locmap.get("latitude").toString()), (Double) locmap.get("longitude")));
                                                mp.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
                                                mp.title(document.getString("name"));
                                                mMap.addMarker(mp);
                                            } else {
                                                MarkerOptions mp = new MarkerOptions();
                                                mp.position(new LatLng(Double.parseDouble(locmap.get("latitude").toString()), (Double) locmap.get("longitude")));
                                                mp.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                                                mp.title(document.getString("name"));
                                                mMap.addMarker(mp);
                                            }
                                        }
                                    }
                                } else {
                                    Log.d(TAG, "Error getting documents: ", task.getException());
                                }
                            }
                        });
            }
        });

    }}