package com.drusade.outdoorsie.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.drusade.outdoorsie.Constants;
import com.drusade.outdoorsie.R;
import com.drusade.outdoorsie.adapters.FirebaseActivityViewHolder;
import com.drusade.outdoorsie.models.AnActivity;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SavedActivitiesActivity extends AppCompatActivity {

    private DatabaseReference mMyActivitiesReference;
    private FirebaseRecyclerAdapter<AnActivity, FirebaseActivityViewHolder> mFirebaseAdapter;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.recyclerView2) RecyclerView mRecyclerView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activities_list);
        ButterKnife.bind(this);

        Objects.requireNonNull(getSupportActionBar()).setTitle("My Saved Activities");

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();

        mMyActivitiesReference = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_CHILD_ACTIVITIES).child(uid);
        SetUpFirebaseAdapter();
        showActivities();

    }

    private void SetUpFirebaseAdapter() {
        FirebaseRecyclerOptions<AnActivity> options =
             new FirebaseRecyclerOptions.Builder<AnActivity>()
                     .setQuery(mMyActivitiesReference, AnActivity.class)
                     .build();

        mFirebaseAdapter = new FirebaseRecyclerAdapter<AnActivity, FirebaseActivityViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull FirebaseActivityViewHolder firebaseActivityViewHolder, int position, @NonNull AnActivity anAct) {
                firebaseActivityViewHolder.bindActivities(anAct);
            }

            @NonNull
            @Override
            public FirebaseActivityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activities_list_recycler_view_item, parent, false);
                return new FirebaseActivityViewHolder(view);
            }
        };

        mRecyclerView2.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView2.setAdapter(mFirebaseAdapter);
    }

    private void showActivities() {
        mRecyclerView2.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mFirebaseAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mFirebaseAdapter!= null) {
            mFirebaseAdapter.stopListening();
        }
    }
}