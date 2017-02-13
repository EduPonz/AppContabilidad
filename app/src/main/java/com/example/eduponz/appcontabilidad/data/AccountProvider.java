package com.example.eduponz.appcontabilidad.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;
import com.example.eduponz.appcontabilidad.data.AccountContract.ExpenseEntry;
import com.example.eduponz.appcontabilidad.data.AccountContract.BudgetEntry;
import com.example.eduponz.appcontabilidad.data.AccountContract.ConceptEntry;
import com.example.eduponz.appcontabilidad.data.AccountContract.CurrencyEntry;

/**
 * Created by eduponz on 12/02/2017.
 */

public class AccountProvider extends ContentProvider {
    private static final int EXPENSES = 100;
    private static final int EXPENSE_ID = 101;
    private static final int BUDGET = 102;
    private static final int BUDGET_ID = 103;
    private static final int CONCEPT = 104;
    private static final int CONCEPT_ID = 105;
    private static final int CURRENCY = 106;
    private static final int CURRENCY_ID = 107;
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriMatcher.addURI(AccountContract.CONTENT_AUTHORITY, AccountContract.PATH_EXPENSES, EXPENSES);
        sUriMatcher.addURI(AccountContract.CONTENT_AUTHORITY, AccountContract.PATH_EXPENSES + "/#", EXPENSE_ID);
        sUriMatcher.addURI(AccountContract.CONTENT_AUTHORITY, AccountContract.PATH_BUDGET, BUDGET);
        sUriMatcher.addURI(AccountContract.CONTENT_AUTHORITY, AccountContract.PATH_BUDGET + "/#", BUDGET_ID);
        sUriMatcher.addURI(AccountContract.CONTENT_AUTHORITY, AccountContract.PATH_BUDGET, CONCEPT);
        sUriMatcher.addURI(AccountContract.CONTENT_AUTHORITY, AccountContract.PATH_BUDGET + "/#", CONCEPT_ID);
        sUriMatcher.addURI(AccountContract.CONTENT_AUTHORITY, AccountContract.PATH_BUDGET, CURRENCY);
        sUriMatcher.addURI(AccountContract.CONTENT_AUTHORITY, AccountContract.PATH_BUDGET + "/#", CURRENCY_ID);
    }

    // Tag for the log messages
    public static final String LOG_TAG = AccountProvider.class.getSimpleName();
    private AccountDbHelper mDbHelper;


    // Initialize the provider and the database helper object.
    @Override
    public boolean onCreate() {
        mDbHelper = new AccountDbHelper(getContext());
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
            case EXPENSES:
                cursor = database.query(ExpenseEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            case EXPENSE_ID:
                selection = ExpenseEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                cursor = database.query(ExpenseEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            case BUDGET:
                cursor = database.query(BudgetEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            case BUDGET_ID:
                selection = BudgetEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                cursor = database.query(BudgetEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            case CONCEPT:
                cursor = database.query(ConceptEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            case CONCEPT_ID:
                selection = ConceptEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                cursor = database.query(ConceptEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            case CURRENCY:
                cursor = database.query(CurrencyEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            case CURRENCY_ID:
                selection = CurrencyEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                cursor = database.query(CurrencyEntry.TABLE_NAME, projection, selection, selectionArgs,
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
            case EXPENSES:
                return insertExpense(uri, contentValues);
            case BUDGET:
                return insertBudget(uri, contentValues);
            case CONCEPT:
                return insertConcept(uri, contentValues);
            case CURRENCY:
                return insertCurrency(uri, contentValues);
            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
    }

    private Uri insertExpense(Uri uri, ContentValues values) {

        // Check that the user is not null
        String user = values.getAsString(ExpenseEntry.COLUMN_USER);
        if (user == null) {
            throw new IllegalArgumentException("Expense requires a user");
        }

        // Check that the expense_income is 1 or 2
        int expense_income = values.getAsInteger(ExpenseEntry.COLUMN_EXPENSE_INCOME);
        if (expense_income != 1 && expense_income != 2) {
            throw new IllegalArgumentException("Expense requires a expense_income");
        }

        // Check that the concept is not null
        String concept = values.getAsString(ExpenseEntry.COLUMN_CONCEPT);
        if (concept == null) {
            throw new IllegalArgumentException("Expense requires a concept");
        }

        // Check that the paid_with is not null
        String paid_with = values.getAsString(ExpenseEntry.COLUMN_PAID_WITH);
        if (paid_with == null) {
            throw new IllegalArgumentException("Expense requires a payment method");
        }

        // Check that the date is not null
        String date = values.getAsString(ExpenseEntry.COLUMN_DATE);
        if (date == null) {
            throw new IllegalArgumentException("Expense requires a date");
        }

        // Check that the currency is not null
        String currency = values.getAsString(ExpenseEntry.COLUMN_CURRENCY);
        if (currency == null) {
            throw new IllegalArgumentException("Expense requires a currency");
        }

        // Check that the quantity is not null
        String quantity = values.getAsString(ExpenseEntry.COLUMN_QUANTITY);
        if (quantity == null) {
            throw new IllegalArgumentException("Expense requires a quantity");
        }

        SQLiteDatabase database = mDbHelper.getWritableDatabase();
        long id = database.insert(ExpenseEntry.TABLE_NAME, null, values);

        if (id == -1) {
            Log.e(LOG_TAG, "Failed to insert row for " + uri);
            return null;
        }

        // Notify all the listeners that the data has changed fot the user content URI
        // URI: context://com.example.eduponz.appcontabilidad/expenses
        getContext().getContentResolver().notifyChange(uri, null);
        return ContentUris.withAppendedId(uri, id);
    }

    private Uri insertBudget(Uri uri, ContentValues values) {

        // Check that the user is not null
        String user = values.getAsString(BudgetEntry.COLUMN_USER);
        if (user == null) {
            throw new IllegalArgumentException("Budget requires a user");
        }

        // Check that the expense_income is 1 or 2
        int expense_income = values.getAsInteger(BudgetEntry.COLUMN_EXPENSE_INCOME);
        if (expense_income != 1 && expense_income != 2) {
            throw new IllegalArgumentException("Budget requires a expense_income");
        }

        // Check that the concept is not null
        String concept = values.getAsString(BudgetEntry.COLUMN_CONCEPT);
        if (concept == null) {
            throw new IllegalArgumentException("Budget requires a concept");
        }

        // Check that the currency is not null
        String currency = values.getAsString(BudgetEntry.COLUMN_CURRENCY);
        if (currency == null) {
            throw new IllegalArgumentException("Budget requires a currency");
        }

        // Check that the quantity is not null
        String quantity = values.getAsString(BudgetEntry.COLUMN_QUANTITY);
        if (quantity == null) {
            throw new IllegalArgumentException("Budget requires a quantity");
        }

        SQLiteDatabase database = mDbHelper.getWritableDatabase();
        long id = database.insert(BudgetEntry.TABLE_NAME, null, values);

        if (id == -1) {
            Log.e(LOG_TAG, "Failed to insert row for " + uri);
            return null;
        }

        // Notify all the listeners that the data has changed fot the user content URI
        // URI: context://com.example.eduponz.appcontabilidad/budget
        getContext().getContentResolver().notifyChange(uri, null);
        return ContentUris.withAppendedId(uri, id);
    }

    private Uri insertConcept(Uri uri, ContentValues values) {

        // Check that the user is not null
        String user = values.getAsString(ConceptEntry.COLUMN_USER);
        if (user == null) {
            throw new IllegalArgumentException("Concept requires a user");
        }

        // Check that the concept is not null
        String concept = values.getAsString(ConceptEntry.COLUMN_CONCEPT);
        if (concept == null) {
            throw new IllegalArgumentException("Concept requires a concept");
        }

        // Check that the expense_income is 1 or 2
        int expense_income = values.getAsInteger(ConceptEntry.COLUMN_EXPENSE_INCOME);
        if (expense_income != 1 && expense_income != 2) {
            throw new IllegalArgumentException("Concept requires a expense_income");
        }

        SQLiteDatabase database = mDbHelper.getWritableDatabase();
        long id = database.insert(ConceptEntry.TABLE_NAME, null, values);

        if (id == -1) {
            Log.e(LOG_TAG, "Failed to insert row for " + uri);
            return null;
        }

        // Notify all the listeners that the data has changed fot the user content URI
        // URI: context://com.example.eduponz.appcontabilidad/budget
        getContext().getContentResolver().notifyChange(uri, null);
        return ContentUris.withAppendedId(uri, id);
    }

    private Uri insertCurrency(Uri uri, ContentValues values) {

        // Check that the user is not null
        String user = values.getAsString(CurrencyEntry.COLUMN_USER);
        if (user == null) {
            throw new IllegalArgumentException("Currency requires a user");
        }

        // Check that the currency is not null
        String currency = values.getAsString(CurrencyEntry.COLUMN_CURRENCY);
        if (currency == null) {
            throw new IllegalArgumentException("Currency requires a currency");
        }

        // Check that the exchange_rate is not null
        String exchange_rate = values.getAsString(CurrencyEntry.COLUMN_EXCHANGE_RATE);
        if (exchange_rate == null) {
            throw new IllegalArgumentException("Currency requires a quantity");
        }

        SQLiteDatabase database = mDbHelper.getWritableDatabase();
        long id = database.insert(BudgetEntry.TABLE_NAME, null, values);

        if (id == -1) {
            Log.e(LOG_TAG, "Failed to insert row for " + uri);
            return null;
        }

        // Notify all the listeners that the data has changed fot the user content URI
        // URI: context://com.example.eduponz.appcontabilidad/budget
        getContext().getContentResolver().notifyChange(uri, null);
        return ContentUris.withAppendedId(uri, id);
    }

    // Updates the data at the given selection and selection arguments, with the new ContentValues.
    @Override
    public int update(Uri uri, ContentValues contentValues, String selection,
                      String[] selectionArgs) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case EXPENSES:
                return updateExpense(uri, contentValues, selection, selectionArgs);
            case EXPENSE_ID:
                // For the EXPENSE_ID code, extract out the ID from the URI,
                // so we know which row to update. Selection will be "_id=?" and selection
                // arguments will be a String array containing the actual ID.
                selection = ExpenseEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                return updateExpense(uri, contentValues, selection, selectionArgs);
            case BUDGET:
                return updateBudget(uri, contentValues, selection, selectionArgs);
            case BUDGET_ID:
                // For the BUDGET_ID code, extract out the ID from the URI,
                // so we know which row to update. Selection will be "_id=?" and selection
                // arguments will be a String array containing the actual ID.
                selection = BudgetEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                return updateBudget(uri, contentValues, selection, selectionArgs);
            case CONCEPT:
                return updateConcept(uri, contentValues, selection, selectionArgs);
            case CONCEPT_ID:
                // For the CONCEPT_ID code, extract out the ID from the URI,
                // so we know which row to update. Selection will be "_id=?" and selection
                // arguments will be a String array containing the actual ID.
                selection = ConceptEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                return updateConcept(uri, contentValues, selection, selectionArgs);
            case CURRENCY:
                return updateCurrency(uri, contentValues, selection, selectionArgs);
            case CURRENCY_ID:
                // For the CURRENCY_ID code, extract out the ID from the URI,
                // so we know which row to update. Selection will be "_id=?" and selection
                // arguments will be a String array containing the actual ID.
                selection = CurrencyEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                return updateCurrency(uri, contentValues, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Update is not supported for " + uri);
        }
    }

    // Return the number of rows that were successfully updated.
    private int updateExpense(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        // If the {@link ExpenseEntry#COLUMN_USER} key is present,
        // check that the user value is not null.
        if (values.containsKey(ExpenseEntry.COLUMN_USER)) {
            String user = values.getAsString(ExpenseEntry.COLUMN_USER);
            if (user == null) {
                throw new IllegalArgumentException("Expense requires a user name");
            }
        }

        // If the {@link ExpenseEntry#COLUMN_EXPENSE_INCOME} key is present,
        // check that the expense_income value is 1 or 2.
        if (values.containsKey(ExpenseEntry.COLUMN_EXPENSE_INCOME)) {
            int expense_income = values.getAsInteger(ExpenseEntry.COLUMN_EXPENSE_INCOME);
            if (expense_income != 1 && expense_income != 2) {
                throw new IllegalArgumentException("Expense requires a expense_income");
            }
        }

        // If the {@link ExpenseEntry#COLUMN_CONCEPT} key is present,
        // check that the concept value is not null.
        if (values.containsKey(ExpenseEntry.COLUMN_CONCEPT)) {
            String concept = values.getAsString(ExpenseEntry.COLUMN_CONCEPT);
            if (concept == null) {
                throw new IllegalArgumentException("Expense requires a concept");
            }
        }

        // If the {@link ExpenseEntry#COLUMN_PAID_WITH} key is present,
        // check that the paid_with value is not null.
        if (values.containsKey(ExpenseEntry.COLUMN_PAID_WITH)) {
            String paid_with = values.getAsString(ExpenseEntry.COLUMN_PAID_WITH);
            if (paid_with == null) {
                throw new IllegalArgumentException("Expense requires a paid_with");
            }
        }

        // If the {@link ExpenseEntry#COLUMN_DATE} key is present,
        // check that the date value is not null.
        if (values.containsKey(ExpenseEntry.COLUMN_DATE)) {
            String date = values.getAsString(ExpenseEntry.COLUMN_DATE);
            if (date == null) {
                throw new IllegalArgumentException("Expense requires a date");
            }
        }

        // If the {@link ExpenseEntry#COLUMN_CURRENCY} key is present,
        // check that the currency value is not null.
        if (values.containsKey(ExpenseEntry.COLUMN_CURRENCY)) {
            String currency = values.getAsString(ExpenseEntry.COLUMN_CURRENCY);
            if (currency == null) {
                throw new IllegalArgumentException("Expense requires a currency");
            }
        }

        // If the {@link ExpenseEntry#COLUMN_QUANTITY} key is present,
        // check that the quantity value is not null.
        if (values.containsKey(ExpenseEntry.COLUMN_QUANTITY)) {
            String quantity = values.getAsString(ExpenseEntry.COLUMN_QUANTITY);
            if (quantity == null) {
                throw new IllegalArgumentException("Expense requires a paid_with");
            }
        }

        // If there are no values to update, then don't try to update the database
        if (values.size() == 0) {
            return 0;
        }

        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        // Perform the update on the database and get the number of rows affected
        int rowsUpdated = database.update(ExpenseEntry.TABLE_NAME, values, selection, selectionArgs);
        // If 1 or more rows were updated, then notify all listeners that the data at the given URI has changed
        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        // Return the number of rows updated
        return rowsUpdated;
    }

    // Return the number of rows that were successfully updated.
    private int updateBudget(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        // If the {@link BudgetEntry#COLUMN_USER} key is present,
        // check that the user value is not null.
        if (values.containsKey(BudgetEntry.COLUMN_USER)) {
            String user = values.getAsString(BudgetEntry.COLUMN_USER);
            if (user == null) {
                throw new IllegalArgumentException("Budget requires a user name");
            }
        }

        // If the {@link BudgetEntry#COLUMN_EXPENSE_INCOME} key is present,
        // check that the expense_income value is 1 or 2.
        if (values.containsKey(BudgetEntry.COLUMN_EXPENSE_INCOME)) {
            int expense_income = values.getAsInteger(BudgetEntry.COLUMN_EXPENSE_INCOME);
            if (expense_income != 1 && expense_income != 2) {
                throw new IllegalArgumentException("Budget requires a expense_income");
            }
        }

        // If the {@link BudgetEntry#COLUMN_CONCEPT} key is present,
        // check that the concept value is not null.
        if (values.containsKey(BudgetEntry.COLUMN_CONCEPT)) {
            String concept = values.getAsString(BudgetEntry.COLUMN_CONCEPT);
            if (concept == null) {
                throw new IllegalArgumentException("Budget requires a concept");
            }
        }

        // If the {@link BudgetEntry#COLUMN_CURRENCY} key is present,
        // check that the currency value is not null.
        if (values.containsKey(BudgetEntry.COLUMN_CURRENCY)) {
            String currency = values.getAsString(BudgetEntry.COLUMN_CURRENCY);
            if (currency == null) {
                throw new IllegalArgumentException("Budget requires a currency");
            }
        }

        // If the {@link BudgetEntry#COLUMN_QUANTITY} key is present,
        // check that the quantity value is not null.
        if (values.containsKey(BudgetEntry.COLUMN_QUANTITY)) {
            String quantity = values.getAsString(BudgetEntry.COLUMN_QUANTITY);
            if (quantity == null) {
                throw new IllegalArgumentException("Budget requires a quantity");
            }
        }

        // If there are no values to update, then don't try to update the database
        if (values.size() == 0) {
            return 0;
        }

        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        // Perform the update on the database and get the number of rows affected
        int rowsUpdated = database.update(BudgetEntry.TABLE_NAME, values, selection, selectionArgs);
        // If 1 or more rows were updated, then notify all listeners that the data at the given URI has changed
        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        // Return the number of rows updated
        return rowsUpdated;
    }

    // Return the number of rows that were successfully updated.
    private int updateConcept(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        // If the {@link ConceptEntry#COLUMN_USER} key is present,
        // check that the user value is not null.
        if (values.containsKey(ConceptEntry.COLUMN_USER)) {
            String user = values.getAsString(ConceptEntry.COLUMN_USER);
            if (user == null) {
                throw new IllegalArgumentException("Concept requires a user name");
            }
        }

        // If the {@link ConceptEntry#COLUMN_CONCEPT} key is present,
        // check that the concept value is not null.
        if (values.containsKey(ConceptEntry.COLUMN_CONCEPT)) {
            String concept = values.getAsString(ConceptEntry.COLUMN_CONCEPT);
            if (concept == null) {
                throw new IllegalArgumentException("Concept requires a concept");
            }
        }

        // If the {@link ConceptEntry#COLUMN_EXPENSE_INCOME} key is present,
        // check that the expense_income value is 1 or 2.
        if (values.containsKey(ConceptEntry.COLUMN_EXPENSE_INCOME)) {
            int expense_income = values.getAsInteger(ConceptEntry.COLUMN_EXPENSE_INCOME);
            if (expense_income != 1 && expense_income != 2) {
                throw new IllegalArgumentException("Concept requires a expense_income");
            }
        }

        // If there are no values to update, then don't try to update the database
        if (values.size() == 0) {
            return 0;
        }

        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        // Perform the update on the database and get the number of rows affected
        int rowsUpdated = database.update(ConceptEntry.TABLE_NAME, values, selection, selectionArgs);
        // If 1 or more rows were updated, then notify all listeners that the data at the given URI has changed
        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        // Return the number of rows updated
        return rowsUpdated;
    }

    // Return the number of rows that were successfully updated.
    private int updateCurrency(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        // If the {@link CurrencyEntry#COLUMN_USER} key is present,
        // check that the user value is not null.
        if (values.containsKey(CurrencyEntry.COLUMN_USER)) {
            String user = values.getAsString(CurrencyEntry.COLUMN_USER);
            if (user == null) {
                throw new IllegalArgumentException("Expense requires a user name");
            }
        }

        // If the {@link CurrencyEntry#COLUMN_CURRENCY} key is present,
        // check that the currency value is not null.
        if (values.containsKey(CurrencyEntry.COLUMN_CURRENCY)) {
            String currency = values.getAsString(CurrencyEntry.COLUMN_CURRENCY);
            if (currency == null) {
                throw new IllegalArgumentException("Expense requires a currency");
            }
        }

        // If the {@link CurrencyEntry#COLUMN_EXCHANGE_RATE} key is present,
        // check that the concept value is not null.
        if (values.containsKey(CurrencyEntry.COLUMN_EXCHANGE_RATE)) {
            String concept = values.getAsString(CurrencyEntry.COLUMN_EXCHANGE_RATE);
            if (concept == null) {
                throw new IllegalArgumentException("Currency requires a exchange_rate");
            }
        }

        // If there are no values to update, then don't try to update the database
        if (values.size() == 0) {
            return 0;
        }

        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        // Perform the update on the database and get the number of rows affected
        int rowsUpdated = database.update(CurrencyEntry.TABLE_NAME, values, selection, selectionArgs);
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
            case EXPENSES:
                rowsDeleted = database.delete(ExpenseEntry.TABLE_NAME, selection, selectionArgs);
                if (rowsDeleted != 0) {
                    getContext().getContentResolver().notifyChange(uri, null);
                }
                return rowsDeleted;
            case EXPENSE_ID:
                // Delete a single row given by the ID in the URI
                selection = ExpenseEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                rowsDeleted = database.delete(ExpenseEntry.TABLE_NAME, selection, selectionArgs);
                if (rowsDeleted != 0) {
                    getContext().getContentResolver().notifyChange(uri, null);
                }
                return rowsDeleted;
            case BUDGET:
                rowsDeleted = database.delete(BudgetEntry.TABLE_NAME, selection, selectionArgs);
                if (rowsDeleted != 0) {
                    getContext().getContentResolver().notifyChange(uri, null);
                }
                return rowsDeleted;
            case BUDGET_ID:
                // Delete a single row given by the ID in the URI
                selection = BudgetEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                rowsDeleted = database.delete(BudgetEntry.TABLE_NAME, selection, selectionArgs);
                if (rowsDeleted != 0) {
                    getContext().getContentResolver().notifyChange(uri, null);
                }
                return rowsDeleted;
            case CONCEPT:
                rowsDeleted = database.delete(ConceptEntry.TABLE_NAME, selection, selectionArgs);
                if (rowsDeleted != 0) {
                    getContext().getContentResolver().notifyChange(uri, null);
                }
                return rowsDeleted;
            case CONCEPT_ID:
                // Delete a single row given by the ID in the URI
                selection = ConceptEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                rowsDeleted = database.delete(ConceptEntry.TABLE_NAME, selection, selectionArgs);
                if (rowsDeleted != 0) {
                    getContext().getContentResolver().notifyChange(uri, null);
                }
                return rowsDeleted;
            case CURRENCY:
                rowsDeleted = database.delete(CurrencyEntry.TABLE_NAME, selection, selectionArgs);
                if (rowsDeleted != 0) {
                    getContext().getContentResolver().notifyChange(uri, null);
                }
                return rowsDeleted;
            case CURRENCY_ID:
                // Delete a single row given by the ID in the URI
                selection = CurrencyEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                rowsDeleted = database.delete(CurrencyEntry.TABLE_NAME, selection, selectionArgs);
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
            case EXPENSES:
                return ExpenseEntry.CONTENT_LIST_TYPE;
            case EXPENSE_ID:
                return ExpenseEntry.CONTENT_ITEM_TYPE;
            case BUDGET:
                return BudgetEntry.CONTENT_LIST_TYPE;
            case BUDGET_ID:
                return BudgetEntry.CONTENT_ITEM_TYPE;
            case CONCEPT:
                return ConceptEntry.CONTENT_LIST_TYPE;
            case CONCEPT_ID:
                return ConceptEntry.CONTENT_ITEM_TYPE;
            case CURRENCY:
                return CurrencyEntry.CONTENT_LIST_TYPE;
            case CURRENCY_ID:
                return CurrencyEntry.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalStateException("Unknown URI " + uri + " with match " + match);
        }
    }
}
