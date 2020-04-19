package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.project.volunteerFragments.Home;
import com.example.project.volunteerFragments.Profile;
import com.example.project.volunteerFragments.Stats;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class HomeVolunteerv2 extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    private static final int NUM_PAGES = 3;

    private ViewPager2 viewPager;

    private FragmentStateAdapter pagerAdapter;

    //Initializing fragments
    Home home;
    Profile profile;
    Stats stats;
    MenuItem prevMenuItem;

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_volunteerv2);

        //Initializing viewPager
        viewPager = (ViewPager2) findViewById(R.id.pager);

        //Initializing the bottomNavigationView
        bottomNavigationView = (BottomNavigationView)findViewById(R.id.bottom_navigation);

        firebaseAuth = FirebaseAuth.getInstance();

        pagerAdapter = new ScreenSlidePagerAdapter(this);

        viewPager.setAdapter(pagerAdapter);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_home:
                                Log.d("From Bottom Navigation","Action Home");
                                viewPager.setCurrentItem(0);
                                break;
                            case R.id.action_profile:
                                Log.d("From Bottom Navigation","Action Profile"+ item.getTitle());
                                item.setChecked(true);
                                viewPager.setCurrentItem(1);
                                break;
                            case R.id.action_stats:
                                Log.d("From Bottom Navigation","Action Stats"+ item.getTitle());
                                item.setChecked(true);
                                viewPager.setCurrentItem(2);
                                break;
                        }
                        return false;
                    }
                });
    }

    @Override
    public void onBackPressed() {
        if (viewPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            firebaseAuth.signOut();
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
        }
    }

    /**
     * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
     * sequence.
     */
    private class ScreenSlidePagerAdapter extends FragmentStateAdapter {
        public ScreenSlidePagerAdapter(FragmentActivity fa) {
            super(fa);
        }

        @Override
        public Fragment createFragment(int position) {
            Log.d("Position",String.valueOf(position));
            switch (position) {
                case 0:
                    return new Home();
                case 1:
                    return new Profile();
                case 2:
                    return new Stats();
            }
            return new Home();
        }

        @Override
        public int getItemCount() {
            return NUM_PAGES;
        }
    }
}

