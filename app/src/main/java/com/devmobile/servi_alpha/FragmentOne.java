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
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.List;


public class FragmentOne extends MyFragment {



    private RecyclerView rv;
    private List<Post> offers;
    private Bundle args;
    OnCardSelectedListener mcallback;
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
        initializeData();
        rv = (RecyclerView) myview.findViewById(R.id.rv);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        rv.setLayoutManager(llm);
        RVPAdapter adapter = new RVPAdapter(offers);
        rv.setAdapter(adapter);
        rv.addOnItemTouchListener(
                new RecyclerItemClickListener(getContext(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        mcallback.onPersonSelected(position,0);
                    }
                })
        );
        return myview;

    }


    private void initializeData(){
        offers = new ArrayList<>();
        args=getArguments();
        try {
            for (int i = 0; i < args.getInt("len"); i++) {

                offers.add(new Post( args.getString("title" + i),args.getString("postID" + i),
                        args.getString("desc" + i), args.getString("owner" + i), args.getInt("photoID" + i)));
            }
        }
        catch (Exception e){
            offers.add(new Post("Fake Post","fakeid","Fake Description","1",R.drawable.tournevis));
        }
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);



        if (context instanceof Activity){
            mcallback=(OnCardSelectedListener) context;
        }

    }
}
