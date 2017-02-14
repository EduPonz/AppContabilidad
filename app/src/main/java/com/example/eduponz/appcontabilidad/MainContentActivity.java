package com.example.eduponz.appcontabilidad;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.eduponz.appcontabilidad.Fragments.SummaryFragment;
import com.example.eduponz.appcontabilidad.data.AccountContract.ExpenseEntry;


public class MainContentActivity extends AppCompatActivity {

    // a static variable to get a reference of our application context
    public static Context contextOfApplication;
    private int tabPosition;
    public static Context getContextOfApplication()
    {
        return contextOfApplication;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_content);

        contextOfApplication = getApplicationContext();

        // ******* TOOLBAR LAYOUT ************************************************
        final Toolbar toolbar = (Toolbar) findViewById(R.id.main_content_toolbar);
        setSupportActionBar(toolbar);

        final TabLayout tabLayout = (TabLayout) findViewById(R.id.main_content_tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.general_overview_page_title)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.summary_page_title)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.budget_page_title)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.chart_page_title)));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.main_content_viewPager);
        final MainContentPagerAdapter adapter = new MainContentPagerAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                switch (tab.getPosition()) {
                    case 0:
                        toolbar.setTitle(R.string.general_overview_page_title);
                        tabPosition = 0;
                        invalidateOptionsMenu();
                        break;
                    case 1:
                        toolbar.setTitle(R.string.summary_page_title);
                        tabPosition = 1;
                        invalidateOptionsMenu();
                        break;
                    case 2:
                        toolbar.setTitle(R.string.budget_page_title);
                        tabPosition = 2;
                        invalidateOptionsMenu();
                        break;
                    case 3:
                        toolbar.setTitle(R.string.chart_page_title);
                        tabPosition = 3;
                        invalidateOptionsMenu();
                        break;
                    default:
                        toolbar.setTitle(R.string.general_overview_page_title);
                        tabPosition = 0;
                        invalidateOptionsMenu();
                        break;
                }
            }

            @Override
            public void onTabUnselected (TabLayout.Tab tab){

            }

            @Override
            public void onTabReselected (TabLayout.Tab tab){

            }
        });
        // ******* TOOLBAR LAYOUT ************************************************

        // ******* DRAWER LAYOUT ************************************************
        final DrawerLayout mDrawerLayout;
        final ListView mDrawerList;
        final String[] mDrawerStringList;

        mDrawerLayout = (DrawerLayout) findViewById(R.id.main_content_drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.main_content_navigationDrawer_listView);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.syncState();
        mDrawerStringList = getResources().getStringArray(R.array.fake_string_array);
        mDrawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.main_content_drawer_list_item,
                mDrawerStringList));
        // ************* DRAWER LAYOUT ******************************************

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_catalog.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.main_content_menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        MenuItem dummyExpense = menu.findItem(R.id.action_insert_dummy_expense);
        MenuItem dummyBudget = menu.findItem(R.id.action_insert_dummy_budget_entry);
        MenuItem deleteAllExpenses = menu.findItem(R.id.action_delete_all_expenses);
        MenuItem deleteAllBudget = menu.findItem(R.id.action_delete_all_budget_entries);
        switch (tabPosition){
            case 0:
                dummyExpense.setVisible(false);
                dummyBudget.setVisible(false);
                deleteAllExpenses.setVisible(false);
                deleteAllBudget.setVisible(false);
                break;
            case 1:
                dummyBudget.setVisible(false);
                deleteAllBudget.setVisible(false);
                break;
            case 2:
                dummyExpense.setVisible(false);
                deleteAllExpenses.setVisible(false);
                break;
            case 3:
                dummyExpense.setVisible(false);
                dummyBudget.setVisible(false);
                deleteAllExpenses.setVisible(false);
                deleteAllBudget.setVisible(false);
                break;
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            case R.id.action_insert_dummy_expense:
                SummaryFragment mSummaryFragment = new SummaryFragment();
                mSummaryFragment.insertExpense();
                return true;
            case R.id.action_insert_dummy_budget_entry:
                // Nothing
               return true;
            case R.id.action_delete_all_expenses:
                showDeleteConfirmationDialog();
                return true;
            case R.id.action_delete_all_budget_entries:
                // Nothing yet
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showDeleteConfirmationDialog() {
        // Create an AlertDialog.Builder and set the message, and click listeners
        // for the positive and negative buttons on the dialog.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.delete_all_expenses_dialog_msg);
        builder.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked the "Delete" button, so delete the pet.
                deleteAllExpenses();
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked the "Cancel" button, so dismiss the dialog
                // and continue editing the pet.
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });

        // Create and show the AlertDialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void deleteAllExpenses() {
        if (ExpenseEntry.CONTENT_URI != null) {
            int rowsDeleted = getContentResolver().delete(ExpenseEntry.CONTENT_URI, null, null);

            if (rowsDeleted == 0) {
                Toast.makeText(this, getString(R.string.editor_delete_all_expenses_failed),
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, getString(R.string.editor_delete_all_expenses_successful),
                        Toast.LENGTH_SHORT).show();
            }
        }
    }
}

