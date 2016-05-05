package com.devmobile.servi_alpha;

import android.app.ProgressDialog;
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
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class UserResultActivity extends AppCompatActivity {


    private RecyclerView rv;
    private List<Person> persons;
    public static String JSON_URL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        handleIntent(getIntent());
        getSupportActionBar().setTitle("Results");
        getSupportActionBar().setHomeButtonEnabled(true);
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

            JSON_URL=Globals.address+":8080/servi/app/user/service?id="+ Globals.id + "&token=" + Globals.token + "&service=" + query;
            Log.v("error",JSON_URL);

            final ProgressDialog progressDialog = new ProgressDialog(UserResultActivity.this,
                    R.style.AppTheme_Dark_Dialog);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Loading data...");
            progressDialog.show();
            StringRequest stringRequest = new StringRequest(JSON_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            showJSON(response);

                            rv = (RecyclerView) findViewById(R.id.rv_3);
                            LinearLayoutManager llm = new LinearLayoutManager(UserResultActivity.this);
                            rv.setLayoutManager(llm);
                            initializeData();
                            RVAdapter adapter = new RVAdapter(persons);
                            rv.setAdapter(adapter);
                            progressDialog.dismiss();
                            findViewById(android.R.id.content).invalidate();
                            rv.addOnItemTouchListener(
                                    new RecyclerItemClickListener(getApplicationContext(), new RecyclerItemClickListener.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(View view, int position) {
                                            Intent intent = new Intent(getApplicationContext(), Info_Activity.class);
                                            intent.putExtra("value",1);
                                            intent.putExtra("username", persons.get(position).username);
                                            intent.putExtra("address",persons.get(position).address);
                                            intent.putExtra("phone",persons.get(position).phone);
                                            startActivity(intent);
                                        }
                                    })
                            );


                        }

                    }
                    ,
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(UserResultActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                            progressDialog.dismiss();
                        }
                    });
            RequestQueue requestQueue = Volley.newRequestQueue(UserResultActivity.this);
            requestQueue.add(stringRequest);
        }
    }
    private void initializeData() {
       persons = new ArrayList<Person>();
        try {
            for (int i = 0; i < ParseJSON.ruserids.length; i++) {

                persons.add(new Person(ParseJSON.rusernames[i], ParseJSON.ruserids[i], R.drawable.prof));
                persons.get(i).address=ParseJSON.raddresses[i];
                persons.get(i).phone=ParseJSON.rphones[i];
            }
        } catch (Exception e) {
            persons.add(new Person("Souhail","12",R.drawable.prof));
            Log.v("error", "ids :");
        }

    }
    private void showJSON(String json) {

        ParseJSON pj = new ParseJSON(json);
        pj.parseRU();
    }
}