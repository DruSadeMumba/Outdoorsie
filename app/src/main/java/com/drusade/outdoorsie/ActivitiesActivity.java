package com.drusade.outdoorsie;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ActivitiesActivity extends AppCompatActivity implements View.OnClickListener {

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.viewProfileButton) Button mViewProfileButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activities);
        ButterKnife.bind(this);
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