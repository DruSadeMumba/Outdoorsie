package com.drusade.outdoorsie.ui.fragments;


import static android.app.Activity.RESULT_OK;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

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

    private Uri imageUri;
    private FirebaseStorage mFirebaseStorage;
    private StorageReference mStorageReference;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_profile, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);
        mFirebaseStorage = FirebaseStorage.getInstance();
        mStorageReference = mFirebaseStorage.getReference();

        mViewSavedButton.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), SavedActivitiesActivity.class);
            ((ProfileActivity) getActivity()).startActivity(intent);
        });

        mMyProfilePic.setOnClickListener(v -> {
            choosePicture();
        });

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener(){
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth){
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    mMyProfileName.setText(user.getDisplayName());
                    mMyProfileEmail.setText(user.getEmail());
                } 
                else {

                }
            }
        };
    }

    private void choosePicture() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null){
            imageUri = data.getData();
            mMyProfilePic.setImageURI(imageUri);
            uploadPic();
        }
    }

    private void uploadPic() {

        final String randomKey = UUID.randomUUID().toString();
        StorageReference mountainsRef = mStorageReference.child("images/" + randomKey);

        mountainsRef.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });

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