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

import com.drusade.outdoorsie.R;
import com.drusade.outdoorsie.ui.ProfileActivity;
import com.drusade.outdoorsie.ui.SavedActivitiesActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyProfileFragment extends DialogFragment {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.viewSavedButton) Button mViewSavedButton;

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

        mViewSavedButton.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), SavedActivitiesActivity.class);
            ((ProfileActivity) getActivity()).startActivity(intent);
        });

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

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}