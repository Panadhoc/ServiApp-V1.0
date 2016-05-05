package com.devmobile.servi_alpha;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity implements MyFragment.OnCardSelectedListener{

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager myVP;
    boolean flag= true;
    private FragmentOne F1= new FragmentOne();
    private FragmentTwo F2= new FragmentTwo();
    private int[] iconTab= {
            R.mipmap.ic_local_offer_white_24dp,
            R.mipmap.ic_people_white_24dp,
            R.mipmap.ic_local_offer_black_24dp,
            R.mipmap.ic_people_black_24dp
    };

    public static  String JSON_USERS_URL ;
    public static  String JSON_POSTS_URL ;
    private List<Person> persons;
    private List<Post> offers;
    String filename="token.txt";
    String token,id,password;
    FloatingActionButton  add;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.v("error", "content view set");
        add=(FloatingActionButton) findViewById(R.id.fab);
        add.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), NewPostActivity.class);
                startActivity(intent);

            }
        });
        Log.v("error","onclicklistener added");

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        File file = new File(getFilesDir(), filename);

        try{
            Scanner sc = new Scanner(file);
            id=sc.nextLine();
            token=sc.nextLine();
            password=sc.nextLine();
            Globals g = new Globals(token,id);
            Globals.set_pass(password);

        } catch (Exception e){
            e.printStackTrace();
        }

        JSON_USERS_URL =Globals.address+":8080/servi/app/user/featuredusers?id="+ id + "&token="+token+"&max=5";
        JSON_POSTS_URL =Globals.address+":8080/servi/app/user/featuredposts?id="+ id + "&token="+token+"&max=5";
        Log.v("error",id+" "+token);
        sendRequest();
        findViewById(android.R.id.content).invalidate();

    }

    @Override
    public void onBackPressed() {
        // disable going back to the LoginActivity
        moveTaskToBack(true);
    }

    private void setupTabIcons(){

        tabLayout.getTabAt(0).setIcon(iconTab[0]);
        tabLayout.getTabAt(1).setIcon(iconTab[3]);
    }
    private void setupViewPager(ViewPager viewPager, FragmentOne F1, FragmentTwo F2) {
        MainPagerAdapter adapter = new MainPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(F1);
        adapter.addFragment(F2);
        viewPager.setAdapter(adapter);
        myVP.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    getSupportActionBar().setTitle("Offers");
                    tabLayout.getTabAt(0).setIcon(iconTab[0]);
                    tabLayout.getTabAt(1).setIcon(iconTab[3]);

                }
                if (position == 1) {
                    getSupportActionBar().setTitle("Professionals");
                    tabLayout.getTabAt(1).setIcon(iconTab[1]);
                    tabLayout.getTabAt(0).setIcon(iconTab[2]);

                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                flag=!flag;
            }
        });
    }




    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        if (flag) {
            SearchManager searchManager =
                    (SearchManager) getSystemService(Context.SEARCH_SERVICE);
            SearchView searchView =
                    (SearchView) menu.findItem(R.id.menu_search).getActionView();
            ComponentName cn = new ComponentName(this, UserResultActivity.class);
            searchView.setSearchableInfo(searchManager.getSearchableInfo(cn));
        }
        else
        {SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
            SearchView searchView =
                    (SearchView) menu.findItem(R.id.menu_search).getActionView();
            ComponentName cn = new ComponentName(this, PostResultActivity.class);
            searchView.setSearchableInfo(searchManager.getSearchableInfo(cn));
        }
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_logout:
                //traitement logout
                // 1. Instantiate an AlertDialog.Builder with its constructor
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        String filename="token.txt";
                        File file = new File(getFilesDir(), filename);
                        file.delete();
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(intent);

                        // User clicked OK button
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                        dialog.dismiss();
                    }
                });

// 2. Chain together various setter methods to set the dialog characteristics
                        builder.setMessage(R.string.dialog_message)
                        .setTitle(R.string.dialog_title);

