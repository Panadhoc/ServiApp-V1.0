package com.devmobile.servi_alpha;

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class SearchResultActivity extends AppCompatActivity {


    private RecyclerView rv;
    private List<Person> persons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        handleIntent(getIntent());
        getSupportActionBar().setTitle("Results");
        getSupportActionBar().setHomeButtonEnabled(true);
        rv = (RecyclerView) findViewById(R.id.rv_3);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);
        initializeData();
        RVAdapter adapter = new RVAdapter(persons);
        rv.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            //use the query to search
        }
    }
    private void initializeData(){
        persons = new ArrayList<>();
        persons.add(new Person("Result 1", "Near", R.drawable.prof));
        persons.add(new Person("Result 2", "Far", R.drawable.prof));
        persons.add(new Person("Result 3", "Further", R.drawable.prof));
    }
}