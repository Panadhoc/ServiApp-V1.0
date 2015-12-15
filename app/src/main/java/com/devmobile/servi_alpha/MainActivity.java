package com.devmobile.servi_alpha;

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager myVP;
    private int[] iconTab= {
            R.mipmap.ic_local_offer_white_24dp,
            R.mipmap.ic_people_white_24dp,
            R.mipmap.ic_local_offer_black_24dp,
            R.mipmap.ic_people_black_24dp
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        myVP = (ViewPager) findViewById(R.id.pager);
        setupViewPager(myVP);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(myVP);

        setupTabIcons();

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
    private void setupViewPager(ViewPager viewPager) {
        MainPagerAdapter adapter = new MainPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new FragmentOne());
        adapter.addFragment(new FragmentTwo());
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

            }
        });
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.menu_search).getActionView();
        ComponentName cn = new ComponentName(this, SearchResultActivity.class);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(cn));

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
}

