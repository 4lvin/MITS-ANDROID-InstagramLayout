package com.example.alpin.bottomnavigation;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by alpin on 07/08/17.
 */

public class PagerAdapter extends FragmentStatePagerAdapter {

    int numbOfTabs;
    private boolean enabled;

    public PagerAdapter(FragmentManager fm, int mNumbOfTabsumb) {
        super(fm);
        this.numbOfTabs = mNumbOfTabsumb;
    }


    @Override
    public int getCount() {
        return numbOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new CameraFragment().newInstance("SecondFragment, Instance 1");
            case 1:
                return new HomeFragment();
            case 2:
               return new InboxFragment();
        }

        return null;
}

}

