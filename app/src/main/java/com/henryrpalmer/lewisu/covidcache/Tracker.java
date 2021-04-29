package com.henryrpalmer.lewisu.covidcache;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;

public class Tracker {
    private Date startDate;
    private Date twoWeek;
    private Date threeDay;
    private ArrayList<String> symptoms;

    public Tracker() {
        this.startDate = new Date();
        this.twoWeek = new Date(startDate.getTime() + 86400000*14);
        this.threeDay = new Date(startDate.getTime() + 86400000*3);
    }

    public String getStartDate() {
        return startDate.toString();
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getTwoWeek() {
        return twoWeek.toString();
    }

    public void setTwoWeek(Date twoWeek) {
        this.twoWeek = twoWeek;
    }

    public String getThreeDay() {
        return threeDay.toString();
    }

    public void setThreeDay(Date threeDay) {
        this.threeDay = threeDay;
    }


    public void setSymptoms(ArrayList<String> symps) {
        this.symptoms = symps;
    }

    public String getSymptoms() {
        if (symptoms == null) {
            return "No symptoms reported";
        }
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < symptoms.size() - 1; i++) {
            builder.append(symptoms.get(i));
            builder.append(",");
        }
        return builder.toString();

    }

}
