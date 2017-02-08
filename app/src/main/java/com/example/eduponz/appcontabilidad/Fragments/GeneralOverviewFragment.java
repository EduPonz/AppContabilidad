package com.example.eduponz.appcontabilidad.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.eduponz.appcontabilidad.R;

public class GeneralOverviewFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_general_overview, container, false);

        final ListView fakeList;
        final String[] fakeStringList;

        fakeList = (ListView) rootView.findViewById(R.id.general_fragment_list);

        View emptyView = rootView.findViewById(R.id.fragment_overview_empty_view);
        fakeList.setEmptyView(emptyView);

        fakeStringList = getResources().getStringArray(R.array.drawer_string_array);
        fakeList.setAdapter(new ArrayAdapter<String>(getContext(),
                R.layout.fragment_overview_list_item, R.id.fragment_overview_list_item, fakeStringList));

        return rootView;

    }
}
