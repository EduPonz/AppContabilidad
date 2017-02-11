package com.example.eduponz.appcontabilidad.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by eduponz on 11/02/2017.
 */

public class AccountContract {

    private AccountContract () {}

    public static final String CONTENT_AUTHORITY = "com.example.android.account";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_EXPENSES = "expenses";
    public static final String PATH_BUDGET = "budget";

    public static abstract class ExpenseEntry implements BaseColumns {

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_EXPENSES);

        // The MIME type of the {@link #CONTENT_URI} for a list of pets.
        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_EXPENSES;

        // The MIME type of the {@link #CONTENT_URI} for a single pet.
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_EXPENSES;

        public static final String TABLE_NAME = "expenses";
        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_CONCEPT = "concept";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_PAID_WITH = "paid_with";
        public static final String COLUMN_DATE = "date";
        public static final String COLUMN_CURRENCY = "currency";
        public static final String COLUMN_QUANTITY = "quantity";


        // Possible values for the Currency.
        public static final int CURRENCY_DOLLAR = 0;
        public static final int CURRENCY_EURO = 1;
        public static final int CURRENCY_POUND = 2;
        public static final int CURRENCY_DKK = 3;

        public static boolean isValidCurrency(int currency) {
            if (currency == CURRENCY_DOLLAR || currency == CURRENCY_EURO
                    || currency == CURRENCY_POUND || currency == CURRENCY_DKK) {
                return true;
            }else {
                return false;
            }
        }
    }

    public static abstract class BudgetEntry implements BaseColumns {

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_BUDGET);

        // The MIME type of the {@link #CONTENT_URI} for a list of pets.
        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_BUDGET;

        // The MIME type of the {@link #CONTENT_URI} for a single pet.
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_BUDGET;

        public static final String TABLE_NAME = "budget";
        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_CONCEPT = "concept";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_CURRENCY = "currency";
        public static final String COLUMN_QUANTITY = "quantity";


        // Possible values for the Currency.
        public static final int CURRENCY_DOLLAR = 0;
        public static final int CURRENCY_EURO = 1;
        public static final int CURRENCY_POUND = 2;
        public static final int CURRENCY_DKK = 3;

        public static boolean isValidCurrency(int currency) {
            if (currency == CURRENCY_DOLLAR || currency == CURRENCY_EURO
                    || currency == CURRENCY_POUND || currency == CURRENCY_DKK) {
                return true;
            }else {
                return false;
            }
        }
    }

}