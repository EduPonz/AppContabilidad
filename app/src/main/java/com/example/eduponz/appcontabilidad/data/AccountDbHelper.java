package com.example.eduponz.appcontabilidad.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.eduponz.appcontabilidad.data.AccountContract.ExpenseEntry;
import com.example.eduponz.appcontabilidad.data.AccountContract.BudgetEntry;


/**
 * Created by eduponz on 11/02/2017.
 */

public class AccountDbHelper extends  SQLiteOpenHelper {
        // If you change the database schema, you must increment the database version.
        private static final int DATABASE_VERSION = 1;
        private static final String DATABASE_NAME = "expenses.db";
        private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + ExpenseEntry.TABLE_NAME;

        public AccountDbHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        public void onCreate(SQLiteDatabase db) {

            /******************************* EXPENSES TABLE *************************************/
            String SQL_CREATE_EXPENSES_TABLE = "CREATE TABLE " + ExpenseEntry.TABLE_NAME + " ("
                    + ExpenseEntry._ID + " INTEGER PRIMARY KEY,"
                    + ExpenseEntry.COLUMN_CONCEPT + " INTEGER NOT NULL,"
                    + ExpenseEntry.COLUMN_DESCRIPTION + " TEXT,"
                    + ExpenseEntry.COLUMN_PAID_WITH + " INTEGER NOT NULL,"
                    + ExpenseEntry.COLUMN_DATE + " INTEGER NOT NULL,"
                    + ExpenseEntry.COLUMN_CURRENCY + " INTEGER NOT NULL,"
                    + ExpenseEntry.COLUMN_QUANTITY + " INTEGER NOT NULL)";

            db.execSQL(SQL_CREATE_EXPENSES_TABLE);
            /******************************* EXPENSES TABLE *************************************/


            /********************************* BUDGET TABLE *************************************/
            String SQL_CREATE_BUDGET_TABLE = "CREATE TABLE " + BudgetEntry.TABLE_NAME + " ("
                    + ExpenseEntry._ID + " INTEGER PRIMARY KEY,"
                    + ExpenseEntry.COLUMN_CONCEPT + " INTEGER NOT NULL,"
                    + ExpenseEntry.COLUMN_DESCRIPTION + " TEXT,"
                    + ExpenseEntry.COLUMN_CURRENCY + " INTEGER NOT NULL,"
                    + ExpenseEntry.COLUMN_QUANTITY + " INTEGER NOT NULL)";

            db.execSQL(SQL_CREATE_BUDGET_TABLE);
            /********************************* BUDGET TABLE *************************************/

        }

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + ExpenseEntry.TABLE_NAME);
        }
}
