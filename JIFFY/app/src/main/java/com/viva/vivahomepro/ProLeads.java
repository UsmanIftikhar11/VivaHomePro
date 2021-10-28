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

import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.RelativeLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

public class ProLeads extends AppCompatActivity {

    private RelativeLayout relativeLayout1, relativeLayout3, relativeLayout4, relativeLayout5;
    private FirebaseAuth mAuth;

    private RecyclerView leadsList ;
    private DatabaseReference mDatabase , mDatabaseService ;
    private Query mQueryCurrentUser ;
    private static  String product_key , city , job , jobAndCity;

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
        setContentView(R.layout.activity_pro_leads);

        mAuth = FirebaseAuth.getInstance();

        /*AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        toolbar.setTitle("Leads");

        setSupportActionBar(toolbar);*/

        tab = findViewById(R.id.tabs);
        appBarLayout = findViewById(R.id.appbar2);
        toolbar = findViewById(R.id.toolbar2);
        toolbar.setTitle("Leads");

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.backarrowblue1);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = findViewById(R.id.container);

        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setOffscreenPageLimit(3);


        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tab.getTabAt(0).setText("Active");
        tab.getTabAt(1).setText("Cancel");
        tab.getTabAt(2).setText("Completed");

        relativeLayout1 = findViewById(R.id.review2);
        relativeLayout3 = findViewById(R.id.getReview2);
        relativeLayout4 = findViewById(R.id.gallery2);
        relativeLayout5 = findViewById(R.id.profile2);

        relativeLayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProLeads.this , ProHome.class);
                startActivity(intent);
                finish();
            }
        });

        relativeLayout3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProLeads.this , ProGetRreviews.class);
                startActivity(intent);
                //finish();
            }
        });

        relativeLayout4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProLeads.this , ProGallery.class);
                startActivity(intent);
                finish();
            }
        });

        relativeLayout5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProLeads.this , ProProfile.class);
                startActivity(intent);
                finish();
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();

       /* String curretnUserId = mAuth.getCurrentUser().getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("ProData").child(curretnUserId);
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {



                leadsList = findViewById(R.id.leadsList);
                leadsList.setHasFixedSize(true);
                leadsList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

                city = String.valueOf(dataSnapshot.child("City").getValue());
                job = String.valueOf(dataSnapshot.child("JobCategory").getValue());
                jobAndCity = city+"_"+job;

                mDatabaseService = FirebaseDatabase.getInstance().getReference().child("Service Request");
                mQueryCurrentUser = mDatabaseService.orderByChild("Location and Job").equalTo(jobAndCity);

                FirebaseRecyclerAdapter<FirebaseVariables , MyLeadsViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<FirebaseVariables, MyLeadsViewHolder>(

                        FirebaseVariables.class ,
                        R.layout.pro_service_list ,
                        MyLeadsViewHolder.class ,
                        mQueryCurrentUser
                ) {
                    @Override
                    protected void populateViewHolder(final MyLeadsViewHolder viewHolder, final FirebaseVariables model, final int position) {

                        product_key = getRef(position).getKey();

                        viewHolder.setDesc(model.getProjectDesc());
                        viewHolder.setTime(model.getEstTime());
                        viewHolder.setBudget(model.getBudget());

                        viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                            @Overridel
                            public void onClick(View v) {
                                Intent intent = new Intent(ProLeads.this , Chat.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                finish();
                            }
                        });


                    }
                };*/
                //leadsList.setAdapter(firebaseRecyclerAdapter);


            //}

            //@Override
            //public void onCancelled(DatabaseError databaseError) {

            //}
        //});
    }

    /*public static class MyLeadsViewHolder extends RecyclerView.ViewHolder{

        View mView;
        Context context ;
        CheckBox mark ;

        public MyLeadsViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            context = itemView.getContext();

        }

        public void setDesc(String desc){
            TextView txt_projDesc = (TextView)mView.findViewById(R.id.txt_projDesc1);
            txt_projDesc.setText(desc);
        }

        public void setTime(String time){
            TextView txt_estTime = (TextView)mView.findViewById(R.id.txt_estTime1);
            txt_estTime.setText("Estimated Time:" + time);
        }

        public void setBudget(String budget){
            TextView txt_budget = (TextView)mView.findViewById(R.id.txt_budget1);
            txt_budget.setText("Budget = $" + budget);
        }
    }*/

    @Override
    public void onBackPressed() {
        //super.onBackPressed();

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Do you want to logout ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                /*mAuth.getInstance().signOut();

                Intent intent = new Intent(ProLeads.this , ProLoginNew.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);*/
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
                    return new FragmentActive();
                case 1:
                    return new FragmentCompleted();
                case 2:
                    return new FragmentCancel();
                default:
                    return null;
            }

        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }
    }
}
