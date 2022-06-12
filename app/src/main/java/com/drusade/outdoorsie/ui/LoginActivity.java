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
    @BindView(R.id.emailEditText) EditText mEmailEditText;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.passwordEditText) EditText mPasswordEditText;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.passwordLoginButton) Button mPasswordLoginButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        mPasswordLoginButton.setOnClickListener(this);
    }

      @Override
      public void onClick(View v) {


          if(v == mPasswordLoginButton) {
              String email = mEmailEditText.getText().toString();
              String password = mPasswordEditText.getText().toString();
              if (email.isEmpty() || password.isEmpty() ){
                  Toast.makeText(getApplicationContext(), "Please fill in all the fields", Toast.LENGTH_LONG).show();
              }
              else {
                  Intent intent = new Intent(LoginActivity.this, AddActivity.class);
                  intent.putExtra("email", email);
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
