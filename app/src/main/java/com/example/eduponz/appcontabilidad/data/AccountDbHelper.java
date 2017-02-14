package com.example.eduponz.appcontabilidad.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.eduponz.appcontabilidad.data.AccountContract.ExpenseEntry;
import com.example.eduponz.appcontabilidad.data.AccountContract.BudgetEntry;
import com.example.eduponz.appcontabilidad.data.AccountContract.ConceptEntry;
import com.example.eduponz.appcontabilidad.data.AccountContract.CurrencyEntry;

/**
 * Created by eduponz on 11/02/2017.
 */

public class AccountDbHelper extends  SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "expenses.db";

    private static final String SQL_DELETE_EXPENSES_ENTRIES = "DROP TABLE IF EXISTS "
            + ExpenseEntry.TABLE_NAME;
    private static final String SQL_DELETE_BUDGET_ENTRIES = "DROP TABLE IF EXISTS "
            + BudgetEntry.TABLE_NAME;
    private static final String SQL_DELETE_CONCEPT_ENTRIES = "DROP TABLE IF EXISTS "
            + ConceptEntry.TABLE_NAME;
    private static final String SQL_DELETE_CURRENCY_ENTRIES = "DROP TABLE IF EXISTS "
            + CurrencyEntry.TABLE_NAME;

    public AccountDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {

        /******************************* EXPENSES TABLE *************************************/
        String SQL_CREATE_EXPENSES_TABLE = "CREATE TABLE " + ExpenseEntry.TABLE_NAME + " ("
                + ExpenseEntry._ID + " INTEGER PRIMARY KEY,"
                + ExpenseEntry.COLUMN_USER + " TEXT NOT NULL,"
                + ExpenseEntry.COLUMN_EXPENSE_INCOME + " INTEGER NOT NULL,"
                + ExpenseEntry.COLUMN_CONCEPT + " TEXT NOT NULL,"
                + ExpenseEntry.COLUMN_DESCRIPTION + " TEXT,"
                + ExpenseEntry.COLUMN_PAID_WITH + " TEXT NOT NULL,"
                + ExpenseEntry.COLUMN_DATE + " TEXT NOT NULL,"
                + ExpenseEntry.COLUMN_CURRENCY + " TEXT NOT NULL,"
                + ExpenseEntry.COLUMN_QUANTITY + " INTEGER NOT NULL)";

        db.execSQL(SQL_CREATE_EXPENSES_TABLE);
        /******************************* EXPENSES TABLE *************************************/


        /********************************* BUDGET TABLE *************************************/
        String SQL_CREATE_BUDGET_TABLE = "CREATE TABLE " + BudgetEntry.TABLE_NAME + " ("
                + BudgetEntry._ID + " INTEGER PRIMARY KEY,"
                + BudgetEntry.COLUMN_USER + " TEXT NOT NULL,"
                + BudgetEntry.COLUMN_EXPENSE_INCOME + " INTEGER NOT NULL,"
                + BudgetEntry.COLUMN_CONCEPT + " TEXT NOT NULL,"
                + BudgetEntry.COLUMN_DESCRIPTION + " TEXT,"
                + BudgetEntry.COLUMN_CURRENCY + " TEXT NOT NULL,"
                + BudgetEntry.COLUMN_QUANTITY + " INTEGER NOT NULL)";

        db.execSQL(SQL_CREATE_BUDGET_TABLE);
        /********************************* BUDGET TABLE *************************************/


        /********************************* CONCEPT TABLE ************************************/
        String SQL_CREATE_CONCEPT_TABLE = "CREATE TABLE " + ConceptEntry.TABLE_NAME + " ("
                + ConceptEntry._ID + " INTEGER PRIMARY KEY,"
                + ConceptEntry.COLUMN_USER + " TEXT NOT NULL,"
                + ConceptEntry.COLUMN_CONCEPT + " TEXT NOT NULL,"
                + ConceptEntry.COLUMN_EXPENSE_INCOME + " INTEGER NOT NULL)";

        db.execSQL(SQL_CREATE_CONCEPT_TABLE);
        /********************************* CONCEPT TABLE ************************************/


        /********************************* CURRENCY TABLE ***********************************/
        String SQL_CREATE_CURRENCY_TABLE = "CREATE TABLE " + CurrencyEntry.TABLE_NAME + " ("
                + CurrencyEntry._ID + " INTEGER PRIMARY KEY,"
                + CurrencyEntry.COLUMN_USER + " TEXT NOT NULL,"
                + CurrencyEntry.COLUMN_CURRENCY + " TEXT NOT NULL,"
                + CurrencyEntry.COLUMN_EXCHANGE_RATE + " INTEGER NOT NULL)";

        db.execSQL(SQL_CREATE_CURRENCY_TABLE);
        /********************************* CURRENCY TABLE ***********************************/

    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
