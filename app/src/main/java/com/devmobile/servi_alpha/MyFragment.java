package com.devmobile.servi_alpha;

import android.os.Bundle;
import android.support.v4.app.Fragment;



public class MyFragment extends Fragment {


    public MyFragment(){

    }
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public interface OnCardSelectedListener {
        void onPersonSelected(int position,int value);
    }
}
