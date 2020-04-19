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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_home, null);
        return v;
    }
}



