package com.example.alpin.bottomnavigation;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;
import java.util.Vector;

import static com.example.alpin.bottomnavigation.R.id.pager;

/**
 * Created by alpin on 06/08/17.
 */

public class HomeFragment extends Fragment {

    public static HomeFragment newInstance() {
        HomeFragment f = new HomeFragment();
        return f;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);



        return view;
    }


}
