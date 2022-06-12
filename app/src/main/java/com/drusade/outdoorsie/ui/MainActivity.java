package com.drusade.outdoorsie.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.drusade.outdoorsie.Constants;
import com.drusade.outdoorsie.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.viewActivitiesButton) Button mViewActivitiesButton;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.appNameText) TextView mAppNameText;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.appSloganText) TextView mAppSloganText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mViewActivitiesButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == mViewActivitiesButton) {
            Intent intent = new Intent(MainActivity.this, AddActivity.class);
            startActivity(intent);
        }
    }
}