package com.example.project;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class RegionData extends Fragment {

    private String regionName, totalCases, activeCases, recoveredCases, deceasedCases;

    TextView rn, tc, ac, rc, dc;

    public RegionData() {
        // Required empty public constructor
    }
    public RegionData(String regionName, String totalCases, String activeCases, String recoveredCases, String deceasedCases){
        this.regionName = regionName;
        this.totalCases = totalCases;
        this.activeCases = activeCases;
        this.recoveredCases = recoveredCases;
        this.deceasedCases = deceasedCases;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v  = inflater.inflate(R.layout.fragment_region_data, null, false);
        initializeView(v);

        rn.setText(regionName);
        tc.setText(totalCases);
        ac.setText(activeCases);
        rc.setText(recoveredCases);
        dc.setText(deceasedCases);

        return v;
    }

    private void initializeView(View v){
        rn = v.findViewById(R.id.regionName);
        tc = v.findViewById(R.id.region_total_cases);
        ac = v.findViewById(R.id.region_active_cases);
        rc = v.findViewById(R.id.region_recovered_cases);
        dc = v.findViewById(R.id.region_deceased_cases);
    }
}
