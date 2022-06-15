package com.drusade.outdoorsie.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.drusade.outdoorsie.R;
import com.drusade.outdoorsie.models.AnActivity;
import com.drusade.outdoorsie.models.AnActivityResponse;
import com.drusade.outdoorsie.ui.ActivitiesDetailActivity;
import com.drusade.outdoorsie.ui.AddActivity;


public class FirebaseActivityViewHolder extends RecyclerView.ViewHolder {

    public TextView mActivityTextView, mLocationTextView, mTxt_option, mDateTextView;
    public Button mViewActivityDetailsButton, mSaveActivityButton;
    Context context;

    public FirebaseActivityViewHolder(@NonNull View itemView) {
        super(itemView);
        mActivityTextView = itemView.findViewById(R.id.activitiesNameListTextView);
        mLocationTextView = itemView.findViewById(R.id.activitiesLocationListTextView);
        mDateTextView = itemView.findViewById(R.id.activitiesDateListTextView);
        mViewActivityDetailsButton = itemView.findViewById(R.id.viewActivityDetailsButton);
        mSaveActivityButton = itemView.findViewById(R.id.saveActivityButton);
        mTxt_option = itemView.findViewById(R.id.txt_option);
    }

    public void bindActivities (AnActivity anAct) {
        mActivityTextView.setText(anAct.getActivityName());
        mLocationTextView.setText(anAct.getLocation());
    }
}
