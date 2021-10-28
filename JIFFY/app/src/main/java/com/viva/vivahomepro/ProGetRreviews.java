package com.viva.vivahomepro;

import android.content.Intent;
import androidx.annotation.NonNull;

import com.viva.vivahomepro.R;
import com.google.android.material.appbar.AppBarLayout;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.santalu.maskedittext.MaskEditText;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class ProGetRreviews extends AppCompatActivity {

    private MaskEditText et_clientPhone;
    private EditText et_clientEmail , et_jobDetails;
    private Button btn_send;

    private DatabaseReference mDatabse ;
    private DatabaseReference mDatabseUsers ;
    private FirebaseAuth mAuth ;
    private FirebaseUser mCurrentUser ;

    private ArrayList<String> proDetails;
    private SessionManager sessionManager;
    private String proId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pro_get_rreviews);


        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar1);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar1);
        toolbar.setTitle("Request a Review");

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.close);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        proDetails = new ArrayList<>();
        sessionManager= new SessionManager(this);
        proDetails = sessionManager.getUserDetails();
        proId = proDetails.get(0);

        et_clientEmail = findViewById(R.id.et_clientEmail);
        et_clientPhone = findViewById(R.id.et_clientPhone);
        et_jobDetails = findViewById(R.id.et_jobDetails);
        btn_send = findViewById(R.id.btn_send);

        //mAuth = FirebaseAuth.getInstance();
        //mCurrentUser = mAuth.getCurrentUser();

        mDatabse = FirebaseDatabase.getInstance().getReference().child("ReviewRequest");

        mDatabseUsers = FirebaseDatabase.getInstance().getReference().child("ProData").child(proId);

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startPosting();
            }
        });
    }

    private void startPosting() {

        final String email_string = et_clientEmail.getText().toString().toLowerCase().trim();
        final String phone_string = et_clientPhone.getText().toString().trim();
        final String title_string = et_jobDetails.getText().toString().trim();


        if(!TextUtils.isEmpty(title_string) && !TextUtils.isEmpty(email_string) && !TextUtils.isEmpty(phone_string) ){

            final String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

            final DatabaseReference newProduct = mDatabse.child(proId).push();

            mDatabseUsers.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    // String s = String.valueOf(dataSnapshot.child("name").getValue());
                    // final DatabaseReference newProduct = mDatabse.push();

                    newProduct.child("JobTitle").setValue(title_string);
                    newProduct.child("ClientPhone").setValue(phone_string);
                    newProduct.child("ClientEmail").setValue(email_string);
                    newProduct.child("ReqStatus").setValue("Sent");
                    newProduct.child("JobDate").setValue(date);
                    newProduct.child("ProfId").setValue(proId).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if(task.isSuccessful()){
                                //startActivity(new Intent(CompanyAddProduct.this  , CompanyHome.class));

                                final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
                                emailIntent.setType("text/plain");
                                emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, email_string);
                                emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, title_string);
                                emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Add Message here");


                                emailIntent.setType("message/rfc822");

                                try {
                                    startActivity(Intent.createChooser(emailIntent,
                                            "Send email using..."));
//                                    Intent intent = new Intent(ProGetRreviews.this  , ProHome.class);
//                                    startActivity(intent);
                                } catch (android.content.ActivityNotFoundException ex) {
                                    Toast.makeText(getApplicationContext(), "No email clients installed.", Toast.LENGTH_SHORT).show();
                                }


                            }
                        }
                    });
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }

        else {
            Toast.makeText(getApplicationContext() , "fill all fields" , Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();

        Intent intent = new Intent(ProGetRreviews.this , ProHome.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }
}
