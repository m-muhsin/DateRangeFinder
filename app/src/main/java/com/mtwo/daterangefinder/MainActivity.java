package com.mtwo.daterangefinder;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.FragmentManager;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private DateRange dateRange;
    Button btnCalculate;
    TextView txtFromDate, txtToDate, txtNoOfDays;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // find the retained fragment on activity restarts
        FragmentManager fm = getFragmentManager();
        dateRange = (DateRange) fm.findFragmentByTag("dateRange");

        // create the fragment and data the first time
        if (dateRange == null) {

            dateRange = new DateRange();
            fm.beginTransaction().add(dateRange, "dateRange").commit();

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM//dd");
            Calendar calFrom = Calendar.getInstance();
            Calendar calTo = Calendar.getInstance();

            try {

                calFrom.setTime(sdf.parse((String) txtFromDate.getText()));
                calTo.setTime(sdf.parse((String) txtToDate.getText()));
                dateRange.setFromDate(calFrom);
                dateRange.setToDate(calTo);

            } catch (ParseException | NullPointerException e) {}
        }

        showDialogOnTextEdit();
        startListener(dateRange);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void startListener(final DateRange dateRange) {
        txtNoOfDays = (TextView) findViewById(R.id.txtNoOfDays);
        btnCalculate = (Button) findViewById(R.id.btnCalculate);

        btnCalculate.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(dateRange.getFromDate() == null || dateRange.getToDate() == null)
                            Snackbar.make(txtNoOfDays, "Please select From and To dates", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        else {
                            txtNoOfDays.setText(dateRange.doCalculation());
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

    public void showDialogOnTextEdit() {
        txtFromDate = (TextView) findViewById(R.id.txtFromDate);
        txtFromDate.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showDialog(DateRange.FROM_DIALOG_ID);
                    }
                }
        );

        txtToDate = (TextView) findViewById(R.id.txtToDate);
        txtToDate.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showDialog(DateRange.TO_DIALOG_ID);
                    }
                }
        );
    }

    private DatePickerDialog.OnDateSetListener fromDatePickerListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            txtFromDate.setText(dateRange.setFromDate(year, monthOfYear, dayOfMonth));
        }
    };

    private DatePickerDialog.OnDateSetListener toDatePickerListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            txtToDate.setText(dateRange.setToDate(year, monthOfYear, dayOfMonth));
        }
    };

    @Override
    protected Dialog onCreateDialog(int id) {
        if(id == DateRange.FROM_DIALOG_ID)
            return new DatePickerDialog(this, fromDatePickerListener, dateRange.getYear_x(), dateRange.getMonth_x(), dateRange.getDay_x());

        if(id == DateRange.TO_DIALOG_ID)
            return new DatePickerDialog(this, toDatePickerListener, dateRange.getYear_x(), dateRange.getMonth_x(), dateRange.getDay_x());

        return null;
    }

}
