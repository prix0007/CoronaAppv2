package com.example.project.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.project.R;
import com.example.project.Sympnext;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    int total=0;
    private long mLastClickTime = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {   final View v = inflater.inflate(R.layout.fragment_symp, null);







        final Button h = (Button) v.findViewById( R.id.head );
        h.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                total=total+2;
                status(v,total);
                System.out.println( total );
                h.setBackgroundColor(getResources().getColor(R.color.change));

            }
        });





        final Button f = (Button) v.findViewById( R.id.fever );
        f.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                total=total+3;
                status(v,total);
                System.out.println( total );
                f.setBackgroundColor(getResources().getColor(R.color.change));

            }
        });




        final Button c = (Button) v.findViewById( R.id.cough );
        c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                total=total+2;
                status(v,total);
                System.out.println( total );
                c.setBackgroundColor(getResources().getColor(R.color.change));

            }
        });


        final Button b= (Button) v.findViewById( R.id.breath );
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                total=total+8;
                status(v,total);
                System.out.println( total );
                b.setBackgroundColor(getResources().getColor(R.color.change));

            }
        });





        final Button s= (Button) v.findViewById( R.id.shaking );
        s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                total=total+2;
                status(v,total);
                System.out.println( total );
                s.setBackgroundColor(getResources().getColor(R.color.change));

            }
        });



        final Button d = (Button) v.findViewById( R.id.dir );
        d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                total=total+2;
                status(v,total);
                System.out.println( total );
                d.setBackgroundColor(getResources().getColor(R.color.change));

            }
        });



        final TextView r = (TextView) v.findViewById( R.id.textView20);
        r.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageView ques=(ImageView) v.findViewById(R.id.question);
                ques.setVisibility(View.VISIBLE);
                ImageView safe=(ImageView) v.findViewById(R.id.safe);
                safe.setVisibility(View.INVISIBLE);
                ImageView onverge=(ImageView) v.findViewById(R.id.onverge);
                onverge.setVisibility(View.INVISIBLE);
                ImageView notsafe=(ImageView) v.findViewById(R.id.notsafe);
                notsafe.setVisibility(View.INVISIBLE);
                ImageView danger=(ImageView) v.findViewById(R.id.danger);
                danger.setVisibility(View.INVISIBLE);
                f.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                b.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                s.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                d.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                h.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                c.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                total=0;
            }
        });
        final TextView check = (TextView) v.findViewById( R.id.textView27);
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(total!=0){
                    Intent intent = new Intent(getActivity(), Sympnext.class);
                    intent.putExtra("total",total);
                    startActivity(intent);

                }
                else{
                    Toast.makeText(getActivity(),"No Symptom Checked!",Toast.LENGTH_SHORT).show();
                }
            }
        });


        return v;
    }
    private void status(View v,int tot){
        ImageView ques = v.findViewById(R.id.question);
        ImageView safe= v.findViewById(R.id.safe);
        ImageView onverge= v.findViewById(R.id.onverge);
        ImageView notsafe= v.findViewById(R.id.notsafe);
        ImageView danger= v.findViewById(R.id.danger);

        if(tot!=0 && tot < 4){
            ques.setVisibility(View.INVISIBLE);
            safe.setVisibility(View.VISIBLE);
            onverge.setVisibility(View.INVISIBLE);
            notsafe.setVisibility(View.INVISIBLE);
            danger.setVisibility(View.INVISIBLE);
        }
        else if((tot >= 4) && (tot < 9)){
            ques.setVisibility(View.INVISIBLE);
            safe.setVisibility(View.INVISIBLE);
            onverge.setVisibility(View.VISIBLE);
            notsafe.setVisibility(View.INVISIBLE);
            danger.setVisibility(View.INVISIBLE);
        }
        else if((tot >= 9) && (tot < 14)){
            ques.setVisibility(View.INVISIBLE);
            safe.setVisibility(View.INVISIBLE);
            onverge.setVisibility(View.INVISIBLE);
            notsafe.setVisibility(View.VISIBLE);
            danger.setVisibility(View.INVISIBLE);
        }
        else if( tot >= 14 ){
            ques.setVisibility(View.INVISIBLE);
            safe.setVisibility(View.INVISIBLE);
            onverge.setVisibility(View.INVISIBLE);
            notsafe.setVisibility(View.INVISIBLE);
            danger.setVisibility(View.VISIBLE);
        } else {
            //In case of zero or negative total
            ques.setVisibility(View.VISIBLE);
            safe.setVisibility(View.INVISIBLE);
            onverge.setVisibility(View.INVISIBLE);
            notsafe.setVisibility(View.INVISIBLE);
            danger.setVisibility(View.INVISIBLE);
        }
    }
    }



