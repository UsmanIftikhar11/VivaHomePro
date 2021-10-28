package com.viva.vivahomepro;

import android.content.Intent;
import androidx.annotation.NonNull;

import com.viva.vivahomepro.R;
import com.google.android.material.appbar.AppBarLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
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

public class ProReplyTemplateDelete extends AppCompatActivity {

    private String title, body , ProId ;

    private EditText et_templateTitle , et_templateBody;
    private Button btn_saveReply;

    private DatabaseReference mDatabse ;
    private DatabaseReference mDatabseUsers ;
    //private FirebaseAuth mAuth ;
    //private FirebaseUser mCurrentUser ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pro_reply_template_delete);

        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar1);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar1);
        toolbar.setTitle("New Template");

        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.close);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        title = getIntent().getExtras().getString("title");
        body = getIntent().getExtras().getString("body");
        ProId = getIntent().getExtras().getString("id");

        et_templateBody = findViewById(R.id.et_templateBody);
        et_templateTitle = findViewById(R.id.et_templateTitle);
        btn_saveReply = findViewById(R.id.btn_saveReply);

        et_templateTitle.setText(title);
        et_templateBody.setText(body);

        //mAuth = FirebaseAuth.getInstance();
        //mCurrentUser = mAuth.getCurrentUser();

        mDatabse = FirebaseDatabase.getInstance().getReference().child("ReplyTemplate").child("01");

        mDatabseUsers = FirebaseDatabase.getInstance().getReference().child("ProData").child("01");

        btn_saveReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startPosting();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.pro_template_delete , menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.action_deleteTemplate:
                //Intent intent = new Intent(ProReplyTemplateDelete.this , ProReplyTemplateCreate.class);
                //startActivity(intent);
                //finish();
                mDatabse.child(ProId).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Intent intent = new Intent(ProReplyTemplateDelete.this , ProReplyTemplates.class);
                        startActivity(intent);
                        finish();
                    }
                });
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void startPosting() {

        final String title_string = et_templateTitle.getText().toString().toLowerCase().trim();
        final String body_string = et_templateBody.getText().toString().trim();


        if(!TextUtils.isEmpty(title_string) && !TextUtils.isEmpty(body_string) ) {

            final DatabaseReference newProduct = mDatabse.child(ProId);

            mDatabseUsers.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    newProduct.child("Title").setValue(title_string);
                    newProduct.child("Body").setValue(body_string).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if(task.isSuccessful()){
                                //startActivity(new Intent(CompanyAddProduct.this  , CompanyHome.class));
                                Intent intent = new Intent(ProReplyTemplateDelete.this  , ProReplyTemplates.class);
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

