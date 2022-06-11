package com.drusade.outdoorsie.ui;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.drusade.outdoorsie.Constants;
import com.drusade.outdoorsie.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddActivity extends AppCompatActivity implements View.OnClickListener{

    String [] activityList = {"Camping", "Biking", "Hiking", "Picnic", "Swimming", "Boating", "Music Festival"};
    ArrayAdapter<String> adapterActivity;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.autoCompleteTextActivityName) AutoCompleteTextView mAutoCompleteTextActivityName;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.editTextLocationName) EditText mEditTextLocationName;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.addLocationButton) Button mAddLocationButton;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.datePickerButton) Button mDatePickerButton;

    private DatePickerDialog datePickerDialog;
    private Button dateButton;
    private DatabaseReference mTypedLocationReference;
    private ValueEventListener mTypedLocationReferenceListener;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        mTypedLocationReference = FirebaseDatabase
                .getInstance()
                .getReference()
                .child(Constants.FIREBASE_CHILD_TYPED_LOCATION);

        mTypedLocationReference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot locationSnapshot : dataSnapshot.getChildren()) {
                    String location = locationSnapshot.getValue().toString();
                    Log.d("Locations updated", "location: " + location);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        ButterKnife.bind(this);

        adapterActivity = new ArrayAdapter<String>(this, R.layout.activities_selection_list_item, activityList);
        mAutoCompleteTextActivityName.setAdapter(adapterActivity);

        initDatePicker();
        dateButton = findViewById(R.id.datePickerButton);
        dateButton.setText(getTodaysDate());

        mAddLocationButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == mAddLocationButton) {
            String location = mEditTextLocationName.getText().toString();
            String activityName = mAutoCompleteTextActivityName.getText().toString();

            saveLocationToFirebase(location);

            Intent intent = new Intent(AddActivity.this, ActivitiesActivity.class);
            intent.putExtra("activityName", activityName);
            intent.putExtra("location", location);
            startActivity(intent);
        }
    }

    public void saveLocationToFirebase(String location){
        mTypedLocationReference.push().setValue(location);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mTypedLocationReference.removeEventListener(mTypedLocationReferenceListener);
    }

    private String getTodaysDate()
    {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }

    private void initDatePicker()
    {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day)
            {
                month = month + 1;
                String date = makeDateString(day, month, year);
                dateButton.setText(date);
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
    }

    private String makeDateString(int day, int month, int year)
    {
        return getMonthFormat(month) + " " + day + " " + year;
    }

    private String getMonthFormat(int month)
    {
        if(month == 1)
            return "JAN";
        if(month == 2)
            return "FEB";
        if(month == 3)
            return "MAR";
        if(month == 4)
            return "APR";
        if(month == 5)
            return "MAY";
        if(month == 6)
            return "JUN";
        if(month == 7)
            return "JUL";
        if(month == 8)
            return "AUG";
        if(month == 9)
            return "SEP";
        if(month == 10)
            return "OCT";
        if(month == 11)
            return "NOV";
        if(month == 12)
            return "DEC";

        return "JAN";
    }

    public void openDatePicker(View view)
    {
        datePickerDialog.show();
    }
}

