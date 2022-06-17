package com.drusade.outdoorsie.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.drusade.outdoorsie.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CreateAccountActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String TAG = CreateAccountActivity.class.getSimpleName();
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private String mName;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.createUserButton) Button mCreateUserButton;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.nameEditText) EditText mNameEditText;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.emailEditText) EditText mEmailEditText;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.passwordEditText) EditText mPasswordEditText;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.confirmPasswordEditText) EditText mConfirmPasswordEditText;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.loginTextView) TextView mLoginTextView;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.firebaseProgressBar) ProgressBar mSignInProgressBar;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.loadingTextView) TextView mLoadingSignUp;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.createAccountTitleTextView) TextView mCreateAccountTitleTextView;

    float v = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        ButterKnife.bind(this);

        mAuth = FirebaseAuth.getInstance();

        mLoginTextView.setOnClickListener(this);
        mCreateUserButton.setOnClickListener(this);

        createAuthStateListener();

        mCreateAccountTitleTextView.setTranslationX(300);
        mNameEditText.setTranslationX(300);
        mEmailEditText.setTranslationX(300);
        mPasswordEditText.setTranslationX(300);
        mConfirmPasswordEditText.setTranslationX(300);
        mCreateUserButton.setTranslationY(300);
        mLoginTextView.setTranslationY(300);

        mCreateAccountTitleTextView.setAlpha(v);
        mNameEditText.setAlpha(v);
        mEmailEditText.setAlpha(v);
        mPasswordEditText.setAlpha(v);
        mConfirmPasswordEditText.setAlpha(v);
        mCreateUserButton.setAlpha(v);
        mLoginTextView.setAlpha(v);

        mCreateAccountTitleTextView.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(400).start();
        mNameEditText.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(600).start();
        mEmailEditText.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(800).start();
        mPasswordEditText.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(1000).start();
        mConfirmPasswordEditText.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(1200).start();
        mCreateUserButton.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(1400).start();
        mLoginTextView.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(1500).start();
    }

    private void showProgressBar() {
        mSignInProgressBar.setVisibility(View.VISIBLE);
        mLoadingSignUp.setVisibility(View.VISIBLE);
        mLoadingSignUp.setText("Sign Up process in Progress");
    }

    private void hideProgressBar() {
        mSignInProgressBar.setVisibility(View.GONE);
        mLoadingSignUp.setVisibility(View.GONE);
    }

    private boolean isValidEmail(String email) {
        boolean isGoodEmail =
                (email != null && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches());
        if (!isGoodEmail) {
            mEmailEditText.setError("Please enter a valid email address");
            return false;
        }
        return isGoodEmail;
    }

    private boolean isValidName(String name) {
        if (name.equals("")) {
            mNameEditText.setError("Please enter your name");
            return false;
        }
        return true;
    }

    private boolean isValidPassword(String password, String confirmPassword) {
        if (password.length() < 6) {
            mPasswordEditText.setError("Please create a password containing at least 6 characters");
            return false;
        } else if (!password.equals(confirmPassword)) {
            mPasswordEditText.setError("Passwords do not match");
            return false;
        }
        return true;
    }

    private void createNewUser() {
        mName = mNameEditText.getText().toString().trim();
        final String email = mEmailEditText.getText().toString().trim();
        String password = mPasswordEditText.getText().toString().trim();
        String confirmPassword = mConfirmPasswordEditText.getText().toString().trim();

        boolean validEmail = isValidEmail(email);
        boolean validName = isValidName(mName);
        boolean validPassword = isValidPassword(password, confirmPassword);

        if (!validEmail || !validName || !validPassword) return;
        showProgressBar();

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                hideProgressBar();
                if (task.isSuccessful()) {
                    Log.d(TAG, "Authentication successful");
                    createFirebaseUserProfile(Objects.requireNonNull(task.getResult().getUser()));
                } else {
                    Toast.makeText(CreateAccountActivity.this, "Authentication failed.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void createAuthStateListener(){
        mAuthListener = firebaseAuth -> {
            final FirebaseUser user = firebaseAuth.getCurrentUser();
            if (user != null){
                Intent intent = new Intent(CreateAccountActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        };
    }

    private void createFirebaseUserProfile(final FirebaseUser user) {
        UserProfileChangeRequest addProfileName = new UserProfileChangeRequest.Builder()
                .setDisplayName(mName)
                .build();

        user.updateProfile(addProfileName).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Log.d(TAG, Objects.requireNonNull(user.getDisplayName()));
                    Toast.makeText(CreateAccountActivity.this, "The display name has been set", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public void onStart(){
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop(){
        super.onStop();
        if(mAuthListener != null){
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    @Override
    public void onClick(View view) {
        if (view == mLoginTextView) {
            Intent intent = new Intent(CreateAccountActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }
        if (view == mCreateUserButton) {
            createNewUser();
        }
    }
}