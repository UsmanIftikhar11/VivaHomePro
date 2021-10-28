package com.viva.vivahomepro;

import android.content.DialogInterface;
import android.content.Intent;

import com.viva.vivahomepro.R;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import com.google.firebase.auth.FirebaseAuth;

public class ProHome extends AppCompatActivity {

    private RelativeLayout relativeLayout2, relativeLayout3, relativeLayout4, relativeLayout5;
    private FirebaseAuth mAuth;

    private ActionBarDrawerToggle mtoogle;
    private Toolbar mtoolbar , toolbar;
    private AppBarLayout appBarLayout;
    private TabLayout tab;
    private ViewPager.OnPageChangeListener indicator;

    private SectionsPagerAdapter mSectionsPagerAdapter;

    private ViewPager mViewPager;

    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pro_home);

        /*sessionManager = new SessionManager(this);
        sessionManager.checkLogin();*/

        mAuth = FirebaseAuth.getInstance();

        //String id = mAuth.getCurrentUser().getUid();

        relativeLayout2 = findViewById(R.id.leads1);
        relativeLayout3 = findViewById(R.id.getReview1);
        relativeLayout4 = findViewById(R.id.gallery1);
        relativeLayout5 = findViewById(R.id.profile1);

        tab = (TabLayout) findViewById(R.id.tabs);
        appBarLayout = (AppBarLayout) findViewById(R.id.appbar);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.backarrowblue1);
        toolbar.setTitle("Reviews");
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tab.getTabAt(0).setText("All");
        tab.getTabAt(1).setText("Drafts");
        tab.getTabAt(2).setText("Sent");
        tab.getTabAt(3).setText("Published");

        relativeLayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProHome.this , ProLeads.class);
                startActivity(intent);
                finish();
            }
        });

        relativeLayout3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProHome.this , ProGetRreviews.class);
                startActivity(intent);
                //finish();
            }
        });

        relativeLayout4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProHome.this , ProGallery.class);
                startActivity(intent);
                finish();
            }
        });

        relativeLayout5.setOnClickListener(v -> {
            Intent intent = new Intent(ProHome.this , ProProfile.class);
            startActivity(intent);
            finish();
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.prohome , menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.action_review:
                Intent intent = new Intent(ProHome.this , ProGetRreviews.class);
                startActivity(intent);
                //finish();
                break;
            case android.R.id.home:
                // todo: goto back activity from here

                /*Intent intent1 = new Intent(CurrentActivity.this, MainActivity.class);
                intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent1);
                finish();
                break;*/
                final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Do you want to logout ?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        //mAuth.getInstance().signOut();

                        sessionManager.logout();

                        Intent intent = new Intent(ProHome.this , ProLoginNew.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();

                    }
                });

                final AlertDialog ad = builder.create();
                ad.show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Do you want to logout ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                //mAuth.getInstance().signOut();

                sessionManager.logout();

                Intent intent = new Intent(ProHome.this , ProLoginNew.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();

            }
        });

        final AlertDialog ad = builder.create();
        ad.show();
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getItemPosition(Object object) {

            return super.getItemPosition(object);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            // return PlaceholderFragment.newInstance(position + 1);
            switch (position) {
                case 0:
                    FragmentAll all = new FragmentAll();
                    return all;

                case 1:
                    FragmentDraft draft = new FragmentDraft();
                    return draft;
                case 2:
                    FragmentSent sent = new FragmentSent();
                    return sent;
                case 3:
                    FragmentPublished published = new FragmentPublished();
                    return  published;
                default:
                    return null;
            }

        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 4;
        }
    }
}
