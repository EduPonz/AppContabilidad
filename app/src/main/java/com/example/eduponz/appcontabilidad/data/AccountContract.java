package com.example.eduponz.appcontabilidad.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by eduponz on 11/02/2017.
 */

public class AccountContract {

    private AccountContract () {}

    public static final String CONTENT_AUTHORITY = "com.example.eduponz.appcontabilidad";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_EXPENSES = "expenses";
    public static final String PATH_BUDGET = "budget";
    public static final String PATH_CONCEPTS = "concepts";
    public static final String PATH_CURRENCY = "currency";

    public static abstract class ExpenseEntry implements BaseColumns {

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_EXPENSES);

        // The MIME type of the {@link #CONTENT_URI} for a list of expenses.
        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_EXPENSES;

        // The MIME type of the {@link #CONTENT_URI} for a single expense.
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_EXPENSES;

        public static final String TABLE_NAME = "expenses";
        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_USER = "user";
        public static final String COLUMN_CONCEPT = "concept";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_PAID_WITH = "paid_with";
        public static final String COLUMN_DATE = "date";
        public static final String COLUMN_CURRENCY = "currency";
        public static final String COLUMN_QUANTITY = "quantity";

    }

    public static abstract class BudgetEntry implements BaseColumns {

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_BUDGET);

        // The MIME type of the {@link #CONTENT_URI} for a list of budgets.
        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_BUDGET;

        // The MIME type of the {@link #CONTENT_URI} for a single budget.
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_BUDGET;

        public static final String TABLE_NAME = "budget";
        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_USER = "user";
        public static final String COLUMN_CONCEPT = "concept";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_CURRENCY = "currency";
        public static final String COLUMN_QUANTITY = "quantity";
    }

    public static abstract class ConceptEntry implements BaseColumns {

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_CONCEPTS);

        // The MIME type of the {@link #CONTENT_URI} for a list of concepts.
        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_CONCEPTS;

        // The MIME type of the {@link #CONTENT_URI} for a single concepts.
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_CONCEPTS;

        public static final String TABLE_NAME = "concepts";
        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_USER = "user";
        public static final String COLUMN_CONCEPT = "concept";
    }

    public static abstract class CurrencyEntry implements BaseColumns {

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_CURRENCY);

        // The MIME type of the {@link #CONTENT_URI} for a list of budgets.
        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_CURRENCY;

        // The MIME type of the {@link #CONTENT_URI} for a single budget.
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_CURRENCY;

        public static final String TABLE_NAME = "currency";
        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_USER = "user";
        public static final String COLUMN_CURRENCY = "currency";
        public static final String COLUMN_EXCHANGE_RATE = "exchange_rate";
    }

}