package com.example.project;

import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Sympnext extends AppCompatActivity {

    private Location location;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );

        setContentView( R.layout.sympnext );

        TextView dis=(TextView) findViewById( R.id.next );
        Bundle extra = getIntent().getExtras();

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
}
