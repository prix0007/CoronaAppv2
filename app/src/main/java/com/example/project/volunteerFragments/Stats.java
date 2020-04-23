package com.example.project.volunteerFragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.project.R;
import com.example.project.RegionData;
import com.example.project.fetchLatestData;

import java.util.List;
import java.util.Map;

public class Stats extends Fragment {
    public static TextView total_cases, active_cases, recovered_cases, deceased_cases, total_indian_cases, total_forgien_cases;
    Button refresh_stats;
    public static LinearLayout dataList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_volunteer_stats, container, false);

        initializeItems(v);

        fetchLatestData process = new fetchLatestData();
        process.execute("https://api.rootnet.in/covid19-in/stats/latest");
        List<Map<String , String>> data = process.getRegionalData();
        Log.d("Stats.Java",String.valueOf(data.isEmpty()));
        //Loop on Main Thread halting Data
        while(data.isEmpty()){
            Log.d("Stats.Java",String.valueOf(data.isEmpty()));
            //Loop with a ping of 500 mills to fetch data
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            data = process.getRegionalData();
        }
        FragmentManager fm = getChildFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        for(Map<String, String> a: data){
            ft.add(R.id.dataList, new RegionData(a.get("region"), a.get("totalConfirmed"), a.get("active"),a.get("recovered"),a.get("deceased")));
        }
        ft.commit();

        refresh_stats.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                resetItems(v);
                fetchLatestData process = new fetchLatestData();
                process.execute("https://api.rootnet.in/covid19-in/stats/latest");
                List<Map<String , String>> data = process.getRegionalData();
                //Loop on Main Thread halting Data
                while(data.isEmpty()){
                    Log.d("Stats.Java",String.valueOf(data.isEmpty()));
                    //Loop with a ping of 500 mills to fetch data
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    data = process.getRegionalData();
                }
                FragmentManager fm = getChildFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                dataList.removeAllViews();
                for(Map<String, String> a: data){
                    ft.add(R.id.dataList, new RegionData(a.get("region"), a.get("totalConfirmed"), a.get("active"),a.get("recovered"),a.get("deceased")));
                }
                ft.commit();
            }
        });
        return v;
    }

    private void initializeItems(View v){
        refresh_stats = v.findViewById(R.id.volunteerRefreshStats);
        total_cases = v.findViewById(R.id.stats_total);
        total_indian_cases = v.findViewById(R.id.stats_indian);
        total_forgien_cases = v.findViewById(R.id.stats_foreign);
        recovered_cases = v.findViewById(R.id.stats_recovered);
        active_cases = v.findViewById(R.id.stats_active);
        deceased_cases = v.findViewById(R.id.stats_deceased);
        dataList = v.findViewById(R.id.dataList);
    }
    private void resetItems(View v){
        total_indian_cases.setText("");
        total_cases.setText("");
        total_forgien_cases.setText("");
        recovered_cases.setText("");
        active_cases.setText("");
        deceased_cases.setText("");
    }

}

