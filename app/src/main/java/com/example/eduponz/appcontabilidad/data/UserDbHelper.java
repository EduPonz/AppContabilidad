package com.example.eduponz.appcontabilidad.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.eduponz.appcontabilidad.data.UserContract.UserEntry;


/**
 * Created by eduponz on 12/02/2017.
 */

public class UserDbHelper extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "users.db";
    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + UserEntry.TABLE_NAME;

    public UserDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {

        /******************************* USERS TABLE *************************************/
        String SQL_CREATE_EXPENSES_TABLE = "CREATE TABLE " + UserEntry.TABLE_NAME + " ("
                + UserEntry._ID + " INTEGER PRIMARY KEY,"
                + UserEntry.COLUMN_USER + " STRING NOT NULL,"
                + UserEntry.COLUMN_PASSWORD + " STRING NOT NULL)";

        db.execSQL(SQL_CREATE_EXPENSES_TABLE);
        /******************************* USERS TABLE *************************************/

    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
