package com.drusade.outdoorsie;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.drusade.outdoorsie.fragments.ActivitiesFragment;
import com.drusade.outdoorsie.fragments.ExitFragment;
import com.drusade.outdoorsie.fragments.MyProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ProfileActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    ActivitiesFragment activitiesFragment = new ActivitiesFragment();
    MyProfileFragment myProfileFragment = new MyProfileFragment();
    ExitFragment exitFragment = new ExitFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
    }
}