package com.drusade.outdoorsie.ui.fragments;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.drusade.outdoorsie.R;
import com.drusade.outdoorsie.ui.ProfileActivity;
import com.drusade.outdoorsie.ui.SavedActivitiesActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyProfileFragment extends DialogFragment {

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.viewSavedButton) Button mViewSavedButton;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.myProfilePic) ImageView mMyProfilePic;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.myProfileName) TextView mMyProfileName;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_profile, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);

        mViewSavedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SavedActivitiesActivity.class);
                ((ProfileActivity) getActivity()).startActivity(intent);
            }
        });
    }
}