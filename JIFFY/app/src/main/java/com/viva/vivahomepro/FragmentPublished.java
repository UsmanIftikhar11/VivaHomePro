package com.viva.vivahomepro;

import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.viva.vivahomepro.R;

import java.util.ArrayList;

public class FragmentPublished extends Fragment {

    private ImageView img_fragmentAll;
    private TextView allSent;

    private DatabaseReference mDatabase , mDatabase1;
    //private FirebaseUser mCurrentUsers;
    private Query mQueryCurrentUser ;
    //private FirebaseAuth mAuth;
    //private FirebaseAuth.AuthStateListener mAuthListner ;

    private RecyclerView allReqList ;
    private static  String product_key ;
    private ArrayList<String> proDetails;
    private SessionManager sessionManager;
    private String proId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_published, container, false);

        img_fragmentAll = view.findViewById(R.id.img_fragmentAll);
        allSent = view.findViewById(R.id.allSent);

        proDetails = new ArrayList<>();
        sessionManager= new SessionManager(getActivity());
        proDetails = sessionManager.getUserDetails();
        proId = proDetails.get(0);

        allReqList = view.findViewById(R.id.allReqList);
        allReqList.setHasFixedSize(true);
        allReqList.setLayoutManager(new LinearLayoutManager(getActivity()));

        //mAuth = FirebaseAuth.getInstance();
        mDatabase1 = FirebaseDatabase.getInstance().getReference().child("ReviewRequest").child(proId);
        mQueryCurrentUser = mDatabase1.orderByChild("ReqStatus").equalTo("Sent");
        //String curretnUserId = mAuth.getCurrentUser().getUid();

        //mCurrentUsers = mAuth.getCurrentUser();

        try {
            mDatabase = FirebaseDatabase.getInstance().getReference().child("ReviewRequest");
            mDatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    if (dataSnapshot.hasChild(proId)) {
                        //btn_template.setClickable(false);
                        img_fragmentAll.setVisibility(View.INVISIBLE);
                        allSent.setVisibility(View.INVISIBLE);
                    } else {

                        //btn_template.setClickable(true);
                        img_fragmentAll.setVisibility(View.VISIBLE);
                        allSent.setVisibility(View.VISIBLE);
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

        FirebaseRecyclerAdapter<FirebaseVariables , MyAllReqViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<FirebaseVariables, MyAllReqViewHolder>(

                FirebaseVariables.class ,
                R.layout.review_request_list ,
                MyAllReqViewHolder.class ,
                mQueryCurrentUser
        ) {
            @Override
            protected void populateViewHolder(MyAllReqViewHolder viewHolder, final FirebaseVariables model, final int position) {

                product_key = getRef(position).getKey();

                viewHolder.setReqDate(model.getJobDate());
                viewHolder.setReqEmail(model.getClientEmail());
                viewHolder.setReqStatus(model.getReqStatus());
                viewHolder.setReqTitle(model.getJobTitle());

            }
        };
        allReqList.setAdapter(firebaseRecyclerAdapter);

        return view;
    }

    public static class MyAllReqViewHolder extends RecyclerView.ViewHolder{

        View mView;
        Context context ;

        public MyAllReqViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            context = itemView.getContext();
        }

        public void setReqDate(String reqDate){
            TextView txt_projDesc = (TextView)mView.findViewById(R.id.txtReqDate);
            txt_projDesc.setText(reqDate);
        }

        public void setReqTitle(String reqTitle){
            TextView txt_estTime = (TextView)mView.findViewById(R.id.txtReqTitle);
            txt_estTime.setText(reqTitle);
        }

        public void setReqStatus(String reqStatus){
            TextView txt_estTime = (TextView)mView.findViewById(R.id.txtReqStatus);
            txt_estTime.setText(reqStatus);
        }

        public void setReqEmail(String reqEmail){
            TextView txt_estTime = (TextView)mView.findViewById(R.id.txtReqEmail);
            txt_estTime.setText(reqEmail);
        }
    }
}
