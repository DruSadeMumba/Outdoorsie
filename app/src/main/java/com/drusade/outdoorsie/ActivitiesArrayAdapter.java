package com.drusade.outdoorsie;

import android.content.Context;
import android.widget.ArrayAdapter;

public class ActivitiesArrayAdapter extends ArrayAdapter {
    private Context mContext;
    private String[] mActivities;


    public ActivitiesArrayAdapter(Context mContext, int resource, String[] mActivities) {
        super(mContext, resource);
        this.mContext = mContext;
        this.mActivities = mActivities;

    }

    @Override
    public Object getItem(int position) {
        String activity = mActivities[position];
        return String.format("%s ", activity);
    }

    @Override
    public int getCount() {
        return mActivities.length;
    }
}
