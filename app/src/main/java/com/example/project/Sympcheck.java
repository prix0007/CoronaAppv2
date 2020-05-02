package com.example.project;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Sympcheck extends Activity {

    int total=0;
    String[] symptoms = new String[7];
    Button none;
    Integer[] checkFlag = { 0 , 0 , 0 , 0 , 0 , 0 };

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate( savedInstanceState );
        setContentView( R.layout.fragment_symp );

        none = findViewById(R.id.none); // this is none

        final Button f = findViewById( R.id.fever );
        f.setOnClickListener(view -> {
            resetNone(1);

            //Calling function to check if already tick
            if(checkActive(1)){
                Toast.makeText(this, "Already Checked!",Toast.LENGTH_SHORT).show();
                return;
            }
            total = total + 3;
//            status(total);
            System.out.println( total );
            f.setBackgroundColor(getResources().getColor(R.color.change));
            none.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            symptoms[1] = "Fever";
            checkFlag[1] = 1;
        });

        final Button c = findViewById( R.id.cough );
        c.setOnClickListener(view -> {
            resetNone(1);
            //Calling function to check if already tick
            if(checkActive(2)){
                Toast.makeText(this, "Already Checked!",Toast.LENGTH_SHORT).show();
                return;
            }
            total = total + 2;
//            status(total);
            System.out.println( total );
            c.setBackgroundColor(getResources().getColor(R.color.change));
            none.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            symptoms[2] = "Cough";
            checkFlag[2] = 1;

        });

        final Button b= findViewById( R.id.breath );
        b.setOnClickListener(view -> {
            resetNone(1);

            //Calling function to check if already tick
            if(checkActive(3)){
                Toast.makeText(this, "Already Checked!",Toast.LENGTH_SHORT).show();
                return;
            }
            total = total + 8;
//            status(total);
            System.out.println( total );
            b.setBackgroundColor(getResources().getColor(R.color.change));
            none.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            symptoms[3] = "Shortness of Breath";
            checkFlag[3] = 1;

        });

        final Button s= findViewById( R.id.shaking );
        s.setOnClickListener(view -> {
            resetNone(1);

            //Calling function to check if already tick
            if(checkActive(4)){
                Toast.makeText(this, "Already Checked!",Toast.LENGTH_SHORT).show();
                return;
            }
            total = total + 2;
//            status(total);
            System.out.println( total );
            s.setBackgroundColor(getResources().getColor(R.color.change));
            none.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            symptoms[4] = "Shaking Chills";
            checkFlag[4] = 1;
        });

        final Button d = findViewById( R.id.dir );
        d.setOnClickListener(view -> {
            resetNone(1);

            //Calling function to check if already tick
            if(checkActive(5)){
                Toast.makeText(this, "Already Checked!",Toast.LENGTH_SHORT).show();
                return;
            }

            total = total + 2;
//            status(total);
            System.out.println( total );
            d.setBackgroundColor(getResources().getColor(R.color.change));
            none.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            symptoms[5] = "Diarrhea";
            checkFlag[5] = 1;
        });

        final Button h = findViewById( R.id.head );
        h.setOnClickListener(view -> {
            //Calling Function to untick none
            resetNone(1);

            //Calling function to check if already tick
            if(checkActive(0)){
                Toast.makeText(this, "Already Checked!",Toast.LENGTH_SHORT).show();
                return;
            }

            total = total + 2;
//            status(total);
            System.out.println( total );
            h.setBackgroundColor(getResources().getColor(R.color.change));
            none.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            symptoms[0] = "Headache & Sore Throat";
            checkFlag[0] = 1;
        });


        none.setOnClickListener(view -> {

            total = 1;
//            status(total);

            none.setBackgroundColor(getResources().getColor(R.color.change));

            f.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            b.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            s.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            d.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            h.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            c.setBackgroundColor(getResources().getColor(R.color.colorAccent));

            resetSymptoms();
            symptoms[6] = "Safe";
            resetCheckFlag();

        });

        //Code to reset all fields
        final TextView r = findViewById( R.id.textView20);

        r.setOnClickListener(view -> {
//            ImageView ques= findViewById(R.id.question);
//            ques.setVisibility(View.VISIBLE);
//            ImageView safe= findViewById(R.id.safe);
//            safe.setVisibility(View.INVISIBLE);
//            ImageView onverge= findViewById(R.id.onverge);
//            onverge.setVisibility(View.INVISIBLE);
//            ImageView notsafe= findViewById(R.id.notsafe);
//            notsafe.setVisibility(View.INVISIBLE);
//            ImageView danger= findViewById(R.id.danger);
//            danger.setVisibility(View.INVISIBLE);
            f.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            b.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            s.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            d.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            h.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            c.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            none.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            total=0;
            resetSymptoms();
            resetCheckFlag();
        });

        //Code to send the Symptom checkup data to next stage
        final TextView check = (TextView) findViewById( R.id.textView27);
        check.setOnClickListener(view -> {
            if(total>1){
                Intent intent = new Intent(getApplicationContext(), Sympnext.class);
                intent.putExtra("total",total);
                intent.putExtra("sympdata", symptoms);
                startActivity(intent);
            }
            else if(total==1){
                Toast.makeText(getApplicationContext(),"You are Safe",Toast.LENGTH_SHORT).show();
                Intent no= new Intent(getApplicationContext(), Sympnext.class);
                no.putExtra("total",total);
                no.putExtra("sympdata", symptoms);
                startActivity(no);
            }

            else {
                Toast.makeText(getApplicationContext(),"No Symptom Checked!",Toast.LENGTH_SHORT).show();
            }
        });
    }
