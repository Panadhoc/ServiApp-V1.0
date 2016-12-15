package com.devmobile.servi_alpha;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


public class FragmentTwo extends MyFragment {

    private RecyclerView rv;
    public List<Person> persons;
    OnCardSelectedListener mcallback;
    private Bundle args;
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
        initializeData();
        rv = (RecyclerView) myview.findViewById(R.id.rv_2);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        rv.setLayoutManager(llm);
        RVAdapter adapter = new RVAdapter(persons);
        rv.setAdapter(adapter);
        rv.addOnItemTouchListener(
                new RecyclerItemClickListener(getContext(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        mcallback.onPersonSelected(position,1);
                    }
                })
        );

        return myview;

    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);



        if (context instanceof Activity){
            mcallback=(OnCardSelectedListener) context;
        }

    }
    private void initializeData(){
        persons = new ArrayList<>();
        args=getArguments();
        try {
            for (int i = 0; i < args.getInt("len"); i++) {

                persons.add(new Person(args.getString("userName" + i), args.getString("userID" + i), args.getInt("photoID" + i)));
                persons.get(i).service=args.getString("service"+i);
            }
        } catch (Exception e){
            persons.add(new Person ("Fake","17",R.drawable.prof));
        }
    }


}
