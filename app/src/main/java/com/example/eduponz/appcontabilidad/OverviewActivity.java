package com.example.eduponz.appcontabilidad;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.support.v7.app.AppCompatActivity;


public class OverviewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);

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
    }
}

