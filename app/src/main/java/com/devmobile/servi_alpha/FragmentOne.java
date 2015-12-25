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
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class FragmentOne extends Fragment {


    private RecyclerView rv;
    private List<Person> offers;
    public FragmentOne() {
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
        View myview = inflater.inflate(R.layout.fragment_blank, container, false);
        rv = (RecyclerView) myview.findViewById(R.id.rv);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        rv.setLayoutManager(llm);
        initializeData();
        RVAdapter adapter = new RVAdapter(offers);
        rv.setAdapter(adapter);
        return myview;

    }


    // This method creates an ArrayList that has three Person objects
// Checkout the project associated with this tutorial on Github if
// you want to use the same images.
    private void initializeData(){
        offers = new ArrayList<>();
        offers.add(new Person("Offer 1", "Tunis", R.drawable.saw));
        offers.add(new Person("Offer 2", "Ariana", R.drawable.hammer));
        offers.add(new Person("Offer 3", "Ben Arous", R.drawable.tournevis));
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */


}
