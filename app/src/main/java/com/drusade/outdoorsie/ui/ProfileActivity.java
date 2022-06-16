package com.drusade.outdoorsie.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.drusade.outdoorsie.R;
import com.drusade.outdoorsie.ui.fragments.MyActivitiesFragment;
import com.drusade.outdoorsie.ui.fragments.ExitFragment;
import com.drusade.outdoorsie.ui.fragments.MyProfileFragment;
import com.google.android.material.navigation.NavigationBarView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProfileActivity extends AppCompatActivity {

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.bottomNav) com.google.android.material.bottomnavigation.BottomNavigationView mBottomNav;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.fragmentContainer) FrameLayout mFragmentContainer;

    /*MyActivitiesFragment mMyActivities = new MyActivitiesFragment();*/
    MyProfileFragment mMyProfile = new MyProfileFragment();
    ExitFragment mExit = new ExitFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, mMyProfile).commit();

        mBottomNav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {

            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    /*case R.id.myActivities:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, mMyActivities).commit();
                        return true;*/
                    case R.id.profile:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, mMyProfile).commit();
                        return true;
                    case R.id.exit:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, mExit).commit();
                        return true;
                }
                return false;
            }
        });
    }
}