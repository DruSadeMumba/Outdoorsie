package com.drusade.outdoorsie.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
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
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.drusade.outdoorsie.Constants;
import com.drusade.outdoorsie.R;
import com.drusade.outdoorsie.models.AnActivity;
import com.drusade.outdoorsie.models.AnActivityResponse;
import com.drusade.outdoorsie.ui.ActivitiesDetailActivity;
import com.drusade.outdoorsie.ui.AddActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ActivitiesListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Filterable {

    private Context context;
    List<AnActivity> activities;
    List<AnActivity> activitiesFull;

    public ActivitiesListAdapter (Context ctx, List<AnActivity> activities) {
        this.activities = activities;
        activitiesFull = new ArrayList<>();
        activitiesFull.addAll(activities);
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
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        AnActivity e = activities.get(position);
        FirebaseActivityViewHolder vh = (FirebaseActivityViewHolder) holder;
        AnActivity anAct = e==null? activities.get(position):e;

        ((FirebaseActivityViewHolder) holder).bindActivities(activities.get(position));

        vh.mViewActivityDetailsButton.setOnClickListener(v -> {
            String location = vh.mLocationTextView.getText().toString();
            String activityName = vh.mActivityTextView.getText().toString();
            Intent intent = new Intent(context, ActivitiesDetailActivity.class);
            intent.putExtra("activityName", activityName);
            intent.putExtra("location", location);
            context.startActivity(intent);
        });

        vh.mSaveActivityButton.setOnClickListener(v -> {
            AnActivity mAnActivity = new AnActivity(vh.mActivityTextView.getText().toString(), vh.mLocationTextView.getText().toString(), vh.mDateTextView.getAutofillType());
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            assert user != null;
            String uid = user.getUid();
            DatabaseReference activityRef = FirebaseDatabase
                    .getInstance()
                    .getReference(Constants.FIREBASE_CHILD_ACTIVITIES)
                    .child(uid);

            DatabaseReference pushRef = activityRef.push();

            String pushId = pushRef.getKey();
            mAnActivity.setPushId(pushId);
            pushRef.setValue(mAnActivity);
            Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show();
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
                    if (item.getActivityName().toLowerCase().contains(filterPattern.toLowerCase())) {
                        filteredList.add(item);
                    }
                    if (item.getLocation().toLowerCase().contains(filterPattern.toLowerCase())) {
                        filteredList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;
            Log.e("VALUES", results.values.toString());

            return results;
        }

        @SuppressLint("NotifyDataSetChanged")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            activities.clear();
            activities.addAll((Collection<? extends AnActivity>) results.values);
            /*activities.addAll((List) results.values);*/
            notifyDataSetChanged();
        }
    };
}

