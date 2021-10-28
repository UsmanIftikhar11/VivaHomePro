package com.viva.vivahomepro;

import com.viva.vivahomepro.R;
import com.google.android.material.appbar.AppBarLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;

public class ProStarScore extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pro_star_score);

        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar3);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar3);
        toolbar.setTitle("Star Score");

        setSupportActionBar(toolbar);

        //getSupportActionBar().setHomeAsUpIndicator(R.drawable.backarrowblue1);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
