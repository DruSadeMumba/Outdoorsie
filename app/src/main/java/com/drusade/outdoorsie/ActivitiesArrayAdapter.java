package com.drusade.outdoorsie;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

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



/*
*
    * BASEADAPTER for fragment
    * private Context mContext;
    private ArrayList <Integer> list;

    public ActivitiesArrayAdapter(Context mContext, ArrayList<Integer> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View listView;

        if(convertView == null){
            listView = layoutInflater.inflate(R.layout.fragment_myActivities, null);
            TextView number = listView.findViewById(R.id.activitiesListView);
            number.setText(list.get(position));
        } else {
            listView = convertView;
        }
        return listView;
    }*/