package com.example.julia.homeworkcalendar;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeWorkRecycler extends Fragment {
private RecyclerView recyclerView;
private HomeWorkAdapter adapter;

    public HomeWorkRecycler() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Context context = getContext();
        View view = inflater.inflate(R.layout.fragment_home_work_recycler, container,false);
        adapter = new HomeWorkAdapter(context);
        recyclerView = (RecyclerView) view.findViewById(R.id.mRecyler);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        // Inflate the layout for this fragment
        return view;
    }

}
