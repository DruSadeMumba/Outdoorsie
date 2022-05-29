package com.drusade.outdoorsie;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.drusade.outdoorsie.adapters.ActivitiesArrayAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ActivitiesActivity extends AppCompatActivity implements View.OnClickListener {

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.activitiesTextView) TextView mActivitiesTextView;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.activitiesListView) ListView mActivitiesListView;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.viewProfileButton) Button mViewProfileButton;

    private final String [] activities = new String [] {
            "Camping Trip", "Picnic", "Hiking", "Boating"
    };

    private final String [] locations = new String[] {
        "Maasai Mara", "Ololua", "Longonot", "Sagana"
    };

    private final String [] weathers = new String[] {
        "Rainy", "Sunny", "Cloudy", "Thunder Storm"
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activities);
        ButterKnife.bind(this);

        ActivitiesArrayAdapter adapter = new ActivitiesArrayAdapter(this, android.R.layout.simple_list_item_1, activities, locations, weathers);
        mActivitiesListView.setAdapter(adapter);

        mViewProfileButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == mViewProfileButton) {
            Intent intent = new Intent(ActivitiesActivity.this, ProfileActivity.class);
            startActivity(intent);
        }
    }
}