// 3. Get the AlertDialog from create()
                AlertDialog dialog = builder.create();
                dialog.show();
                return true;
            case R.id.action_profile:
                //traitement profil
                Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_about:
               // traitement a propos
                AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
                builder1.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        dialog.dismiss();
                    }
                });
                builder1.setMessage(R.string.dialog_about)
                        .setTitle("About US");
                AlertDialog dialog1 = builder1.create();
                dialog1.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onPersonSelected(int position, int value) {

        if (value==1){

            Intent intent = new Intent(getApplicationContext(), Info_Activity.class);
            intent.putExtra("value",1);
            intent.putExtra("username", persons.get(position).username);
            intent.putExtra("address",persons.get(position).address);
            intent.putExtra("phone",persons.get(position).phone);
            startActivity(intent);
        }
        else
        {

            Intent intent = new Intent(getApplicationContext(), Info_Activity.class);
            intent.putExtra("value",0);
            intent.putExtra("title",offers.get(position).title);
            intent.putExtra("desc",offers.get(position).desc);
            intent.putExtra("postid",offers.get(position).postid);
            intent.putExtra("owner",offers.get(position).owner);
            startActivity(intent);
        }

    }
    private void sendRequest(){
        StringRequest stringRequest1 = new StringRequest(JSON_POSTS_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        showJSON(response, "offer");
                        initializePData();
                        Bundle args1 = new Bundle();
                        for (int i = 0; i < offers.size(); i++) {

                            args1.putInt("len",offers.size());
                            args1.putString("postID" + i, offers.get(i).postid);
                            args1.putString("title"+i, offers.get(i).title);
                            args1.putString("desc"+i,offers.get(i).desc);
                            args1.putString("owner"+i,offers.get(i).owner);
                            args1.putInt("photoID"+i, offers.get(i).photoID);

                        }
                        F1.setArguments(args1);
                    }},
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

        StringRequest stringRequest2 = new StringRequest(JSON_USERS_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        showJSON(response,"user");
                        initializeUData();
                        Bundle args2 = new Bundle();
                        for (int i=0;i<persons.size();i++) {

                            args2.putInt("len",persons.size());
                            args2.putString("userID" + i, persons.get(i).userId);
                            args2.putString("userName" + i, persons.get(i).username);
                            args2.putString("address"+i, persons.get(i).address);
                            args2.putString("phone"+i,persons.get(i).phone);
                            args2.putInt("photoID" + i, persons.get(i).photoID);
                            args2.putString("service"+i, persons.get(i).service);

                        }
                        F2.setArguments(args2);



                        Log.v("response", response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(stringRequest1);
        requestQueue.add(stringRequest2);
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {

                        myVP = (ViewPager) findViewById(R.id.pager);
                        setupViewPager(myVP, F1, F2);
                        tabLayout = (TabLayout) findViewById(R.id.tabs);
                        tabLayout.setupWithViewPager(myVP);
                        setupTabIcons();
                    }
                }, 3000);

    }
    private void showJSON(String json,String opt){
        ParseJSON pj = new ParseJSON(json);
        if (opt.equals("user")) {
            pj.parseFU();
        }
        else
            pj.parseFO();
    }
    private void initializeUData() {
        persons = new ArrayList<Person>();

        try {
            for (int i = 0; i < ParseJSON.userids.length; i++) {

                persons.add(new Person(ParseJSON.usernames[i], ParseJSON.userids[i], R.drawable.prof));
                persons.get(i).address=ParseJSON.addresses[i];
                persons.get(i).phone=ParseJSON.phones[i];
                persons.get(i).service=ParseJSON.services[i];
            }
        } catch (Exception e) {

           persons.add(new Person("Souhail","12",R.drawable.prof));
                    Log.v("error", "ids :");
        }
    }
    private void initializePData() {
        offers = new ArrayList<Post>();
        try {
            for (int i = 0; i < ParseJSON.postids.length; i++) {

                offers.add(new Post(ParseJSON.posttitles[i],ParseJSON.postids[i],

                                    ParseJSON.postdescs[i],ParseJSON.postowners[i], R.drawable.tournevis));
            }
        } catch (Exception e) {
            offers.add(new Post("id","titre","description","17",R.drawable.tournevis));
            Log.v("error", "ids :");
        }
    }
}

