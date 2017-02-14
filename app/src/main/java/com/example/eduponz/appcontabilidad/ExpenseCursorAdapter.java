package com.example.eduponz.appcontabilidad;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.eduponz.appcontabilidad.data.AccountContract.ExpenseEntry;

/**
 * Created by eduponz on 14/02/2017.
 */

public class ExpenseCursorAdapter extends CursorAdapter {
    public ExpenseCursorAdapter(Context context, Cursor c) {
        super(context, c, 0 /* flags */);
    }

    /**
     * Makes a new blank list item view. No data is set (or bound) to the views yet.
     *
     * @param context app context
     * @param cursor  The cursor from which to get the data. The cursor is already
     *                moved to the correct position.
     * @param parent  The parent to which the new view is attached to
     * @return the newly created list item view.
     */
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.fragment_summary_list_item, parent, false);
    }

    /**
     * This method binds the entry data (in the current row pointed to by cursor) to the given
     * list item layout. For example, the name for the current entry can be set on the name TextView
     * in the list item layout.
     *
     * @param view    Existing view, returned earlier by newView() method
     * @param context app context
     * @param cursor  The cursor from which to get the data. The cursor is already moved to the
     *                correct row.
     */
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Find fields to populate in inflated template
        TextView dateTextView = (TextView) view.findViewById(R.id.fragment_summary_list_item_date_textView);
        TextView conceptTextView = (TextView) view.findViewById(R.id.fragment_summary_list_item_concept_textView);
        TextView quantityTextView = (TextView) view.findViewById(R.id.fragment_summary_list_item_quantity_textView);

        //Find the columns of entry attributes that we're interested in
        int dateColumnIndex = cursor.getColumnIndex(ExpenseEntry.COLUMN_DATE);
        int conceptColumnIndex = cursor.getColumnIndex(ExpenseEntry.COLUMN_CONCEPT);
        int quantityColumnIndex = cursor.getColumnIndex(ExpenseEntry.COLUMN_QUANTITY);
        int currencyColumnIndex = cursor.getColumnIndex(ExpenseEntry.COLUMN_CURRENCY);

        // Extract properties from cursor
        String entryDate = cursor.getString(dateColumnIndex);
        String entryConcept = cursor.getString(conceptColumnIndex);
        String entryQuantity = cursor.getString(quantityColumnIndex);
        String entryCurrency = cursor.getString(currencyColumnIndex);

        String quantityCurrency = new StringBuilder().append(entryQuantity)
                .append(" ")
                .append(entryCurrency).toString();

        // Populate fields with extracted properties
        dateTextView.setText(entryDate);
        conceptTextView.setText(entryConcept);
        quantityTextView.setText(quantityCurrency);
    }
}

