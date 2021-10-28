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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ProReplyTemplateCreate extends AppCompatActivity {

    private EditText et_templateTitle , et_templateBody;
    private Button btn_saveReply;

    private DatabaseReference mDatabse ;
    private DatabaseReference mDatabseUsers ;
    //private FirebaseAuth mAuth ;
    //private FirebaseUser mCurrentUser ;

    private ArrayList<String> proDetails;
    private SessionManager sessionManager;
    private String proId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pro_reply_template_create);

        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar1);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar1);
        toolbar.setTitle("New Template");

        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.close);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        proDetails = new ArrayList<>();
        sessionManager= new SessionManager(this);
        proDetails = sessionManager.getUserDetails();
        proId = proDetails.get(0);

        et_templateBody = findViewById(R.id.et_templateBody);
        et_templateTitle = findViewById(R.id.et_templateTitle);
        btn_saveReply = findViewById(R.id.btn_saveReply);

        //mAuth = FirebaseAuth.getInstance();
        //mCurrentUser = mAuth.getCurrentUser();

        mDatabse = FirebaseDatabase.getInstance().getReference().child("ReplyTemplate");

        mDatabseUsers = FirebaseDatabase.getInstance().getReference().child("ProData").child(proId);

        btn_saveReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startPosting();
            }
        });

    }

    private void startPosting() {

        final String title_string = et_templateTitle.getText().toString().toLowerCase().trim();
        final String body_string = et_templateBody.getText().toString().trim();


        if(!TextUtils.isEmpty(title_string) && !TextUtils.isEmpty(body_string) ){


                    final DatabaseReference newProduct = mDatabse.child(proId).push();

                    mDatabseUsers.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            // String s = String.valueOf(dataSnapshot.child("name").getValue());
                            // final DatabaseReference newProduct = mDatabse.push();

                            newProduct.child("Title").setValue(title_string);
                            newProduct.child("Body").setValue(body_string);
                            newProduct.child("ProfId").setValue(proId).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if(task.isSuccessful()){
                                        //startActivity(new Intent(CompanyAddProduct.this  , CompanyHome.class));
                                        Intent intent = new Intent(ProReplyTemplateCreate.this  , ProReplyTemplates.class);
                                        startActivity(intent);

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
}
