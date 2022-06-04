  package com.drusade.outdoorsie.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.drusade.outdoorsie.R;

import butterknife.BindView;
import butterknife.ButterKnife;

  public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.password) EditText mPassword;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.username) EditText mUsername;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.signInButton) Button mSignInButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        mSignInButton.setOnClickListener(this);
    }

      @Override
      public void onClick(View v) {


          if(v == mSignInButton) {
              String username = mUsername.getText().toString();
              String password = mPassword.getText().toString();
              if (username.isEmpty() || password.isEmpty() ){
                  Toast.makeText(getApplicationContext(), "Please fill in all the fields", Toast.LENGTH_LONG).show();
              }
              else {
                  Intent intent = new Intent(LoginActivity.this, ActivitiesActivity.class);
                  intent.putExtra("username", username);
                  intent.putExtra("password", password);
                  startActivity(intent);
              }
          }
      }
  }






  /*FragmentManager fragmentManager = getSupportFragmentManager();
                  FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                  Bundle bundle = new Bundle();
                  bundle.putString("username", username);
                  MyProfileFragment myProfileFragment = new MyProfileFragment();
                  myProfileFragment.setArguments(bundle);*/
