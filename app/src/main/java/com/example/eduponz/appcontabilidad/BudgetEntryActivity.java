package com.example.eduponz.appcontabilidad;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

public class BudgetEntryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget_entry);

        /****************************** TOOLBAR **********************************/
        final Toolbar toolbar = (Toolbar) findViewById(R.id.budget_entry_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        /****************************** TOOLBAR **********************************/
    }
}
