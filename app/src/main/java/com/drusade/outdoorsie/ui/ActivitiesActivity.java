package com.drusade.outdoorsie.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.drusade.outdoorsie.Constants;
import com.drusade.outdoorsie.R;
import com.drusade.outdoorsie.adapters.WeatherDisplayAdapter;
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

    private ActivitiesActivity mDisplayAdapter;
    private List<Forecast> mList;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.activitiesTextView) TextView mActivitiesTextView;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.recyclerView) RecyclerView mRecyclerView;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.viewProfileButton) Button mViewProfileButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activities);
        ButterKnife.bind(this);
        mViewProfileButton.setOnClickListener(this);

        YahooWeatherApi client = YahooWeatherClient.getClient();
        Call<YahooWeatherLocationSearchResponse> call = client.getWeather(getIntent().getStringExtra("location"));

        call.enqueue(new Callback<YahooWeatherLocationSearchResponse>() {
            @Override
            public void onResponse(Call<YahooWeatherLocationSearchResponse> call, Response<YahooWeatherLocationSearchResponse> response) {
                if (response.isSuccessful()) {
                    mList = response.body().getForecasts();

                    WeatherDisplayAdapter displayAdapter = new WeatherDisplayAdapter(ActivitiesActivity.this, mList);
                    mRecyclerView.setAdapter(displayAdapter);
                    mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

                }
            }

            @Override
            public void onFailure(Call<YahooWeatherLocationSearchResponse> call, Throwable t) {

            }
        });
    }

    @Override
    public void onClick(View v){
        if (v == mViewProfileButton) {
            Intent intent = new Intent(ActivitiesActivity.this, ProfileActivity.class);
            startActivity(intent);
        }
    }
}
