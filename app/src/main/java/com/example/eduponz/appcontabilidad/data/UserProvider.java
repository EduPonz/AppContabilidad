package com.example.eduponz.appcontabilidad.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;
import com.example.eduponz.appcontabilidad.data.UserContract.UserEntry;

/**
 * Created by eduponz on 12/02/2017.
 */

public class UserProvider extends ContentProvider {

    private static final int USERS = 100;
    private static final int USERS_ID = 101;
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriMatcher.addURI(UserContract.CONTENT_AUTHORITY, UserContract.PATH_USERS, USERS);
        sUriMatcher.addURI(UserContract.CONTENT_AUTHORITY, UserContract.PATH_USERS + "/#", USERS_ID);
    }

    // Tag for the log messages
    public static final String LOG_TAG = UserProvider.class.getSimpleName();
    private UserDbHelper mDbHelper;


    // Initialize the provider and the database helper object.
    @Override
    public boolean onCreate() {
        mDbHelper = new UserDbHelper(getContext());
        return true;
    }

    //Perform the query for the given URI.
    // SQL --> SELECT projection FROM PetEntry.TABLE_NAME WHERE selection=selectionArgs ORDER BY sortOrder
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {

        SQLiteDatabase database = mDbHelper.getReadableDatabase();
        Cursor cursor;

        // Figure out if the URI matcher can match the URI to a specific code
        int match = sUriMatcher.match(uri);
        switch (match) {
            case USERS:
                cursor = database.query(UserEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            case USERS_ID:
                selection = UserEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                cursor = database.query(UserEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Cannot query unknown URI " + uri);
        }

        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    // Insert new data into the provider with the given ContentValues.
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case USERS:
                return insertUser(uri, contentValues);
            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
    }

    private Uri insertUser(Uri uri, ContentValues values) {

        // Check that the name is not null
        String user = values.getAsString(UserEntry.COLUMN_USER);
        if (user == null) {
            throw new IllegalArgumentException("User requires a user");
        }

        String password = values.getAsString(UserEntry.COLUMN_PASSWORD);
        if (password == null) {
            throw new IllegalArgumentException("Pet requires valid gender");
        }

        SQLiteDatabase database = mDbHelper.getWritableDatabase();
        long id = database.insert(UserEntry.TABLE_NAME, null, values);

        if (id == -1) {
            Log.e(LOG_TAG, "Failed to insert row for " + uri);
            return null;
        }

        // Notify all the listeners that the data has changed fot the user content URI
        // URI: context://com.example.eduponz.appcontabilidad/users
        getContext().getContentResolver().notifyChange(uri, null);
        return ContentUris.withAppendedId(uri, id);
    }

    // Updates the data at the given selection and selection arguments, with the new ContentValues.
    @Override
    public int update(Uri uri, ContentValues contentValues, String selection,
                      String[] selectionArgs) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case USERS:
                return updateUser(uri, contentValues, selection, selectionArgs);
            case USERS_ID:
                // For the USERS_ID code, extract out the ID from the URI,
                // so we know which row to update. Selection will be "_id=?" and selection
                // arguments will be a String array containing the actual ID.
                selection = UserEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                return updateUser(uri, contentValues, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Update is not supported for " + uri);
        }
    }

    // Return the number of rows that were successfully updated.
    private int updateUser(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        // If the {@link UserEntry#COLUMN_USER} key is present,
        // check that the user value is not null.
        if (values.containsKey(UserEntry.COLUMN_USER)) {
            String user = values.getAsString(UserEntry.COLUMN_USER);
            if (user == null) {
                throw new IllegalArgumentException("User requires a user name");
            }
        }

        // If the {@link UserEntry#COLUMN_PASSWORD} key is present,
        // check that the password value is not null.
        if (values.containsKey(UserEntry.COLUMN_PASSWORD)) {
            Integer password = values.getAsInteger(UserEntry.COLUMN_PASSWORD);
            if (password == null) {
                throw new IllegalArgumentException("Users requires password");
            }
        }

        // If there are no values to update, then don't try to update the database
        if (values.size() == 0) {
            return 0;
        }

        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        // Perform the update on the database and get the number of rows affected
        int rowsUpdated = database.update(UserEntry.TABLE_NAME, values, selection, selectionArgs);
        // If 1 or more rows were updated, then notify all listeners that the data at the given URI has changed
        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        // Return the number of rows updated
        return rowsUpdated;
    }

    // Delete the data at the given selection and selection arguments.
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        SQLiteDatabase database = mDbHelper.getWritableDatabase();
        // Track the number of rows that were deleted
        int rowsDeleted;

        final int match = sUriMatcher.match(uri);
        switch (match) {
            case USERS:
                rowsDeleted = database.delete(UserEntry.TABLE_NAME, selection, selectionArgs);
                if (rowsDeleted != 0) {
                    getContext().getContentResolver().notifyChange(uri, null);
                }
                return rowsDeleted;
            case USERS_ID:
                // Delete a single row given by the ID in the URI
                selection = UserEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                rowsDeleted = database.delete(UserEntry.TABLE_NAME, selection, selectionArgs);
                if (rowsDeleted != 0) {
                    getContext().getContentResolver().notifyChange(uri, null);
                }
                return rowsDeleted;
            default:
                throw new IllegalArgumentException("Deletion is not supported for " + uri);
        }
    }

    // Returns the MIME type of data for the content URI.
    @Override
    public String getType(Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case USERS:
                return UserEntry.CONTENT_LIST_TYPE;
            case USERS_ID:
                return UserEntry.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalStateException("Unknown URI " + uri + " with match " + match);
        }
    }
}

