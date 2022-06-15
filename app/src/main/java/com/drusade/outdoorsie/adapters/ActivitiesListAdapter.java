package com.drusade.outdoorsie.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.drusade.outdoorsie.R;
import com.drusade.outdoorsie.models.AnActivity;
import com.drusade.outdoorsie.models.AnActivityResponse;
import com.drusade.outdoorsie.ui.ActivitiesDetailActivity;
import com.drusade.outdoorsie.ui.ActivitiesListActivity;
import com.drusade.outdoorsie.ui.AddActivity;
import com.drusade.outdoorsie.ui.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class ActivitiesListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Filterable {

    private Context context;
    List<AnActivity> activities = new ArrayList<>();
    List<AnActivity> activitiesFull = new ArrayList<>();

    public ActivitiesListAdapter (Context ctx, List<AnActivity> activities) {
        this.activities = activities;
        activitiesFull = new ArrayList<>(activities);
        this.context = ctx;
    }

    public void setItems(List<AnActivity> anAct) {
        activities.addAll(anAct);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activities_list_recycler_view_item, parent,false);
        return new FirebaseActivityViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        AnActivity e = activities.get(position);
        FirebaseActivityViewHolder vh = (FirebaseActivityViewHolder) holder;
        AnActivity anAct = e==null? activities.get(position):e;

        vh.mActivityTextView.setText(anAct.getActivityName());
        vh.mLocationTextView.setText(anAct.getLocation());

        vh.mViewActivityDetailsButton.setOnClickListener(v -> {
            String location = vh.mLocationTextView.getText().toString();
            String activityName = vh.mActivityTextView.getText().toString();
            Intent intent = new Intent(context, ActivitiesDetailActivity.class);
            intent.putExtra("activityName", activityName);
            intent.putExtra("location", location);
            context.startActivity(intent);
        });

        vh.mTxt_option.setOnClickListener(v-> {

            PopupMenu popupMenu =new PopupMenu(context,vh.mTxt_option);
            popupMenu.inflate(R.menu.option_menu);
            popupMenu.setOnMenuItemClickListener(item-> {

                switch (item.getItemId()) {

                    case R.id.menu_edit:
                        Intent intent = new Intent(context, AddActivity.class);
                        intent.putExtra("EDIT", (Parcelable) anAct);
                        context.startActivity(intent);
                        break;

                    case R.id.menu_remove:
                        AnActivityResponse response = new AnActivityResponse();
                        response.remove(anAct.getKey()).addOnSuccessListener(suc-> {
                            Toast.makeText(context, "Activity Removed", Toast.LENGTH_SHORT).show();
                            notifyItemRemoved(position);
                            activities.remove(anAct);
                        }).addOnFailureListener(er-> {
                            Toast.makeText(context, ""+er.getMessage(), Toast.LENGTH_SHORT).show();
                        });

                        break;
                }
                return false;
            });
            popupMenu.show();
        });
    }

    @Override
    public int getItemCount() {
        return activities.size();
    }

    @Override
    public Filter getFilter() {
        return activitiesFilter;
    }

    private Filter activitiesFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            Log.e("constraint", String.valueOf(activities.size()));
            List<AnActivity> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(activities);
            }
            else {
                String filterPattern = constraint.toString().toLowerCase();

                for (AnActivity item : activities) {
                    if (item.getActivityName().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                    if (item.getLocation().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;
            Log.e("VALUES", results.values.toString());

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            activities.clear();
            activities.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
}

