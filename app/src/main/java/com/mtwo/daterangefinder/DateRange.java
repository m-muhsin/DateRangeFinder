package com.mtwo.daterangefinder;

import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Calendar;

/**
 * Created by Muhammad on 12/9/2015.
 */
public class DateRange {
    private int year_x, month_x, day_x;
    private Calendar fromDate, toDate;
    private long dateDifference;
    static final int FROM_DIALOG_ID = 0, TO_DIALOG_ID = 1;

    DateRange() {
        final Calendar calendar = Calendar.getInstance();
        year_x = calendar.get(Calendar.YEAR);
        month_x = calendar.get(Calendar.MONTH);
        day_x = calendar.get(Calendar.DAY_OF_MONTH);
    }

    public String doCalculation() {
        dateDifference = toDate.getTimeInMillis() - fromDate.getTimeInMillis();
        dateDifference = dateDifference / (24 * 60 * 60 * 1000);
        String noOfDays = String.valueOf(dateDifference);
        return  noOfDays;
    }

    public Calendar getFromDate() {
        return fromDate;
    }

    public Calendar getToDate() {
        return toDate;
    }


    public String setFromDate(int year, int monthOfYear, int dayOfMonth) {
        fromDate = Calendar.getInstance();
        fromDate.set(Calendar.YEAR, year);
        fromDate.set(Calendar.MONTH, monthOfYear);
        fromDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        return (year + "/" + (monthOfYear + 1) + "/" + dayOfMonth);
    }

    public String setToDate(int year, int monthOfYear, int dayOfMonth) {
        toDate = Calendar.getInstance();
        toDate.set(Calendar.YEAR, year);
        toDate.set(Calendar.MONTH, monthOfYear);
        toDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        return (year + "/" + (monthOfYear + 1) + "/" + dayOfMonth);
    }

    public int getYear_x() {
        return year_x;
    }

    public int getMonth_x() {
        return month_x;
    }

    public int getDay_x() {
        return day_x;
    }
}
