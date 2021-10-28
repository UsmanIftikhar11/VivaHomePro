package com.viva.vivahomepro;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.viva.vivahomepro.R;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;


public class ProWelcome extends AppCompatActivity {

    private static ViewPager mPager;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    private static final Integer[] IMAGES= {R.drawable.s1,R.drawable.s2,R.drawable.s3};
    private ArrayList<Integer> ImagesArray = new ArrayList<Integer>();

    private static final String[] TEXT= {"Quickly and easily request reviews from your recent clients.","Connect with homeowners looking for local pros just like you.","Track and manage your JIFFY profile from anywhere."};
    private ArrayList<String> TextArray = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pro_welcome);

        //FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        /*if (user != null) {
            // User is signed in
            Intent i = new Intent(ProWelcome.this, ProHome.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
        } else {
            // User is signed out
            //Log.d(TAG, "onAuthStateChanged:signed_out");
        }*/

        Button btn_login = findViewById(R.id.btn_login);

        btn_login.setOnClickListener(v -> {
            Intent intent = new Intent(ProWelcome.this , ProLoginNew.class);
            startActivity(intent);
//            finish();
        });

        TextView txt_signUp2 = findViewById(R.id.txt_signUp2);
        txt_signUp2.setOnClickListener(v -> {
//            Intent intent = new Intent(ProWelcome.this , ProSignUp.class);
//            startActivity(intent);
//            finish();
            String url = "http://vivahomepros.com/pro.php";
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
        });

        init();
    }

    private void init() {
        for(int i=0;i<IMAGES.length;i++) {
            ImagesArray.add(IMAGES[i]);
            TextArray.add(TEXT[i]);
        }

        mPager = findViewById(R.id.pager);

        mPager.setAdapter(new SlidingImage_Adapter(ProWelcome.this,ImagesArray , TextArray));


        //CirclePageIndicator indicator = (CirclePageIndicator) findViewById(R.id.indicator);

        //dotsIndicator.setViewPager(mPager);
        //dotsIndicator.setViewPager(mPager);

        //final float density = getResources().getDisplayMetrics().density;

        //Set circle indicator radius
        //indicator.setRadius(5 * density);

        NUM_PAGES =IMAGES.length;

        // Auto start of viewpager
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == NUM_PAGES) {
                    currentPage = 0;
                }
                mPager.setCurrentItem(currentPage++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 3000, 3000);

        // Pager listener over indicator
        /*indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                currentPage = position;

            }

            @Override
            public void onPageScrolled(int pos, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int pos) {

            }
        });*/
    }
}
