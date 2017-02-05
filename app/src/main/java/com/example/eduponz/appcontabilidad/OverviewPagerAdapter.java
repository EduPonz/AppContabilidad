package com.example.eduponz.appcontabilidad;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.eduponz.appcontabilidad.Fragments.BudgetFragment;
import com.example.eduponz.appcontabilidad.Fragments.ChartFragment;
import com.example.eduponz.appcontabilidad.Fragments.GeneralOverviewFragment;
import com.example.eduponz.appcontabilidad.Fragments.SummaryFragment;

public class OverviewPagerAdapter extends FragmentStatePagerAdapter {

    int mNumOfTabs;

    public OverviewPagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return new GeneralOverviewFragment();
            case 1:
                return new SummaryFragment();
            case 2:
                return new BudgetFragment();
            case 3:
                return new ChartFragment();
            default:
                return null;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "General Overview";
            case 1:
                return "Summary";
            case 2:
                return "Budget";
            case 3:
                return "Chart";
            default:
                return null;
        }
    }
}