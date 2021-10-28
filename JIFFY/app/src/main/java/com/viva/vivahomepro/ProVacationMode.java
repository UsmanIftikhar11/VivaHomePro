package com.viva.vivahomepro;

import android.app.DatePickerDialog;
import android.content.Intent;

import com.viva.vivahomepro.R;
import com.google.android.material.appbar.AppBarLayout;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;

import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class ProVacationMode extends AppCompatActivity {

    int mDay;
    int mMonth;
    int mYear;
    int mDay1;
    int mMonth1;
    int mYear1;
    String lastDate , firstDate ;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private SwitchCompat switchVacation;
    boolean vacation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pro_vacation_mode);

        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Vacation Mode");

        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.backarrowblue1);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sharedPreferences = getSharedPreferences("vacation" , 0);
        editor = sharedPreferences.edit();

        switchVacation = findViewById(R.id.switchVacation);
        switchVacation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    editor.putBoolean("vacation" , false);
                else
                    editor.putBoolean("vacation" , true);
            }
        });

        TextView etSecond = findViewById(R.id.etSecond);
        etSecond.setOnClickListener(v -> {
            Calendar mcurrentDate = Calendar.getInstance();

            mDay   = mcurrentDate.get(Calendar.DAY_OF_MONTH);
            mMonth = mcurrentDate.get(Calendar.MONTH);
            mYear  = mcurrentDate.get(Calendar.YEAR);

            vacation = sharedPreferences.getBoolean("vacation" , false);
            if (vacation){
                DatePickerDialog datePickerDialog = new DatePickerDialog(ProVacationMode.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month = month + 1;
                        //date = dayOfMonth + ":" + month + ":" + year;
                        lastDate = month+", "+ dayOfMonth+" "+year;
                        editor.putString("lastDate" , lastDate);
                        //SimpleDateFormat dateFormat = new SimpleDateFormat("EEE", Locale.US);
                        //String asWeek = dateFormat.format(date);
                        etSecond.setText(lastDate);
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        TextView etFirst = findViewById(R.id.etFirst);
        etFirst.setOnClickListener(v ->{
            Calendar mcurrentDate = Calendar.getInstance();

            mDay1   = mcurrentDate.get(Calendar.DAY_OF_MONTH);
            mMonth1 = mcurrentDate.get(Calendar.MONTH);
            mYear1  = mcurrentDate.get(Calendar.YEAR);

            vacation = sharedPreferences.getBoolean("vacation" , false);
            if (vacation){
                DatePickerDialog datePickerDialog = new DatePickerDialog(ProVacationMode.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month = month + 1;
                        //date = dayOfMonth + ":" + month + ":" + year;
                        firstDate = month+", "+ dayOfMonth+" "+year;
                        editor.putString("firstDate" , firstDate);
                        //SimpleDateFormat dateFormat = new SimpleDateFormat("EEE", Locale.US);
                        //String asWeek = dateFormat.format(date);
                        etFirst.setText(firstDate);
                    }
                }, mYear1, mMonth1, mDay1);
                datePickerDialog.show();
            }
        });
    }

    public static long getDateDiff(SimpleDateFormat format, String oldDate, String newDate) {
        try {
            return TimeUnit.DAYS.convert(format.parse(newDate).getTime() - format.parse(oldDate).getTime(), TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        Intent intent = new Intent(ProVacationMode.this , ProProfile.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }
}
