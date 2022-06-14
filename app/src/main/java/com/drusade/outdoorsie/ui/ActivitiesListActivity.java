package com.drusade.outdoorsie.ui;

import static com.drusade.outdoorsie.R.*;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.drusade.outdoorsie.Constants;
import com.drusade.outdoorsie.R;
import com.drusade.outdoorsie.adapters.ActivitiesListAdapter;
import com.drusade.outdoorsie.adapters.FirebaseActivityViewHolder;
import com.drusade.outdoorsie.models.AnActivity;
import com.drusade.outdoorsie.models.AnActivityResponse;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ActivitiesListActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    ActivitiesListAdapter adapter;
    AnActivityResponse response;
    boolean isLoading=false;
    String key =null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_activities_list);

        mRecyclerView = findViewById(id.recyclerView2);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(manager);
        adapter = new ActivitiesListAdapter(this);
        mRecyclerView.setAdapter(adapter);
        response = new AnActivityResponse();
        loadData();
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int totalItem = linearLayoutManager.getItemCount();
                int lastVisible = linearLayoutManager.findLastCompletelyVisibleItemPosition();

                if(totalItem < lastVisible + 3) {
                    if(!isLoading) {
                        isLoading=true;
                        loadData();
                    }
                }
            }
        });
    }

    private void loadData() {

        response.get(key).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<AnActivity> anActs = new ArrayList<>();
                for (DataSnapshot data : snapshot.getChildren()) {
                    AnActivity anAct = data.getValue(AnActivity.class);
                    anAct.setKey(data.getKey());
                    anActs.add(anAct);
                    key = data.getKey();
                }
                adapter.setItems(anActs);
                adapter.notifyDataSetChanged();
                isLoading =false;

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}


/*mRecyclerView2.addOnScrollListener(new RecyclerView.OnScrollListener()
        {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy)
            {
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) mRecyclerView2.getLayoutManager();
                int totalItem = linearLayoutManager.getItemCount();
                int lastVisible = linearLayoutManager.findLastCompletelyVisibleItemPosition();
                if(totalItem< lastVisible+3)
                {
                    if(!isLoading)
                    {
                        isLoading=true;
                        loadData();
                    }
                }
            }
        });


        TextView mTextView;
    RecyclerView mRecyclerView2;
    ActivitiesListAdapter adapter;
    AnActivityResponse dao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_activities_list);
        ButterKnife.bind(this);

        mRecyclerView2 = findViewById(id.recyclerView2);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        mRecyclerView2.setLayoutManager(manager);
        adapter = new ActivitiesListAdapter(this);
        mRecyclerView2.setAdapter(adapter);
        dao = new AnActivityResponse();
        loadData();

    }
    private void loadData() {
        dao.getActivities().addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                ArrayList<AnActivity> activities = new ArrayList<>();

                for(DataSnapshot data : snapshot.getChildren()){
                   AnActivity anActivity = data.getValue(AnActivity.class);
                    activities.add(anActivity);
                }
                adapter.setItems(activities);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
        */