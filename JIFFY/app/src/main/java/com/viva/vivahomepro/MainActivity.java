package com.viva.vivahomepro;

import android.content.Intent;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.viva.vivahomepro.R;

public class MainActivity extends AppCompatActivity {

    private static int splash = 1500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this , ProWelcome.class);
                startActivity(intent);
                finish();
            }
        }, splash );
    }
}
