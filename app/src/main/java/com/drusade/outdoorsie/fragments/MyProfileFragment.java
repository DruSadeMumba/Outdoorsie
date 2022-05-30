package com.drusade.outdoorsie.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.drusade.outdoorsie.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyProfileFragment extends Fragment {

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.myProfileFragmentText) TextView mMyProfileFragmentText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_profile, container, false);

        /*TextView myProfileFragmentTextView = view.findViewById(R.id.myProfileFragmentText);
        myProfileFragmentTextView.setText(this.getArguments().getString("username"));*/

        return view;
    }

}