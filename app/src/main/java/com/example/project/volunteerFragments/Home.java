package com.example.project.volunteerFragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.project.Organizations;
import com.example.project.R;

public class Home extends Fragment {

    Button contribute, goBack;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_volunteer_home, container);
        contribute = v.findViewById(R.id.volunteerContribute);
        goBack = v.findViewById(R.id.volunteerGoBack);

        contribute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent yes=new Intent(getActivity(), Organizations.class);
                startActivity(yes);
            }
        });

        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"Thank you for considering to volunteer.\nLogged Out Successfully.",Toast.LENGTH_SHORT).show();
            }
        });
        return v;
    }
}
