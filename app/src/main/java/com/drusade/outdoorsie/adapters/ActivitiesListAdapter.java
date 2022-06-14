package com.drusade.outdoorsie.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
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
import com.drusade.outdoorsie.ui.AddActivity;

import java.util.ArrayList;
import java.util.List;

public class ActivitiesListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Filterable {

    private Context context;
    List<AnActivity> activities = new ArrayList<>();
    List<AnActivity> activitiesFull = new ArrayList<>();

    public ActivitiesListAdapter (Context ctx) {
        this.activities = activities;
        activitiesFull = new ArrayList<>(activities);
        this.context = ctx;
    }

    public void setItems(ArrayList<AnActivity> anAct) {
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
        /*this.onBindViewHolder(holder, position, e);
    }

    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position, AnActivity e) {*/
        FirebaseActivityViewHolder vh = (FirebaseActivityViewHolder) holder;
        AnActivity anAct = e==null? activities.get(position):e;

        vh.mActivityTextView.setText(anAct.getActivityName());
        vh.mLocationTextView.setText(anAct.getLocation());
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
            List<AnActivity> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(activitiesFull);
            }
            else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (AnActivity item : activitiesFull) {
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

