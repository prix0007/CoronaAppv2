package com.example.project;


import com.example.project.RegionData;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.project.volunteerFragments.Stats;
import com.google.firebase.firestore.core.View;

import android.R.layout.*;
import android.widget.ScrollView;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;



import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

public class fetchLatestData extends AsyncTask<String, Void, List<Map<String , String>>> {
    String data = "";
    String[] summary_data  = new String[6];
    private List<Map<String , String>> regional_data  = new ArrayList<Map<String,String>>();

    public List<Map<String , String>> getRegionalData(){
        return regional_data;
    }

    @Override
    protected List<Map<String , String>> doInBackground(String... uri) {
        try {
            URL url = new URL(uri[0]);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String flag = "";
            while(flag != null){
                flag = bufferedReader.readLine();
                data += flag;
            }

            JSONObject res = new JSONObject(data);
            //Log.d("data", res.getString("data"));
            JSONObject actualData = new JSONObject(res.getString("data"));
            //Log.d("actualData", actualData.getString("summary"));
            JSONObject sum_data = new JSONObject(actualData.getString("summary"));
            summary_data[0] = sum_data.getString("total");
            summary_data[1] = sum_data.getString("confirmedCasesIndian");
            summary_data[2] = sum_data.getString("confirmedCasesForeign");
            summary_data[4] = sum_data.getString("discharged");
            summary_data[5] = sum_data.getString("deaths");
            summary_data[3] = String.valueOf(Integer.parseInt(summary_data[0]) - (Integer.parseInt(summary_data[4])+Integer.parseInt(summary_data[5])));
            JSONArray regionalData = new JSONArray(actualData.getString("regional"));
            for(int i =0 ; i<regionalData.length(); ++i){
                JSONObject state = new JSONObject((String) regionalData.getString(i));
                Map<String,String> region = new HashMap<String, String>();
                region.put("region",state.getString("loc"));
                region.put("indianConfirmed",state.getString("confirmedCasesIndian"));
                region.put("foreignConfirmed",state.getString("confirmedCasesForeign"));
                region.put("totalConfirmed",state.getString("totalConfirmed"));
                region.put("recovered",state.getString("discharged"));
                region.put("deceased",state.getString("deaths"));
                region.put("active",String.valueOf(Integer.parseInt(state.getString("totalConfirmed"))-(Integer.parseInt(state.getString("discharged"))+Integer.parseInt(state.getString("deaths")))));
                regional_data.add(i,region);
//                Log.d("Data in Map", regional_data.get(i).toString());
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return regional_data;
    }

    protected void onPostExecute(List<Map<String , String>> rd) {
        Stats.total_cases.setText(summary_data[0]);
        Stats.total_indian_cases.setText(summary_data[1]);
        Stats.total_forgien_cases.setText(summary_data[2]);
        Stats.active_cases.setText(summary_data[3]);
        Stats.recovered_cases.setText(summary_data[4]);
        Stats.deceased_cases.setText(summary_data[5]);
        //AsyncListener.doGenerateList(rd);
    }

}
