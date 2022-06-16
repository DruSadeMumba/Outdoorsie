package com.drusade.outdoorsie.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.drusade.outdoorsie.Constants;
import com.drusade.outdoorsie.R;
import com.drusade.outdoorsie.adapters.ForcastDisplayAdapter;
import com.drusade.outdoorsie.models.AnActivity;
import com.drusade.outdoorsie.models.Forecast;
import com.drusade.outdoorsie.models.YahooWeatherLocationSearchResponse;
import com.drusade.outdoorsie.network.YahooWeatherApi;
import com.drusade.outdoorsie.network.YahooWeatherClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivitiesDetailActivity extends AppCompatActivity {

    private ForcastDisplayAdapter mDisplayAdapter;
    private List<Forecast> forecasts;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.recyclerView) RecyclerView mRecyclerView;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.activitiesNameDetailTextView) TextView mActivitiesNameDetailTextView;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.activitiesLocationDetailTextView) TextView mActivitiesLocationDetailTextView;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activities_detail);

        ButterKnife.bind(this);

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

                    mDisplayAdapter = new ForcastDisplayAdapter(ActivitiesDetailActivity.this, forecasts);
                    mRecyclerView.setAdapter(mDisplayAdapter);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ActivitiesDetailActivity.this);
                    mRecyclerView.setLayoutManager(layoutManager);
                    mRecyclerView.setHasFixedSize(true);

                    showWeather();
                }
            }

            @Override
            public void onFailure(Call<YahooWeatherLocationSearchResponse> call, Throwable t) {

            }
        });

        mActivitiesLocationDetailTextView.setText("Location: " + location);
        mActivitiesNameDetailTextView.setText("Activity: " + activityName);

    }

    private void showWeather() {mRecyclerView.setVisibility(View.VISIBLE);}

}
