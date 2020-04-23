package com.example.project;

import android.content.Intent;
import android.os.Build;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class UserStatus extends AppCompatActivity {

    public TextView total_cases, active_cases, recovered_cases, deceased_cases, total_indian_cases, total_foreign_cases;
    Button refresh_stats;
    public LinearLayout dataList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_user_stats);





        refresh_stats = findViewById(R.id.userRefreshStats11);
        total_cases = findViewById(R.id.stats_total11);
        total_indian_cases = findViewById(R.id.stats_indian11);
        total_foreign_cases = findViewById(R.id.stats_foreign11);
        recovered_cases = findViewById(R.id.stats_recovered11);
        active_cases = findViewById(R.id.stats_active11);
        deceased_cases = findViewById(R.id.stats_deceased11);
        dataList = findViewById(R.id.dataList11);



        fetchLatestData process = new fetchLatestData();
        process.execute("https://api.rootnet.in/covid19-in/stats/latest");



        refresh_stats.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                fetchLatestData process = new fetchLatestData();
                process.execute("https://api.rootnet.in/covid19-in/stats/latest");
                List<Map<String , String>> data = process.getRegionalData();
                //Loop on Main Thread halting Data
                while(data.isEmpty()){
                    Log.d("UserStatus.Java",String.valueOf(data.isEmpty()));
                    //Loop with a ping of 500 mills to fetch data
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    data = process.getRegionalData();
                }

            }
        });





    }




}









