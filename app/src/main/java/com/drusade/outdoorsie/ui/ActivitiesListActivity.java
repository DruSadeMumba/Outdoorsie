package com.drusade.outdoorsie.ui;

import static com.drusade.outdoorsie.R.*;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.SearchView;
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
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ActivitiesListActivity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView mRecyclerView;
    ActivitiesListAdapter adapter;
    AnActivityResponse response;
    boolean isLoading=false;
    String key =null;
    List<AnActivity> activities = new ArrayList<>();

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.viewActivityDetailsButton) Button mViewActivityDetailsButton;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.activitiesLocationListTextView) TextView mActivitiesLocationListTextView;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.activitiesNameListTextView) TextView mActivitiesNameListTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_activities_list);

        mRecyclerView = findViewById(id.recyclerView2);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(manager);
        adapter = new ActivitiesListAdapter(this, activities);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);

        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) menuItem.getActionView();

        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }


    @Override
    public void onClick(View v) {
        if (v == mViewActivityDetailsButton) {
            Intent intent = new Intent(ActivitiesListActivity.this, ActivitiesDetailActivity.class);
            startActivity(intent);
        }
    }
}
