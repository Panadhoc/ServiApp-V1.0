package com.devmobile.servi_alpha;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
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

public class PostResultActivity extends AppCompatActivity {


    private RecyclerView rv;
    private List<Post> offers;
    public static String JSON_URL=Globals.address+":8080/servi/app/user/keyword?id=";
    private String resp;

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
            LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

// Define a listener that responds to location updates
            LocationListener locationListener = new LocationListener() {
                public void onLocationChanged(Location location) {
                    // Called when a new location is found by the network location provider.

                }

                public void onStatusChanged(String provider, int status, Bundle extras) {}

                public void onProviderEnabled(String provider) {}

                public void onProviderDisabled(String provider) {}
            };

// Register the listener with the Location Manager to receive location updates
            String locationProvider = LocationManager.NETWORK_PROVIDER;
            try {
                Location lastKnownLocation = locationManager.getLastKnownLocation(locationProvider);
                double lat=lastKnownLocation.getLatitude();
                double lon=lastKnownLocation.getLongitude();
            } catch (SecurityException Se){}


            try {
                query=URLEncoder.encode(query,"utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            JSON_URL+= Globals.id + "&token=" + Globals.token + "&keyword=" + query;

            final ProgressDialog progressDialog = new ProgressDialog(PostResultActivity.this,
                    R.style.AppTheme_Dark_Dialog);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Loading data...");
            progressDialog.show();
            getSupportActionBar().setTitle("Offers");
            StringRequest stringRequest = new StringRequest(JSON_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            resp=response;
                            showJSON(response);

                            rv = (RecyclerView) findViewById(R.id.rv_3);
                            LinearLayoutManager llm = new LinearLayoutManager(PostResultActivity.this);
                            rv.setLayoutManager(llm);
                            initializeData();
                            RVPAdapter adapter = new RVPAdapter(offers);
                            rv.setAdapter(adapter);
                            progressDialog.dismiss();
                            findViewById(android.R.id.content).invalidate();

                        }

                    }
                    ,
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(PostResultActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                            progressDialog.dismiss();
                        }
                    });
            RequestQueue requestQueue = Volley.newRequestQueue(PostResultActivity.this);
            requestQueue.add(stringRequest);
        }
    }

    private void initializeData() {
        offers = new ArrayList<Post>();
        try {
            for (int i = 0; i < ParseJSON.rpostids.length; i++) {

                offers.add(new Post(ParseJSON.rpostids[i], ParseJSON.rposttitles[i],

                        ParseJSON.rpostdescs[i],ParseJSON.rpostowners[i], R.drawable.tournevis));
            }
        } catch (Exception e) {
            offers.add(new Post("id","titre","description","17",R.drawable.tournevis));
            Log.v("error", "ids :");
        }

    }
    private void showJSON(String json) {

        ParseJSON pj = new ParseJSON(json);
        pj.parseRO();
    }
}
