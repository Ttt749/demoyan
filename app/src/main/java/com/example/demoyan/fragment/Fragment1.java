package com.example.demoyan.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.example.demoyan.Adapter.ListAdapter;

import com.example.demoyan.R;

import java.util.ArrayList;
import java.util.List;

public class Fragment1 extends Fragment {
    private List<String> strings = new ArrayList<>();
    private ListAdapter listAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main,container,false);
        init();
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        listAdapter = new ListAdapter(strings);
        recyclerView.setAdapter(listAdapter);
        return view;
    }

    private void init(){
        for (int i =0;i<10;i++){
            strings.add("闫丹丹");
        }
    }
}
