package com.henryrpalmer.lewisu.covidcache;

import com.google.firebase.database.Exclude;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;

public class Tracker {

    @Exclude
    private Date startDate;
    @Exclude
    private Date twoWeek;
    @Exclude
    private Date threeDay;
    @Exclude
    private ArrayList<String> symptoms;

    public Tracker() {
        this.startDate = new Date();
    }

    // start date
    @Exclude
    public String getStartDate() {
        return startDate.toString();
    }
    @Exclude
    public void setStartDate(Date date) {
        this.startDate = date;
        setStartDateString();
    }
    // two week
    @Exclude
    public String getTwoWeek() {
        return twoWeek.toString();
    }
    @Exclude
    public void setTwoWeek() {
        this.twoWeek = new Date(startDate.getTime() + 86400000*14);
        setTwoWeekString();
    }
    //three day
    @Exclude
    public String getThreeDay() {
        return threeDay.toString();
    }
    @Exclude
    public void setThreeDay() {
        this.threeDay = new Date(startDate.getTime() + 86400000*3);
        setThreeDayString();
    }
    //symptoms
    @Exclude
    public void setSymptoms(ArrayList<String> symps) {
        this.symptoms = symps;
        setSymptomsString();
    }
    @Exclude
    public String getSymptoms() {
        if (symptoms == null) {
            return "No symptoms reported";
        }
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < symptoms.size(); i++) {
            builder.append(symptoms.get(i));
            if (i == symptoms.size()-1) {
                builder.append(".");
            } else {
                builder.append(", ");
            }
        }
        return builder.toString();

    }

    // VALUES TO PUSH TO REALTIME DATABASE
    private String startDateString;
    private String twoWeekString;
    private String threeDayString;
    private String symptomsString;

    public String getStartDateString() {
        return startDateString;
    }

    public void setStartDateString() {
        this.startDateString = startDate.toString();
    }

    public String getTwoWeekString() {
        return twoWeekString;
    }

    public void setTwoWeekString() {
        this.twoWeekString = twoWeek.toString();
    }

    public String getThreeDayString() {
        return threeDayString;
    }

    public void setThreeDayString() {
        this.threeDayString = threeDay.toString();
    }

    public String getSymptomsString() {
        return symptomsString;
    }

    public void setSymptomsString() {
        this.symptomsString = getSymptoms();
    }
}
