package com.example.eduponz.appcontabilidad.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.eduponz.appcontabilidad.R;

public class SummaryFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_summary, container, false);

        final ListView fakeSummaryList;
        final String[] fakeConceptString;

        fakeSummaryList = (ListView) rootView.findViewById(R.id.summary_listView);

        View emptyView = rootView.findViewById(R.id.summary_empty_view);
        fakeSummaryList.setEmptyView(emptyView);

        fakeConceptString = getResources().getStringArray(R.array.fake_string_array);

        fakeSummaryList.setAdapter(new ArrayAdapter<String>(getContext(),
                R.layout.fragment_summary_list_item,
                R.id.fragment_summary_list_item_concept_textView, fakeConceptString));

        return rootView;
    }
}
