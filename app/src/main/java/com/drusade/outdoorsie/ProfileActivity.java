package com.drusade.outdoorsie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.drusade.outdoorsie.fragments.ActivitiesFragment;
import com.drusade.outdoorsie.fragments.ExitFragment;
import com.drusade.outdoorsie.fragments.MyProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProfileActivity extends AppCompatActivity {

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.bottomNav) com.google.android.material.bottomnavigation.BottomNavigationView mBottomNav;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.fragmentContainer) FrameLayout mFragmentContainer;

    ActivitiesFragment mActivities = new ActivitiesFragment();
    MyProfileFragment mMyProfile = new MyProfileFragment();
    ExitFragment mExit = new ExitFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, mActivities).commit();

        mBottomNav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {

            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.activities:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, mActivities).commit();
                        return true;
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