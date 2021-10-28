package com.viva.vivahomepro;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.viva.vivahomepro.R;

public class ProLoginNew extends AppCompatActivity {

    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pro_login_new);

        //sessionManager = new SessionManager(this);
        //sessionManager.checkLogin();

        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container , new ProFragementEmail()).commit();
        }
    }

    @Override
    public void onBackPressed() {

        int count = getSupportFragmentManager().getBackStackEntryCount();

        if (count == 0) {
            //super.onBackPressed();
            //additional code
            Intent intent = new Intent(ProLoginNew.this , ProWelcome.class);
            startActivity(intent);
            finish();
        } else {
            getSupportFragmentManager().popBackStack();
        }

    }
}
