package com.drusade.outdoorsie.ui.fragments;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.drusade.outdoorsie.Constants;
import com.drusade.outdoorsie.R;
import com.drusade.outdoorsie.adapters.FirebaseActivityViewHolder;
import com.drusade.outdoorsie.models.AnActivity;
import com.drusade.outdoorsie.ui.ProfileActivity;
import com.drusade.outdoorsie.ui.SavedActivitiesActivity;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyProfileFragment extends DialogFragment {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference mMyActivitiesReference;
    private FirebaseRecyclerAdapter<AnActivity, FirebaseActivityViewHolder> mFirebaseAdapter;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.recyclerView2) RecyclerView mRecyclerView2;

    /*@SuppressLint("NonConstantResourceId")
    @BindView(R.id.viewSavedButton) Button mViewSavedButton;*/

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.myProfilePic) ImageView mMyProfilePic;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.myProfileName) TextView mMyProfileName;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.myProfileEmail) TextView mMyProfileEmail;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_profile, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();

        mMyActivitiesReference = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_CHILD_ACTIVITIES).child(uid);
        SetUpFirebaseAdapter();
        showActivities();

        /*mViewSavedButton.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), SavedActivitiesActivity.class);
            ((ProfileActivity) getActivity()).startActivity(intent);
        });*/

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener(){
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth){
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    mMyProfileName.setText(user.getDisplayName());
                    mMyProfileEmail.setText(user.getEmail());
                    mMyProfilePic.setImageURI(user.getPhotoUrl());
                } 
                else {

                }
            }
        };
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


        mRecyclerView2.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView2.setAdapter(mFirebaseAdapter);
    }

    private void showActivities() {

        mRecyclerView2.setVisibility(View.VISIBLE);
    }

    @Override
    public void onStart() {
        super.onStart();
        if(mAuthListener != null) {
            mAuth.addAuthStateListener(mAuthListener);
        }
        if(mFirebaseAdapter!= null){
            mFirebaseAdapter.startListening();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
        if (mFirebaseAdapter!= null) {
            mFirebaseAdapter.stopListening();
        }
    }
}