package com.example.project;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import static com.google.common.base.Ascii.FF;

public class Sympcheck extends Activity {

    int total=0;
    String[] symptoms = new String[7];

    Integer[] checkFlag = { 0 , 0 , 0 , 0 , 0 , 0 };

    //CheckBox Initialization
    CheckBox nC,f,h,c,b,d,s; //nC=none | fC=fever | hC=headache | cC=cough | bC=breath | dC=diarrhea | sC=shaking chills




    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate( savedInstanceState );
        setContentView( R.layout.fragment_symp );

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
            f.setChecked(false);
            b.setChecked(false);
            s.setChecked(false);
            d.setChecked(false);
            h.setChecked(false);
            c.setChecked(false);
            nC.setChecked(false);
            total=0;
            resetSymptoms();
            resetCheckFlag();
        });

        //Code to send the Symptom checkup data to next stage
        final TextView check = findViewById( R.id.textView27);
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

    public void onCheckBoxes(View view)
    {
        nC = findViewById(R.id.noneC); // this is none
        f = findViewById(R.id.feverC);
        c = findViewById(R.id.coughC);
        b = findViewById(R.id.breathC);
        s = findViewById(R.id.chillsC);
        d = findViewById(R.id.dirC);
        h = findViewById(R.id.headC);


        if (nC.isChecked()) {
            total = 1;
//            status(total);
            f.setChecked(false);
            b.setChecked(false);
            s.setChecked(false);
            d.setChecked(false);
            h.setChecked(false);
            c.setChecked(false);

            resetSymptoms();
            symptoms[6] = "Safe";
            resetCheckFlag();
        }

        if (f.isChecked()) {
            resetNone();

            total = total + 3;
//            status(total);
            System.out.println(total);

            symptoms[1] = "Fever";
            checkFlag[1] = 1;
        }


        if (c.isChecked()) {
            resetNone();


            total = total + 2;
//            status(total);
            System.out.println(total);


            symptoms[2] = "Cough";
            checkFlag[2] = 1;

        }


        if (b.isChecked()) {
            resetNone();


            total = total + 8;
//            status(total);
            System.out.println(total);


            symptoms[3] = "Shortness of Breath";
            checkFlag[3] = 1;

        }


        if (s.isChecked()) {
            resetNone();


            total = total + 2;
//            status(total);
            System.out.println(total);

            symptoms[4] = "Shaking Chills";
            checkFlag[4] = 1;
        }


        if (d.isChecked()) {
            resetNone();


            total = total + 2;
//            status(total);
            System.out.println(total);

            symptoms[5] = "Diarrhea";
            checkFlag[5] = 1;
        }


        if (h.isChecked()) {
            //Calling Function to untick none
            resetNone();


            total = total + 2;
//            status(total);
            System.out.println(total);

            symptoms[0] = "Headache & Sore Throat";
            checkFlag[0] = 1;
        }
    }





//        private void status(int tot){
//            ImageView ques = findViewById(R.id.question);
//            ImageView safe= findViewById(R.id.safe);
//            ImageView onverge= findViewById(R.id.onverge);
//            ImageView notsafe= findViewById(R.id.notsafe);
//            ImageView danger= findViewById(R.id.danger);
//            if(tot==1){
//                ques.setVisibility(View.INVISIBLE);
//                safe.setVisibility(View.INVISIBLE);
//                onverge.setVisibility(View.INVISIBLE);
//                notsafe.setVisibility(View.INVISIBLE);
//                danger.setVisibility(View.INVISIBLE);
//            }
//
//            else if(tot>1 && tot < 4){
//                ques.setVisibility(View.INVISIBLE);
//                safe.setVisibility(View.VISIBLE);
//                onverge.setVisibility(View.INVISIBLE);
//                notsafe.setVisibility(View.INVISIBLE);
//                danger.setVisibility(View.INVISIBLE);
//            }
//            else if((tot >= 4) && (tot < 9)){
//                ques.setVisibility(View.INVISIBLE);
//                safe.setVisibility(View.INVISIBLE);
//                onverge.setVisibility(View.VISIBLE);
//                notsafe.setVisibility(View.INVISIBLE);
//                danger.setVisibility(View.INVISIBLE);
//            }
//            else if((tot >= 9) && (tot < 14)){
//                ques.setVisibility(View.INVISIBLE);
//                safe.setVisibility(View.INVISIBLE);
//                onverge.setVisibility(View.INVISIBLE);
//                notsafe.setVisibility(View.VISIBLE);
//                danger.setVisibility(View.INVISIBLE);
//            }
//            else if( tot >= 14 ){
//                ques.setVisibility(View.INVISIBLE);
//                safe.setVisibility(View.INVISIBLE);
//                onverge.setVisibility(View.INVISIBLE);
//                notsafe.setVisibility(View.INVISIBLE);
//                danger.setVisibility(View.VISIBLE);
//            } else {
//                //In case of zero or negative total
//                ques.setVisibility(View.VISIBLE);
//                safe.setVisibility(View.INVISIBLE);
//                onverge.setVisibility(View.INVISIBLE);
//                notsafe.setVisibility(View.INVISIBLE);
//                danger.setVisibility(View.INVISIBLE);
//            }
        private void resetSymptoms(){
            for(int i =0 ;i<6; ++i){
                symptoms[i] = "";
            }
        }
        private void resetNone(){

                    nC.setChecked(false);
                    symptoms[6] = "";

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
