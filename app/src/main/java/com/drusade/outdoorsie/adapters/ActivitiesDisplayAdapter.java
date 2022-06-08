package com.drusade.outdoorsie.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.drusade.outdoorsie.R;
import com.drusade.outdoorsie.models.Forecast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ActivitiesDisplayAdapter extends RecyclerView.Adapter<ActivitiesDisplayAdapter.DisplayViewHolder>{

    private Context mContext;
    private List<Forecast> weatherList;


    public ActivitiesDisplayAdapter(Context mContext, List<Forecast> weatherList) {
        this.mContext = mContext;
        this.weatherList = weatherList;
    }

    @NonNull
    @Override
    public ActivitiesDisplayAdapter.DisplayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.displayrecyclerview, parent, false);
        DisplayViewHolder displayViewHolder = new DisplayViewHolder(view);

        return displayViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ActivitiesDisplayAdapter.DisplayViewHolder holder, int position) {
        holder.bindWeather(weatherList.get(position));

    }

    @Override
    public int getItemCount() {
        return weatherList.size();
    }

    public class DisplayViewHolder extends RecyclerView.ViewHolder {
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.temperature) TextView mTemeperature;

        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.day) TextView mDay;

        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.text) TextView mText;

        public DisplayViewHolder(@NonNull View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
            mContext = itemView.getContext();
        }
        public void bindWeather(Forecast weather){
            mTemeperature.setText(Integer.toString(weather.getHigh()));
            mDay.setText(weather.getDay());
            mText.setText(weather.getText());
        }
    }

}
