package com.drusade.outdoorsie.adapters;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.drusade.outdoorsie.R;
import com.drusade.outdoorsie.models.AnActivity;


public class FirebaseActivityViewHolder extends RecyclerView.ViewHolder {

    public TextView mActivityTextView, mLocationTextView, mTxt_option, mDateTextView;
    public Button mViewActivityDetailsButton, mSaveActivityButton;

    public FirebaseActivityViewHolder(@NonNull View itemView) {
        super(itemView);
        mActivityTextView = itemView.findViewById(R.id.activitiesNameListTextView);
        mLocationTextView = itemView.findViewById(R.id.activitiesLocationListTextView);
        mDateTextView = itemView.findViewById(R.id.activitiesDateListTextView);
        mViewActivityDetailsButton = itemView.findViewById(R.id.viewActivityDetailsButton);
        mSaveActivityButton = itemView.findViewById(R.id.saveActivityButton);
        mTxt_option = itemView.findViewById(R.id.txt_option);
    }
}
