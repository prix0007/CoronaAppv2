package com.example.project;

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
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.project.R;
import com.example.project.RegionData;
import com.example.project.fetchLatestData;

import java.util.List;
import java.util.Map;




public class UserStatus extends Fragment {
    public static TextView total_cases, active_cases, recovered_cases, deceased_cases, total_indian_cases, total_foreign_cases;
    Button refresh_stats;
    public static LinearLayout dataList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View vv = inflater.inflate(R.layout.fragment_user_stats, container);

        initializeItems(vv);

        fetchLatestData process = new fetchLatestData();
        process.execute("https://api.rootnet.in/covid19-in/stats/latest");
        List<Map<String , String>> data = process.getRegionalData();
        Log.d("UserStatus.Java",String.valueOf(data.isEmpty()));
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
        ///////////////////////////////////////////////
        FragmentManager fm = getChildFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ///////////////////////////////////////
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
                    Log.d("UserStatus.Java",String.valueOf(data.isEmpty()));
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
            }
        });
        return vv;
    }

    private void initializeItems(View v){
        refresh_stats = v.findViewById(R.id.userRefreshStats11);
        total_cases = v.findViewById(R.id.stats_total11);
        total_indian_cases = v.findViewById(R.id.stats_indian11);
        total_foreign_cases = v.findViewById(R.id.stats_foreign11);
        recovered_cases = v.findViewById(R.id.stats_recovered11);
        active_cases = v.findViewById(R.id.stats_active11);
        deceased_cases = v.findViewById(R.id.stats_deceased11);
        dataList = v.findViewById(R.id.dataList11);
    }
    private void resetItems(View v){
        total_indian_cases.setText("");
        total_cases.setText("");
        total_foreign_cases.setText("");
        recovered_cases.setText("");
        active_cases.setText("");
        deceased_cases.setText("");
    }


}
