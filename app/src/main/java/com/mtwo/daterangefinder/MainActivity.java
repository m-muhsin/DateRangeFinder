package com.mtwo.daterangefinder;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    Button btnCalculate;
    int year_x, month_x, day_x;
    Calendar fromDate, toDate;
    long dateDifference;
    static final int FROM_DIALOG_ID = 0;
    static final int TO_DIALOG_ID = 1;
    TextView txtFromDate, txtToDate, txtNoOfDays;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Calendar calendar = Calendar.getInstance();
        year_x = calendar.get(Calendar.YEAR);
        month_x = calendar.get(Calendar.MONTH);
        day_x = calendar.get(Calendar.DAY_OF_MONTH);

        showFromDialogOnTextEdit();
        showToDialogOnTextEdit();

        doCalculation();

    }

    private void doCalculation() {
        txtNoOfDays = (TextView) findViewById(R.id.txtNoOfDays);

        btnCalculate = (Button) findViewById(R.id.btnCalculate);
        btnCalculate.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(toDate == null || fromDate == null)
                            Snackbar.make(txtNoOfDays, "Please select From and To dates", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        else {
                            dateDifference = toDate.getTimeInMillis() - fromDate.getTimeInMillis();
                            dateDifference = dateDifference / (24 * 60 * 60 * 1000);
                            String noOfDays = String.valueOf(dateDifference);
                            txtNoOfDays.setText(noOfDays);
                        }
                    }
                }
        );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void showFromDialogOnTextEdit() {
        txtFromDate = (TextView) findViewById(R.id.txtFromDate);
        txtFromDate.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showDialog(FROM_DIALOG_ID);
                    }
                }
        );
    }

    public void showToDialogOnTextEdit() {
        txtToDate = (TextView) findViewById(R.id.txtToDate);
        txtToDate.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showDialog(TO_DIALOG_ID);
                    }
                }
        );
    }

    private DatePickerDialog.OnDateSetListener fromDatePickerListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

            fromDate = Calendar.getInstance();
            fromDate.set(Calendar.YEAR, year);
            fromDate.set(Calendar.MONTH, monthOfYear);
            fromDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            txtFromDate.setText(year + "/" + (monthOfYear + 1) + "/" + dayOfMonth);
        }
    };

    private DatePickerDialog.OnDateSetListener toDatePickerListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

            toDate = Calendar.getInstance();
            toDate.set(Calendar.YEAR, year);
            toDate.set(Calendar.MONTH, monthOfYear);
            toDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            txtToDate.setText(year + "/" + (monthOfYear + 1) + "/" + dayOfMonth);
        }
    };

    @Override
    protected Dialog onCreateDialog(int id) {
        if(id == FROM_DIALOG_ID)
            return new DatePickerDialog(this, fromDatePickerListener, year_x, month_x, day_x);

        if(id == TO_DIALOG_ID)
            return new DatePickerDialog(this, toDatePickerListener, year_x, month_x, day_x);

        return null;
    }

}
