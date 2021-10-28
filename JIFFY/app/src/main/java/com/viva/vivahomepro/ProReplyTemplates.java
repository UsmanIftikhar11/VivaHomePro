package com.viva.vivahomepro;

import android.content.Context;
import android.content.Intent;

import com.viva.vivahomepro.R;
import com.google.android.material.appbar.AppBarLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ProReplyTemplates extends AppCompatActivity {

    private Button btn_template;
    private TextView txt_reply;

    private DatabaseReference mDatabase , mDatabase1;
    //private FirebaseUser mCurrentUsers;
    //private FirebaseAuth mAuth;

    //private FirebaseAuth.AuthStateListener mAuthListner ;

    private RecyclerView replyList ;
    private Query mQueryCurrentUser ;
    private static  String product_key ;

    private ArrayList<String> proDetails;
    private SessionManager sessionManager;
    private String proId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pro_reply_templates);

        replyList = (RecyclerView)findViewById(R.id.replyTemplateList);
        replyList.setHasFixedSize(true);
        replyList.setLayoutManager(new LinearLayoutManager(this));

        proDetails = new ArrayList<>();
        sessionManager= new SessionManager(this);
        proDetails = sessionManager.getUserDetails();
        proId = proDetails.get(0);

        //mAuth = FirebaseAuth.getInstance();
        mDatabase1 = FirebaseDatabase.getInstance().getReference().child("ReplyTemplate").child(proId);
        //String curretnUserId = mAuth.getCurrentUser().getUid();

        //mCurrentUsers = mAuth.getCurrentUser();

        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Reply Templates");

        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.backarrowblue1);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        btn_template = findViewById(R.id.btnTemplate);
        txt_reply = findViewById(R.id.txtReply);

        try {
            mDatabase = FirebaseDatabase.getInstance().getReference().child("ReplyTemplate");
            mDatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    if (dataSnapshot.hasChild(proId)) {
                        btn_template.setClickable(false);
                        btn_template.setVisibility(View.INVISIBLE);
                        txt_reply.setVisibility(View.INVISIBLE);
                    } else {

                        btn_template.setClickable(true);
                        btn_template.setVisibility(View.VISIBLE);
                        txt_reply.setVisibility(View.VISIBLE);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        } catch (Exception e) {
            //e.printStackTrace();
            //Toast.makeText(getApplicationContext() , e.toString(), Toast.LENGTH_LONG).show();
        }

        //Toast.makeText(getApplicationContext() , curretnUserId , Toast.LENGTH_LONG).show();

        Button btnTemplate = findViewById(R.id.btnTemplate);
        btnTemplate.setOnClickListener(v -> {
            Intent intent = new Intent(ProReplyTemplates.this , ProReplyTemplateCreate.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<FirebaseVariables , MyReplyTemplateViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<FirebaseVariables, MyReplyTemplateViewHolder>(

                FirebaseVariables.class ,
                R.layout.reply_template_list ,
                MyReplyTemplateViewHolder.class ,
                mDatabase1
        ) {
            @Override
            protected void populateViewHolder(MyReplyTemplateViewHolder viewHolder, final FirebaseVariables model, final int position) {

                product_key = getRef(position).getKey();

                viewHolder.setTitle(model.getTitle());
                viewHolder.setBody(model.getBody());

                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(ProReplyTemplates.this , ProReplyTemplateDelete.class);
                        intent.putExtra("title" , model.getTitle());
                        intent.putExtra("body" , model.getBody());
                        intent.putExtra("id" , product_key);
                        startActivity(intent);
                        finish();
                    }
                });



            }
        };
        replyList.setAdapter(firebaseRecyclerAdapter);
    }

    public static class MyReplyTemplateViewHolder extends RecyclerView.ViewHolder{

        View mView;
        Context context ;

        public MyReplyTemplateViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            context = itemView.getContext();
        }

        public void setBody(String body){
            TextView txt_projDesc = (TextView)mView.findViewById(R.id.txtReplyBody);
            txt_projDesc.setText(body);
        }

        public void setTitle(String title){
            TextView txt_estTime = (TextView)mView.findViewById(R.id.txtReplyTitle);
            txt_estTime.setText(title);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.pro_template , menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.action_addTemplate:
                Intent intent = new Intent(ProReplyTemplates.this , ProReplyTemplateCreate.class);
                startActivity(intent);
                //finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        Intent intent = new Intent(ProReplyTemplates.this , ProProfile.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }
}
