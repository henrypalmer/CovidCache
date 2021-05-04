package com.henryrpalmer.lewisu.covidcache;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CheckBox;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Date;

public class DetailActivity extends AppCompatActivity {

    private Tracker mTracker;
    private CalendarView mCalendarView;
    private CheckBox mFeverCheck;
    private CheckBox mCoughCheck;
    private CheckBox mFatigueCheck;
    private CheckBox mMuscleAchesCheck;
    private CheckBox mLossCheck;
    private Button submitButton;


    ArrayList<String> mSymptoms = new ArrayList<String>();

    private FirebaseDatabase mDb;
    private DatabaseReference mRef;
    private Date mDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity);

        mDb = FirebaseDatabase.getInstance();
        mRef = mDb.getReference("tracker");


        // initialize the instance of the tracker and also a dummy Date object that we can assign
        // values to from the CalendarView
        mTracker = new Tracker();
        mDate = new Date();


        mCalendarView = findViewById(R.id.calendar_selection);
        // ensure no time travelers getting exposed in the future
        mCalendarView.setMaxDate(System.currentTimeMillis());
        mCalendarView.setOnDateChangeListener( new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                mDate.setYear(year);
                mDate.setMonth(month);
                mDate.setDate(dayOfMonth);
                mTracker.setStartDate(mDate);
            }
        });

//        symptoms checkboxes
        mFeverCheck = findViewById(R.id.fever);
        mCoughCheck = findViewById(R.id.cough);
        mFatigueCheck = findViewById(R.id.fatigue);
        mMuscleAchesCheck = findViewById(R.id.muscle_aches);
        mLossCheck = findViewById(R.id.loss);


        submitButton = findViewById(R.id.submit_button);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                need to add symptoms to the tracker instance first
                addSymptoms();
                mTracker.setSymptoms(mSymptoms);
                // set start date
                mTracker.setStartDate(mDate);
                // set two week
                mTracker.setTwoWeek();
                // set three day
                mTracker.setThreeDay();
                mRef.push().setValue(mTracker);
                finish();
            }
        });
    }

    private void addSymptoms() {
        if (mFeverCheck.isChecked()) {
            mSymptoms.add("Fever");
        }
        if (mCoughCheck.isChecked()) {
            mSymptoms.add("Cough");
        }
        if (mFatigueCheck.isChecked()) {
            mSymptoms.add("Fatigue");
        }
        if (mMuscleAchesCheck.isChecked()) {
            mSymptoms.add("Muscle aches");
        }
        if (mLossCheck.isChecked()) {
            mSymptoms.add("Loss of taste or smell");
        }
    }


}