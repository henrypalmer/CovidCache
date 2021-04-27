package com.henryrpalmer.lewisu.covidcache;

import java.util.Date;

public class Tracker {
    private Date startDate;
    private Date twoWeek;
    private Date threeDay;

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
}
