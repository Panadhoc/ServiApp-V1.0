package com.devmobile.servi_alpha;


import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.Fragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Souhail on 25/11/2015.
 */
public class MainPagerAdapter extends FragmentPagerAdapter {

    private final List<Fragment> mFragmentList = new ArrayList<>();


    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    public MainPagerAdapter(FragmentManager F){

        super(F);

    }

    @Override
    public Fragment getItem(int position) {

        return mFragmentList.get(position);
    }

    public void addFragment(Fragment fragment) {
        mFragmentList.add(fragment);
    }


}
