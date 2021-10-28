package com.viva.vivahomepro;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.appbar.AppBarLayout;

import java.util.ArrayList;

public class ProChangeCompany extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pro_change_company);

        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Change Company");

        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.backarrowblue1);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        TextView txtTitle = findViewById(R.id.txtTitle);

        SessionManager sessionManager = new SessionManager(ProChangeCompany.this);
        ArrayList<String> userData;
        userData = sessionManager.getUserDetails();

        txtTitle.setText(userData.get(1));
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        Intent intent = new Intent(ProChangeCompany.this , ProProfile.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }
}
