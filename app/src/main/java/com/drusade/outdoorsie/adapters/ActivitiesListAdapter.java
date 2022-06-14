package com.drusade.outdoorsie.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.drusade.outdoorsie.R;
import com.drusade.outdoorsie.models.AnActivity;
import com.drusade.outdoorsie.models.AnActivityResponse;
import com.drusade.outdoorsie.ui.AddActivity;

import java.util.ArrayList;

public class ActivitiesListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    ArrayList<AnActivity> activities = new ArrayList<>();

    public ActivitiesListAdapter (Context ctx) {
        this.context = ctx;
    }

    public void setItems(ArrayList<AnActivity> anAct) {
        activities.addAll(anAct);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activities_list_recycler_view_item, parent,false);
        return new FirebaseActivityViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int location) {
        AnActivity e = null;
        this.onBindViewHolder(holder, location, e);
    }

    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int location, AnActivity e) {
        FirebaseActivityViewHolder vh = (FirebaseActivityViewHolder) holder;
        AnActivity anAct = e==null? activities.get(location):e;

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
                            notifyItemRemoved(location);
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
}

