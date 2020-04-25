package com.example.project;

import android.os.Bundle;
import android.renderscript.Type;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Data extends AppCompatActivity {


    private static final String TAG = "DataJava";
    private static ArrayList<Type> mArrayList = new ArrayList<>();

    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    ListView listView;

    ArrayList<String> arrayList = new ArrayList();
    ArrayAdapter<String> arrayAdapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.datalayout);

        firebaseAuth  = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        listView = findViewById(R.id.listview);

        firebaseFirestore.collection("user_symptoms")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                HashMap sympmap = (HashMap) document.get("symptoms");
                                String symptoms = "";
                                for(int i =0 ; i<7; ++i){
                                    if(sympmap.get(String.valueOf(i))!=null){
                                        symptoms += sympmap.get(String.valueOf(i)) +" ,";
                                    }
                                }
                                HashMap locmap = (HashMap) document.get("loc");
                                String loc = "( Latitude: " + locmap.get("latitude").toString() +"\nLongitude"+ locmap.get("longitude").toString()+" )";
                                String data = "Name of User: "+ document.getString("name")+
                                        "\nAge: "+ document.getString("age")+"\nSymptoms: "+symptoms
                                        +"\nLast Location:\n"+loc;
                                arrayList.add(data);

                            }
                            arrayAdapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,arrayList);
                            listView.setAdapter(arrayAdapter);
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

}
