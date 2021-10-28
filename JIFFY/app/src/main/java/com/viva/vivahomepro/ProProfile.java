package com.viva.vivahomepro;

import android.content.DialogInterface;
import android.content.Intent;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.viva.vivahomepro.R;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ProProfile extends AppCompatActivity {

    private RelativeLayout relativeLayout1, relativeLayout2, relativeLayout3, relativeLayout4;
    private RelativeLayout layoutSuccess , layoutProfile , layoutReply , layoutCategory , layoutVacation , layoutUsers , layoutUpgrade,
            layoutChangeComp , layoutHelp ;
    private FirebaseAuth mAuth;

    private ImageView img_compCover , img_compProfile ;
    private TextView txt_compName1 , txtNA;
    private RelativeLayout logout;

    private String profile , cover , userId;

//    private DatabaseReference mDatabase;
    private ImageSlider imageSlider ;
    private ArrayList<SlideModel> imageList;

    private final String URL_PRODUCTS = "https://vivahomepros.com/mobile-app/pro-profile.php";
    ArrayList<String> serviceList;
    ArrayList<Integer> scoreList;
    private SessionManager sessionManager;
    private ArrayList<String> proDetails;
    private String compName , brandBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pro_profile);

        //mAuth = FirebaseAuth.getInstance();
        //userId = mAuth.getCurrentUser().getUid();

        sessionManager = new SessionManager(this);
        proDetails = new ArrayList<>();
        proDetails = sessionManager.getUserDetails();
        compName = proDetails.get(1);
        brandBuilder = proDetails.get(2);

        serviceList = new ArrayList<>();
        scoreList = new ArrayList<>();

        img_compCover = findViewById(R.id.img_companyCover);
        img_compProfile = findViewById(R.id.img_companyLogo);

//        mDatabase = FirebaseDatabase.getInstance().getReference().child("ProData").child("01");

//        mDatabase.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//
//                profile = String.valueOf(dataSnapshot.child("Image1").getValue());
//                cover = String.valueOf(dataSnapshot.child("Image2").getValue());
//                compName = String.valueOf(dataSnapshot.child("CompanyName").getValue());
//
//                Picasso.get().load(profile).into(img_compProfile);
//                Picasso.get().load(cover).into( img_compCover);
//
//                txt_compName1.setText(compName);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });

        //Blurry.with(this).radius(25).sampling(2).onto((ViewGroup) findViewById(R.id.blur));

        relativeLayout1 = findViewById(R.id.review5);
        relativeLayout2 = findViewById(R.id.leads5);
        relativeLayout3 = findViewById(R.id.getReview5);
        relativeLayout4 = findViewById(R.id.gallery5);

        layoutSuccess = findViewById(R.id.layoutSuccess);
        layoutProfile = findViewById(R.id.layoutProfile);
        layoutReply = findViewById(R.id.layoutReply);
        layoutCategory = findViewById(R.id.layoutCategory);
        layoutVacation = findViewById(R.id.layoutVacation);
        layoutUsers = findViewById(R.id.layoutUsers);
        layoutChangeComp = findViewById(R.id.layoutChangeComp);
        layoutHelp = findViewById(R.id.layoutHelp);
        layoutUpgrade = findViewById(R.id.layoutUpgrade);

        if (brandBuilder.equals("Yes"))
            layoutUpgrade.setVisibility(View.GONE);
        else
            layoutUpgrade.setVisibility(View.VISIBLE);

        txt_compName1 = findViewById(R.id.txt_companyName1);
        txtNA = findViewById(R.id.txtNA);
        logout = findViewById(R.id.layoutLogout);

        txt_compName1.setText(compName);

        imageList = new ArrayList<>();
        imageList.add(new SlideModel(R.drawable.ls1, true));
        imageList.add(new SlideModel(R.drawable.ls2, true));
        imageList.add(new SlideModel(R.drawable.ls3, true));
        imageList.add(new SlideModel(R.drawable.ls4, true));
        imageList.add(new SlideModel(R.drawable.ls5, true));
        imageList.add(new SlideModel(R.drawable.ls6, true));
        imageList.add(new SlideModel(R.drawable.ls7, true));
        imageList.add(new SlideModel(R.drawable.ls8, true));
        imageList.add(new SlideModel(R.drawable.ls9, true));
        imageSlider = findViewById(R.id.image_slider);
        imageSlider.setImageList(imageList, true);


        /*RelativeLayout layoutRating = findViewById(R.id.layoutRating);
        layoutRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProProfile.this , ProStarScore.class);
                startActivity(intent);
            }
        });*/

        layoutSuccess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProProfile.this , ProSuccessTracker.class);
                startActivity(intent);
                //finish();
            }
        });
        layoutProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProProfile.this , ProViewProfile.class);
                startActivity(intent);
                //finish();
            }
        });
        layoutReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProProfile.this , ProReplyTemplates.class);
                startActivity(intent);
                //finish();
            }
        });
        layoutCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProProfile.this , ProCatAndTasks.class);
                startActivity(intent);
                finish();
            }
        });
        layoutVacation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProProfile.this , ProVacationMode.class);
                startActivity(intent);
                //finish();
            }
        });
        layoutUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProProfile.this , ProUsers.class);
                startActivity(intent);
                //finish();
            }
        });
        layoutChangeComp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProProfile.this , ProChangeCompany.class);
                startActivity(intent);
                //finish();
            }
        });
        layoutHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProProfile.this , ProGetHelp.class);
                startActivity(intent);
                //finish();
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.getInstance().signOut();

                Intent intent = new Intent(ProProfile.this , ProLoginNew.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });
        relativeLayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProProfile.this , ProHome.class);
                startActivity(intent);
                finish();
            }
        });

        relativeLayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProProfile.this , ProLeads.class);
                startActivity(intent);
                finish();
            }
        });

        relativeLayout3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProProfile.this , ProGetRreviews.class);
                startActivity(intent);
                //finish();
            }
        });

        relativeLayout4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProProfile.this , ProGallery.class);
                startActivity(intent);
                finish();
            }
        });

        loadProducts();
    }

    private void loadProducts() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_PRODUCTS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //converting the string to json array object
