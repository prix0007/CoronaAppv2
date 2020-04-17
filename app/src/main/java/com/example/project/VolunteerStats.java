package com.example.project;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import org.json.JSONException;
import org.json.JSONObject;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;



public class VolunteerStats extends AppCompatActivity {

    TextView result,tot,concas;
    ProgressBar progressBar;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.volunteer_stats);

        result=findViewById(R.id.textView24);
        progressBar = findViewById(R.id.progressBar);
    tot=findViewById(R.id.textView34);
    concas=findViewById(R.id.textView36);
        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_navigation);

        bottomNavigationView.setSelectedItemId(R.id.statsVol);

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
                        return true;
                    case R.id.homeVol:
                        startActivity(new Intent(getApplicationContext(),VolunteerHome.class));
                        overridePendingTransition(0,0);
                        return true;

                }



                return false;
            }
        });

        TextView ref=(TextView)findViewById( R.id.textView29 );
        ref.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new RetrieveFeedTask().execute();
            }
        });


    }







/////////////////////////////////////////////////////////////////////////////////Stats Displaying class



class RetrieveFeedTask extends AsyncTask<Void, Void, String> {

    private Exception exception;



    protected void onPreExecute() {
        progressBar.setVisibility(View.VISIBLE);

    }

    protected String doInBackground(Void... urls) {


        try {
            URL url = new URL("https://api.rootnet.in/covid19-in/stats/latest");

            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }
                bufferedReader.close();
                return stringBuilder.toString();
            }
            finally{
                urlConnection.disconnect();
            }
        }
        catch(Exception e) {
            Log.e("ERROR", e.getMessage(), e);
            return null;
        }
    }

    protected void onPostExecute(String response) {
        String totalIn,confirmedIn;
        if(response == null) {
            response = "THERE WAS AN ERROR";
        }
        progressBar.setVisibility(View.GONE);
        Log.i("INFO", response);


        try {
            // get JSONObject from JSON file
            JSONObject obj = new JSONObject(response);

            // fetch JSONObject named employee
            JSONObject summary = obj.getJSONObject("summary");
            // get employee name and salary
            totalIn = summary.getString("totalConfirmed");
            confirmedIn = summary.getString("confirmedCasesIndian");
            // set employee name and salary in TextView's
            tot.setText(totalIn);
            concas.setText(confirmedIn);


        } catch (JSONException e) {
            e.printStackTrace();
        }


        }
}

}//end of VolunteerStats