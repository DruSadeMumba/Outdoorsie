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
import com.drusade.outdoorsie.models.AnActivity;
import com.drusade.outdoorsie.models.AnActivityResponse;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddActivity extends AppCompatActivity implements View.OnClickListener {

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.datePickerButton) Button mDatePickerButton;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.autoCompleteTextActivityName) AutoCompleteTextView mAutoCompleteTextActivityName;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.editTextLocationName) EditText mEditTextLocationName;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.addActivityButton) Button mAddActivityButton;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.viewProfileButton) Button mViewProfileButton;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.viewActivitiesListButton) Button mViewActivitiesListButton;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.viewSavedButton) Button mViewSavedButton;

    private DatePickerDialog datePickerDialog;

    String [] activityList = {"Boating", "Biking", "Camping",
            "Cook Out", "Hiking", "Music Festival", "Picnic",
            "Sports", "Swimming", "Zip Lining"};

    ArrayAdapter<String> adapterActivity;

    @SuppressLint("CutPasteId")
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        ButterKnife.bind(this);

        adapterActivity = new ArrayAdapter<String>(this, R.layout.activities_selection_list_item, activityList);
        mAutoCompleteTextActivityName.setAdapter(adapterActivity);

        initDatePicker();

        mDatePickerButton.setText(getTodaysDate());adapterActivity = new ArrayAdapter<String>(this, R.layout.activities_selection_list_item, activityList);
        mAutoCompleteTextActivityName.setAdapter(adapterActivity);
        mViewProfileButton.setOnClickListener(this);
        mViewSavedButton.setOnClickListener(this);

        mViewActivitiesListButton.setOnClickListener(v-> {
            Intent intent =new Intent(AddActivity.this, ActivitiesListActivity.class);
            startActivity(intent);
        });

        AnActivityResponse response = new AnActivityResponse();
        AnActivity anAct_edit = (AnActivity)getIntent().getSerializableExtra("EDIT");
        if(anAct_edit !=null) {

            mAutoCompleteTextActivityName.setText(anAct_edit.getActivityName());
            mEditTextLocationName.setText(anAct_edit.getLocation());
            mDatePickerButton.setText(Math.toIntExact(anAct_edit.getDate()));
            mViewActivitiesListButton.setVisibility(View.GONE);
        }
        else {

            mViewActivitiesListButton.setVisibility(View.VISIBLE);
        }

        mAddActivityButton.setOnClickListener(v-> {
            AnActivity anAct = new AnActivity(mAutoCompleteTextActivityName.getText().toString(), mEditTextLocationName.getText().toString(), mDatePickerButton.getAutofillType());
            if(anAct_edit == null) {
                response.add(anAct).addOnSuccessListener(suc -> {
                    Toast.makeText(this, "Activity added!", Toast.LENGTH_SHORT).show();
                }).addOnFailureListener(er -> {
                    Toast.makeText(this, "" + er.getMessage(), Toast.LENGTH_SHORT).show();
                });
            }
            else {
                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("activityName", mAutoCompleteTextActivityName.getText().toString());
                hashMap.put("location", mEditTextLocationName.getText().toString());

                response.update(anAct_edit.getKeys(), hashMap).addOnSuccessListener(suc -> {
                    Toast.makeText(this, "Record is updated", Toast.LENGTH_SHORT).show();
                    finish();
                })
                        .addOnFailureListener(er -> {
                    Toast.makeText(this, "" + er.getMessage(), Toast.LENGTH_SHORT).show();
                });
            }
        });
    }

    @Override
    public void onClick(View v){
        if (v == mViewProfileButton) {
            Intent intent = new Intent(AddActivity.this, ProfileActivity.class);
            startActivity(intent);
        }
        if (v == mViewSavedButton){
            Intent intent = new Intent(AddActivity.this, SavedActivitiesActivity.class);
            startActivity(intent);
        }
    }

    //Date picker
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
                mDatePickerButton.setText(date);
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