//                            JSONObject object = new JSONObject(response);
//                                serviceList.add(object.getString("tname"));

                            JSONObject object = new JSONObject(response);
                            JSONArray array  = object.getJSONArray("data");
                            JSONObject object1 = array.getJSONObject(0);
                            JSONArray array1  = object1.getJSONArray("score");
                            //JSONArray array = new JSONArray(response);
                            Log.d("dlaa", "active : "+String.valueOf(array));
                            //traversing through all the object
                            scoreList.clear();
                            for (int i = 0; i < array1.length(); i++) {
                                //getting product object from json array
                                JSONObject product = array1.getJSONObject(i);
                                //adding the product to product list
                                scoreList.add(Integer.valueOf(product.getString("avgscore")));
                            }

                            JSONObject object2 = new JSONObject(response);
                            JSONArray array2  = object2.getJSONArray("data");
                            JSONObject object3 = array2.getJSONObject(1);
                            JSONArray array3  = object3.getJSONArray("profile");
                            //JSONArray array = new JSONArray(response);
                            //traversing through all the object
                            for (int i = 0; i < array3.length(); i++) {
                                //getting product object from json array
                                JSONObject product = array3.getJSONObject(i);
                                //adding the product to product list
                                serviceList.add(product.getString("ppic"));
                                serviceList.add(product.getString("pcover"));
                                Glide
                                        .with(ProProfile.this)
                                        .load(serviceList.get(0))
                                        .apply(new RequestOptions().override(600, 600))
                                        .placeholder(R.drawable.profile1mip)
                                        .into(img_compProfile);
                                Glide
                                        .with(ProProfile.this)
                                        .load(serviceList.get(1))
                                        .apply(new RequestOptions().override(600, 600))
                                        .placeholder(R.drawable.profile1mip)
                                        .into(img_compCover);
                            }

//                            Toast.makeText(getApplicationContext(), serviceList.toString(), Toast.LENGTH_LONG).show();
//                            Toast.makeText(getApplicationContext(), scoreList.toString(), Toast.LENGTH_LONG).show();
                            int totalScore = addscore(scoreList).get(1)*10;
                            int obtainedScore = addscore(scoreList).get(0);
                            double starScore = (1.0f* obtainedScore/totalScore)*100;
                            String s = String.format("%.2f", starScore);
                            Log.d("starScore", "total score : "+totalScore + "  obtainedScore = " + obtainedScore + "  starScore = " + starScore + "  size = " + scoreList.size());
                            Log.d("starScore", "scoreList : " + scoreList);
                            txtNA.setText(s+"%");
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Error1:  "+e, Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Error:  "+error, Toast.LENGTH_SHORT).show();
                    }
                })

        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params = new HashMap<>();
                params.put("pid", "11");
//                params.put("category", "Basement Renovation");

                return params;
            }
        };

        //adding our stringrequest to queue
        Volley.newRequestQueue(getApplicationContext()).add(stringRequest);
    }

    public ArrayList<Integer> addscore(ArrayList<Integer> m){
        ArrayList<Integer> arrayList = new ArrayList<>();
        int obtained = 0;
        int total;
        for( total = 0; total < m.size(); total++)
            obtained += m.get(total);
        arrayList.add(obtained);
        arrayList.add(total);

        return arrayList;
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Do you want to logout ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                mAuth.getInstance().signOut();

                Intent intent = new Intent(ProProfile.this , ProLoginNew.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();

            }
        });

        final AlertDialog ad = builder.create();
        ad.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {



        return super.onCreateOptionsMenu(menu);
    }
}
