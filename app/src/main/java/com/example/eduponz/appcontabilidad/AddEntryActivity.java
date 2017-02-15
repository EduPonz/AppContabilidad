package com.example.eduponz.appcontabilidad;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.renderscript.Sampler;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eduponz.appcontabilidad.Fragments.DatePickerFragment;
import com.example.eduponz.appcontabilidad.Fragments.SummaryFragment;

import java.util.Calendar;
import java.util.Date;

public class AddEntryActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    private boolean validDate = false;
    String stringDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_entry);

        /****************************** TOOLBAR **********************************/
        final Toolbar toolbar = (Toolbar) findViewById(R.id.add_entry_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setTitle(R.string.add_expense_income);
        /****************************** TOOLBAR **********************************/

        if (MainContentActivity.tabPosition == MainContentActivity.TAB_BUDGET){
            View paid_with = findViewById(R.id.paid_with_linearLayout);
            paid_with.setVisibility(LinearLayout.GONE);
            View date = findViewById(R.id.date_linearLayout);
            date.setVisibility(LinearLayout.GONE);
            toolbar.setTitle(R.string.add_budget_entry);
            MainContentActivity.tabPosition = MainContentActivity.TAB_OVERVIEW;
        }

    }

    public void showDatePickerDialog(View view) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        TextView dateTextView = (TextView) findViewById(R.id.add_entry_date_textView);

        Calendar selectedDate = Calendar.getInstance();
        selectedDate.set(year, month, dayOfMonth);
        Calendar currentDate = Calendar.getInstance();

        if (currentDate.compareTo(selectedDate) < 0) {
            Toast.makeText(this, R.string.toast_wrong_selected_date,
                    Toast.LENGTH_SHORT).show();
        }else {
            validDate = true;
            invalidateOptionsMenu();
            stringDate = String.valueOf(dayOfMonth)
                    + "/" + String.valueOf(month + 1)
                    + "/" + String.valueOf(year);
            dateTextView.setText(stringDate);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_catalog.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.add_entry_menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        MenuItem actionSave = menu.findItem(R.id.action_save);
        if (!validDate) {
            actionSave.setVisible(false);
        }else{
            actionSave.setVisible(true);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            case R.id.action_save:
                if (validDate){
                    Toast.makeText(this, R.string.add_entry_saved,
                            Toast.LENGTH_SHORT).show();
                    finish();
                }else{
                    Toast.makeText(this, R.string.add_entry_unable_save,
                            Toast.LENGTH_SHORT).show();
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
