package com.devmobile.servi_alpha;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


public class FragmentTwo extends Fragment {

    private RecyclerView rv;
    private List<Person> persons;
    public FragmentTwo() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myview = inflater.inflate(R.layout.fragment_fragment_two, container, false);
        rv = (RecyclerView) myview.findViewById(R.id.rv_2);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        rv.setLayoutManager(llm);
        initializeData();
        RVAdapter adapter = new RVAdapter(persons);
        rv.setAdapter(adapter);
        return myview;

    }

    private void initializeData(){
        persons = new ArrayList<>();
        persons.add(new Person("Person 1", "Tunis", R.drawable.prof));
        persons.add(new Person("Person 2", "Ariana", R.drawable.prof));
        persons.add(new Person("Person 3", "Ben Arous", R.drawable.prof));
    }


}
