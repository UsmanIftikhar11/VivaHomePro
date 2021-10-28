package com.viva.vivahomepro;

import android.content.DialogInterface;
import android.content.Intent;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.viva.vivahomepro.R;
import com.google.android.material.appbar.AppBarLayout;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ProGallery extends AppCompatActivity {

    private RelativeLayout relativeLayout1, relativeLayout2, relativeLayout3, relativeLayout5;

//    private FirebaseAuth mAuth;
    private final String URL_PRODUCTS = " https://vivahomepros.com/mobile-app/gallery.php?apicall=getpics";

    private SessionManager sessionManager;
    private ArrayList<String> proDetails;
    private String brandBuilder;
    private RelativeLayout rlBrandBuilder;

    ArrayList<GalleryModel> serviceList;
    GridView gridView;
    private String proId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pro_gallery);

//        mAuth = FirebaseAuth.getInstance();
        sessionManager = new SessionManager(this);
        proDetails = new ArrayList<>();
        proDetails = sessionManager.getUserDetails();
        brandBuilder = proDetails.get(2);
        proId = proDetails.get(0);


        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar3);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar3);
        toolbar.setTitle("Galleries");

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.backarrowblue1);

        relativeLayout1 = findViewById(R.id.review4);
        relativeLayout2 = findViewById(R.id.leads4);
        relativeLayout3 = findViewById(R.id.getReview4);
        relativeLayout5 = findViewById(R.id.profile4);
        rlBrandBuilder = findViewById(R.id.rlBrandBuilder);
        gridView = findViewById(R.id.gvImages);

        Log.d("brand", "brand builder : "+brandBuilder);
        if (brandBuilder.equals("Yes"))
            rlBrandBuilder.setVisibility(View.GONE);
        else
            rlBrandBuilder.setVisibility(View.VISIBLE);

        serviceList = new ArrayList<>();

        relativeLayout1.setOnClickListener(v -> {
            Intent intent = new Intent(ProGallery.this , ProHome.class);
            startActivity(intent);
            finish();
        });

        relativeLayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProGallery.this , ProLeads.class);
                startActivity(intent);
                finish();
            }
        });

        relativeLayout3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProGallery.this , ProGetRreviews.class);
                startActivity(intent);
                //finish();
            }
        });

        relativeLayout5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProGallery.this , ProProfile.class);
                startActivity(intent);
                finish();
            }
        });
        loadProducts();
    }

    private void loadProducts() {
        HttpsTrustManger.allowAllSSL();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_PRODUCTS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //converting the string to json array object
                            JSONObject object = new JSONObject(response);
                            JSONArray array  = object.getJSONArray("images");
                            //JSONArray array = new JSONArray(response);
                            Log.d("dlaa", "active : "+String.valueOf(array));

                            //traversing through all the object
                            for (int i = 0; i < array.length(); i++) {

                                //getting product object from json array
                                JSONObject product = array.getJSONObject(i);

                                //adding the product to product list
                                serviceList.add(new GalleryModel(
                                        product.getString("id"),
                                        product.getString("tags"),
                                        product.getString("image"),
                                        product.getString("date")
                                ));
                            }

                            //creating adapter object and setting it to recyclerview
                            GalleryAdapter adpter= new GalleryAdapter(getApplicationContext(), serviceList);
                            gridView.setAdapter(adpter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(ProGallery.this, "Error1:  "+e, Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ProGallery.this, "Error:  "+error, Toast.LENGTH_SHORT).show();
                    }
                })

        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params = new HashMap<>();
                params.put("pid", proId);

                return params;
            }
        };

        //adding our stringrequest to queue
        Volley.newRequestQueue(ProGallery.this).add(stringRequest);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.pro_gallery , menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.action_openGallery:
                Toast.makeText(getApplicationContext() , "Gallery cannot be created on basic profile \n Upgrade to brand builder " , Toast.LENGTH_LONG).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Do you want to logout ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

//                mAuth.getInstance().signOut();

                Intent intent = new Intent(ProGallery.this , ProLoginNew.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
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
}
