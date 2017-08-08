package com.example.alpin.bottomnavigation;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import static com.example.alpin.bottomnavigation.R.id.pager;
import static com.example.alpin.bottomnavigation.R.id.visible;

public class MainActivity extends AppCompatActivity {


    private BottomNavigationView mBottomNav;
    PagerAdapter adapter;
    private int mSelectedItem;
    int numbofTabs = 3;
    boolean enabled;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.bar);
        setSupportActionBar(toolbar);

        adapter = new PagerAdapter(getSupportFragmentManager(), numbofTabs);
        viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(1, false);

        mBottomNav.getMaxItemCount();
        mBottomNav = (BottomNavigationView) findViewById(R.id.navigation);
        mBottomNav.setOnNavigationItemSelectedListener
                (new BottomNavigationView.OnNavigationItemSelectedListener() {

                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        selectFragment(item);
                        return true;
                    }
                });

        MenuItem selectedItem;
        if (savedInstanceState != null) {
            selectedItem = mBottomNav.getMenu().findItem(mSelectedItem);
        } else {
            selectedItem = mBottomNav.getMenu().getItem(0);
        }
        selectFragment(selectedItem);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.send);

        return true;
    }


    private void selectFragment(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_home:
                setSwipe(true);
                HomeFragment homeFragment = new HomeFragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, homeFragment)
                        .commit();
                break;
            case R.id.menu_gallery:
                setSwipe(false);
                Galleryragment galleryragment = new Galleryragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, galleryragment)
                        .commit();

                break;
            case R.id.menu_search:
                setSwipe(false);
                SearchFragment searchFragment = new SearchFragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, searchFragment)
                        .commit();
                break;
            case R.id.menu_favorite:
                setSwipe(false);
                FavoriteFragment favoriteFragment = new FavoriteFragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, favoriteFragment)
                        .commit();
                break;
            case R.id.menu_profil:
                setSwipe(false);
                ProfilFragment profilFragment = new ProfilFragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, profilFragment)
                        .commit();
                break;
        }

    }


    private void setSwipe(final boolean isswipe) {
        viewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return !isswipe;
            }
        });
    }

}

