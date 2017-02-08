package com.example.eduponz.appcontabilidad;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class OverviewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);

        // ******* TOOLBAR LAYOUT ************************************************
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.general_overview_page_title)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.summary_page_title)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.budget_page_title)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.chart_page_title)));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final OverviewPagerAdapter adapter = new OverviewPagerAdapter
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
                        break;
                    case 1:
                        toolbar.setTitle(R.string.summary_page_title);
                        break;
                    case 2:
                        toolbar.setTitle(R.string.budget_page_title);
                        break;
                    case 3:
                        toolbar.setTitle(R.string.chart_page_title);
                        break;
                    default:
                        toolbar.setTitle(R.string.general_overview_page_title);
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

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout_overview);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.syncState();
        mDrawerStringList = getResources().getStringArray(R.array.drawer_string_array);
        mDrawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_list_item, mDrawerStringList));
        // ************* DRAWER LAYOUT ******************************************

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_catalog.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.overview_menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        //nothing yet
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            case R.id.action_insert_dummy_data:
                // Nothing
                return true;
            case R.id.action_delete_all_entries:
                // Nothing
               return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

