package com.example.eduponz.appcontabilidad.Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.eduponz.appcontabilidad.AddEntryActivity;
import com.example.eduponz.appcontabilidad.BudgetEntryActivity;
import com.example.eduponz.appcontabilidad.R;


public class BudgetFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_budget, container, false);

        final ListView fakeBudgetList;
        final String[] fakeStringList;

        fakeBudgetList = (ListView) rootView.findViewById(R.id.fragment_budget_listView);

        View emptyView = rootView.findViewById(R.id.budget_empty_view);
        fakeBudgetList.setEmptyView(emptyView);

        fakeStringList = getResources().getStringArray(R.array.fake_string_array);
        fakeBudgetList.setAdapter(new ArrayAdapter<String>(getContext(),
                R.layout.fragment_budget_list_item,
                R.id.fragment_budget_list_item_concept_textView, fakeStringList));

        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.budget_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), BudgetEntryActivity.class);
                startActivity(intent);
            }
        });

        return rootView;
    }
}
