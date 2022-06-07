package com.drusade.outdoorsie.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.drusade.outdoorsie.R;
import com.drusade.outdoorsie.adapters.ActivitiesDisplayAdapter;
import com.drusade.outdoorsie.models.Forecast;
import com.drusade.outdoorsie.models.YahooWeatherLocationSearchResponse;
import com.drusade.outdoorsie.network.YahooWeatherApi;
import com.drusade.outdoorsie.network.YahooWeatherClient;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivitiesActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivitiesDisplayAdapter mDisplayAdapter;
    private List<Forecast> forecasts;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.activitiesTextView) TextView mActivitiesTextView;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.recyclerView) RecyclerView mRecyclerView;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.viewProfileButton) Button mViewProfileButton;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.activitiesNameTextView) TextView mActivitiesNameTextView;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.activitiesLocationTextView) TextView mActivitiesLocationTextView;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activities);

        ButterKnife.bind(this);

        mViewProfileButton.setOnClickListener(this);

        Intent intent = getIntent();
        String location = intent.getStringExtra("location");
        String activityName = intent.getStringExtra("activityName");

        YahooWeatherApi client = YahooWeatherClient.getClient();
        Call<YahooWeatherLocationSearchResponse> call = client.getWeather(location, "c");;

        call.enqueue(new Callback<YahooWeatherLocationSearchResponse>() {
            @Override
            public void onResponse(Call<YahooWeatherLocationSearchResponse> call, Response<YahooWeatherLocationSearchResponse> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    forecasts = response.body().getForecasts();

                    mDisplayAdapter = new ActivitiesDisplayAdapter(ActivitiesActivity.this, forecasts);
                    mRecyclerView.setAdapter(mDisplayAdapter);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ActivitiesActivity.this);
                    mRecyclerView.setLayoutManager(layoutManager);
                    mRecyclerView.setHasFixedSize(true);

                    showWeather();
                }
            }

            @Override
            public void onFailure(Call<YahooWeatherLocationSearchResponse> call, Throwable t) {

            }
        });

        mActivitiesLocationTextView.setText("Location: " + location);
        mActivitiesNameTextView.setText("Activity: " + activityName);

    }

    @Override
    public void onClick(View v){
        if (v == mViewProfileButton) {
            Intent intent = new Intent(ActivitiesActivity.this, ProfileActivity.class);
            startActivity(intent);
        }
    }
    private void showWeather() {mRecyclerView.setVisibility(View.VISIBLE);}
}
