package com.viva.vivahomepro;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.appbar.AppBarLayout;

import java.util.ArrayList;

public class ProUsers extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pro_users);

        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Manage Users");

        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.backarrowblue1);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView txtUserName = findViewById(R.id.txtUserName);
        TextView txtUserEmail = findViewById(R.id.txtUserEmail);
        TextView txtYou = findViewById(R.id.txtYou);

        String email = getSharedPreferences("AppPrefs" , Context.MODE_PRIVATE).getString("proEmail" , "user@test.com");
        SessionManager sessionManager = new SessionManager(ProUsers.this);
        ArrayList<String> userData;
        userData = sessionManager.getUserDetails();

        txtUserName.setText(userData.get(1));
        txtUserEmail.setText(email);
        txtYou.setText("This is you");


    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.pro_gallery , menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }*/

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        Intent intent = new Intent(ProUsers.this , ProProfile.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }
}
