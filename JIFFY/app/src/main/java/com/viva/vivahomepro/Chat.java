package com.viva.vivahomepro;

import android.content.Context;
import android.content.Intent;

import com.viva.vivahomepro.R;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import java.util.Objects;

public class Chat extends AppCompatActivity {

    Button btn_send;
    int counter = 0;

    RelativeLayout l5, l6, l7;
    EditText etChat;


    private ActionBarDrawerToggle mtoogle;
    private Toolbar mtoolbar , toolbar;
    private AppBarLayout appBarLayout;
    private TabLayout tab;
    private ViewPager.OnPageChangeListener indicator;

    private SectionsPagerAdapter mSectionsPagerAdapter;

    private CustomViewPager mViewPager;

    private static String leadId , userName , pId , uId;
    private boolean isServiceAccepted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        /*btn_send = findViewById(R.id.btn_send);
        l5 = findViewById(R.id.l5);
        l6 = findViewById(R.id.l6);
        l7 = findViewById(R.id.l7);
        etChat = findViewById(R.id.et_chat);

        btn_send.setOnClickListener(v1 -> {
            counter++;
            etChat.setText("");
            if(counter == 1){
                l5.setVisibility(View.VISIBLE);
            }
            if(counter == 2){
                l6.setVisibility(View.VISIBLE);
            }
            if(counter == 4){
                l7.setVisibility(View.VISIBLE);
            }

        });*/

        Intent intent = getIntent();

        if (intent!=null && intent.hasExtra("leadId")) {
            leadId = intent.getStringExtra("leadId");
            userName = intent.getStringExtra("userName");
            pId = intent.getStringExtra("pId");
            uId = intent.getStringExtra("uId");
        }

        isServiceAccepted = getSharedPreferences("AUTHENTICATION_FILE_NAME", Context.MODE_PRIVATE).getBoolean(leadId, false);


        tab = (TabLayout) findViewById(R.id.tabs);
        appBarLayout = (AppBarLayout) findViewById(R.id.appbar);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.backarrowblue1);
        toolbar.setTitle("Service Request");
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager =  findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        if (isServiceAccepted)
            mViewPager.disableScroll(false);
        else
            mViewPager.disableScroll(true);

//        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tab.setupWithViewPager(mViewPager);

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tab.getTabAt(0).setText("Service Request");
        tab.getTabAt(1).setText("Chat");

    }

    public static class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getItemPosition(@NonNull Object object) {

            return super.getItemPosition(object);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            // return PlaceholderFragment.newInstance(position + 1);
            switch (position) {
                case 0:
                    Bundle bundle = new Bundle();
                    bundle.putString("leadId", leadId);
                    FragmentServiceRequest fragmentServiceRequest = new FragmentServiceRequest();
                    fragmentServiceRequest.setArguments(bundle);
                    return fragmentServiceRequest;

                case 1:
                    Bundle bundle1 = new Bundle();
                    bundle1.putString("userName", userName);
                    bundle1.putString("leadId", leadId);
                    bundle1.putString("pId", pId);
                    bundle1.putString("uId", uId);
                    FragmentChat fragmentChat = new FragmentChat();
                    fragmentChat.setArguments(bundle1);
                    return fragmentChat;
                default:
                    return null;
            }

        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 2;
        }
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();

        Intent intent = new Intent(Chat.this , ProLeads.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.chat_menu , menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
