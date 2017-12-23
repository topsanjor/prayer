package com.kotlab.tibetanbuddhistprayer.activities;

import android.content.Intent;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kotlab.tibetanbuddhistprayer.R;
import com.kotlab.tibetanbuddhistprayer.database.PechaDatabase;
import com.kotlab.tibetanbuddhistprayer.fragments.EnglishFragment;
import com.kotlab.tibetanbuddhistprayer.fragments.myprayer.MyPrayersFragment;
import com.kotlab.tibetanbuddhistprayer.fragments.TibetanFragment;
import com.kotlab.tibetanbuddhistprayer.pagerAdapter.ViewPagerAdapter;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;
    private Toolbar toolbar;
    private PechaDatabase pechaDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);


        setupToolbar(getResources().getString(R.string.hometitle));
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        setupTabLayout();
        createDB();


    }

    private void createDB() {

        pechaDatabase = new PechaDatabase(this);

    }


    private void setupToolbar(String tooltitle) {
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(tooltitle);

    }

    private void setupTabLayout() {
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(new TibetanFragment(), getResources().getString(R.string.tibtabtitle));
        viewPagerAdapter.addFragment(new EnglishFragment(), getResources().getString(R.string.engtabtitle));
        viewPagerAdapter.addFragment(new MyPrayersFragment(),getResources().getString(R.string.myprayertitle));
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        ViewGroup viewGroup = (ViewGroup) tabLayout.getChildAt(0);
           ViewGroup vgtab = (ViewGroup) viewGroup.getChildAt(0);

           View vgtabtxt= vgtab.getChildAt(1);
           if(vgtabtxt instanceof TextView){

               Typeface tfff =Typeface.createFromAsset(getAssets(),"fonts/nototibetanbold.ttf");
               ((TextView) vgtabtxt).setTypeface(tfff);
           }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        if (id == R.id.nav_contact) {
            startActivity(new Intent(this, ContactActivity.class));


        } else if (id == R.id.nav_about) {
            Intent intent = new Intent(this, AboutActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_otherapps) {
            Intent intent = new Intent(this, OtherApps.class);
            startActivity(intent);

        } else if (id == R.id.nav_share) {

            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT,
                    "Hey check out my app ");
            sendIntent.setType("text/plain");
            startActivity(sendIntent);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
