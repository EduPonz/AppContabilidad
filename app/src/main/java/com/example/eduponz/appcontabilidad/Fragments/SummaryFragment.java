package com.example.eduponz.appcontabilidad.Fragments;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.eduponz.appcontabilidad.AddEntryActivity;
import com.example.eduponz.appcontabilidad.ExpenseCursorAdapter;
import com.example.eduponz.appcontabilidad.MainContentActivity;
import com.example.eduponz.appcontabilidad.R;
import com.example.eduponz.appcontabilidad.data.AccountContract.ExpenseEntry;

public class SummaryFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    ExpenseCursorAdapter expenseCursorAdapter;
    boolean emptyActivity = true;
    private static final int ENTRY_LOADER = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_summary, container, false);

        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.summary_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddEntryActivity.class);
                startActivity(intent);
            }
        });

        final ListView summaryList;

        summaryList = (ListView) rootView.findViewById(R.id.summary_listView);

        View emptyView = rootView.findViewById(R.id.summary_empty_view);
        summaryList.setEmptyView(emptyView);

        expenseCursorAdapter = new ExpenseCursorAdapter(getContext(), null);
        summaryList.setAdapter(expenseCursorAdapter);

        // Kickoff the Loader
        getLoaderManager().initLoader(ENTRY_LOADER, null, this);

        return rootView;
    }

    public void insertExpense() {
        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(ExpenseEntry.COLUMN_USER, "Edu");
        values.put(ExpenseEntry.COLUMN_EXPENSE_INCOME, 1);
        values.put(ExpenseEntry.COLUMN_CONCEPT, "Dummy Concept");
        values.put(ExpenseEntry.COLUMN_DESCRIPTION, "Dummy Description");
        values.put(ExpenseEntry.COLUMN_PAID_WITH, "Edu's Account");
        values.put(ExpenseEntry.COLUMN_DATE, "1/1/1970");
        values.put(ExpenseEntry.COLUMN_CURRENCY, "EUR");
        values.put(ExpenseEntry.COLUMN_QUANTITY, 80);

        // Insert the new row returning the primary key value of the new row (id)
        Context applicationContext = MainContentActivity.getContextOfApplication();
        Uri newUri = applicationContext.getContentResolver().insert(ExpenseEntry.CONTENT_URI, values);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        String [] projection = {
                ExpenseEntry._ID,
                ExpenseEntry.COLUMN_DATE,
                ExpenseEntry.COLUMN_CONCEPT,
                ExpenseEntry.COLUMN_QUANTITY,
                ExpenseEntry.COLUMN_CURRENCY,
                ExpenseEntry.COLUMN_EXPENSE_INCOME
        };

        // The loader will execute the ContentProvider's query method on a background thread
        return new CursorLoader(getContext(),   // Parent activity context
                ExpenseEntry.CONTENT_URI,       // Provider content URI to query
                projection,                     // Columns to query
                null,                           // Selection clause
                null,                           // Selection arguments
                null);                          // Sort Order

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        // Update the {@link PetCursorAdapter} with this new cursor containing updated data
        expenseCursorAdapter.swapCursor(data);
        if (expenseCursorAdapter.getCount() != 0) {
            emptyActivity = false;
        } else {
            emptyActivity = true;
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // Callback called when the data needs to be deleted
        expenseCursorAdapter.swapCursor(null);

    }
}