//        private void status(int tot){
//            ImageView ques = findViewById(R.id.question);
//            ImageView safe= findViewById(R.id.safe);
//            ImageView onverge= findViewById(R.id.onverge);
//            ImageView notsafe= findViewById(R.id.notsafe);
//            ImageView danger= findViewById(R.id.danger);
//            if(tot==1){
////                ques.setVisibility(View.INVISIBLE);
//                safe.setVisibility(View.INVISIBLE);
//                onverge.setVisibility(View.INVISIBLE);
//                notsafe.setVisibility(View.INVISIBLE);
//                danger.setVisibility(View.INVISIBLE);
//            }
//
//            else if(tot>1 && tot < 4){
////                ques.setVisibility(View.INVISIBLE);
//                safe.setVisibility(View.VISIBLE);
//                onverge.setVisibility(View.INVISIBLE);
//                notsafe.setVisibility(View.INVISIBLE);
//                danger.setVisibility(View.INVISIBLE);
//            }
//            else if((tot >= 4) && (tot < 9)){
////                ques.setVisibility(View.INVISIBLE);
//                safe.setVisibility(View.INVISIBLE);
//                onverge.setVisibility(View.VISIBLE);
//                notsafe.setVisibility(View.INVISIBLE);
//                danger.setVisibility(View.INVISIBLE);
//            }
//            else if((tot >= 9) && (tot < 14)){
////                ques.setVisibility(View.INVISIBLE);
//                safe.setVisibility(View.INVISIBLE);
//                onverge.setVisibility(View.INVISIBLE);
//                notsafe.setVisibility(View.VISIBLE);
//                danger.setVisibility(View.INVISIBLE);
//            }
//            else if( tot >= 14 ){
////                ques.setVisibility(View.INVISIBLE);
//                safe.setVisibility(View.INVISIBLE);
//                onverge.setVisibility(View.INVISIBLE);
//                notsafe.setVisibility(View.INVISIBLE);
//                danger.setVisibility(View.VISIBLE);
//            } else {
////                In case of zero or negative total
////                ques.setVisibility(View.VISIBLE);
//                safe.setVisibility(View.INVISIBLE);
//                onverge.setVisibility(View.INVISIBLE);
//                notsafe.setVisibility(View.INVISIBLE);
//                danger.setVisibility(View.INVISIBLE);
//            }
//        }
        private void resetSymptoms(){
            for(int i =0 ;i<6; ++i){
                symptoms[i] = "";
            }
        }
        private void resetNone(int flag){
                if(flag == 1){
                    none.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                    symptoms[6] = "";
                }
        }
        private void resetCheckFlag(){
            for(int i =0 ;i<6; ++i){
                checkFlag[i] = 0;
            }
        }
        private boolean checkActive(int index){
            if(checkFlag[index] == 1){
                return true;
            } else {
                return false;
            }
        }

    }
