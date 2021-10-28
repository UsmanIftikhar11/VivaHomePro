package com.viva.vivahomepro;

import android.content.Intent;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.viva.vivahomepro.R;
import com.google.android.material.appbar.AppBarLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class ProSuccessTracker extends AppCompatActivity {

    private final String URL_PRODUCTS = "https://vivahomepros.com/mobile-app/success-tracker.php";
    ArrayList<String> serviceList;

    TextView txt_month , txtPercent , txtListingNo , txtConnectionNo , txtWebsiteNo , txtPhoneNo , txtLeadsNo , txtProfileNo , txtHomestarNo;
    TextView txtPercent1 , txtListingNo1 , txtConnectionNo1 , txtWebsiteNo1 , txtPhoneNo1 , txtLeadsNo1 , txtProfileNo1 , txtHomestarNo1;

    private ArrayList<String> proDetails;
    private SessionManager sessionManager;
    private String proId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pro_success_tracker);

        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Success Tracker");

        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.backarrowblue1);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txt_month = findViewById(R.id.txt_month);
        txtPercent = findViewById(R.id.txtPercent);
        txtListingNo = findViewById(R.id.txtListingNo);
        txtConnectionNo = findViewById(R.id.txtConnectionNo);
        txtWebsiteNo = findViewById(R.id.txtWebsiteNo);
        txtPhoneNo = findViewById(R.id.txtPhoneNo);
        txtLeadsNo = findViewById(R.id.txtLeadsNo);
        txtProfileNo = findViewById(R.id.txtProfileNo);
        txtHomestarNo = findViewById(R.id.txtHomestarNo);
        txtPercent1 = findViewById(R.id.txtPercent1);
        txtListingNo1 = findViewById(R.id.txtListingNo1);
        txtConnectionNo1 = findViewById(R.id.txtConnectionNo1);
        txtWebsiteNo1 = findViewById(R.id.txtWebsiteNo1);
        txtPhoneNo1 = findViewById(R.id.txtPhoneNo1);
        txtLeadsNo1 = findViewById(R.id.txtLeadsNo1);
        txtProfileNo1 = findViewById(R.id.txtProfileNo1);
        txtHomestarNo1 = findViewById(R.id.txtHomestarNo1);

        proDetails = new ArrayList<>();
        sessionManager= new SessionManager(this);
        proDetails = sessionManager.getUserDetails();
        proId = proDetails.get(0);

        Calendar cal=Calendar.getInstance();
        SimpleDateFormat month_date = new SimpleDateFormat("MMMM");
        String month_name = month_date.format(cal.getTime());

        String allcaps = "MONTH-TO-DATE, "+month_name;

        txt_month.setText(allcaps.toUpperCase());

        serviceList = new ArrayList<>();
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
                            //JSONArray array = new JSONArray(response);
//                            Log.d("dlaa", "active : "+String.valueOf(array));

                            //traversing through all the object
                            for (int i = 0; i < array.length(); i++) {

                                //getting product object from json array
                                JSONObject product = array.getJSONObject(i);

                                //adding the product to product list
                                txtListingNo1.setText(product.getString("pageall"));
                                txtWebsiteNo1.setText(product.getString("weball"));
                                txtPhoneNo1.setText(product.getString("phoneall"));
                                txtLeadsNo1.setText(product.getString("totalleadsall"));
                                txtHomestarNo1.setText(product.getString("systemleadsall"));
                                txtProfileNo1.setText(product.getString("userleadsall"));
                                txtConnectionNo1.setText(product.getString("connectionsall"));
                                txtListingNo.setText(product.getString("pageallmonth"));
                                txtWebsiteNo.setText(product.getString("weballmonth"));
                                txtPhoneNo.setText(product.getString("phoneallmonth"));
                                txtLeadsNo.setText(product.getString("totalleadsallmonth"));
                                txtHomestarNo.setText(product.getString("systemleadsallmonth"));
                                txtProfileNo.setText(product.getString("userleadsallmonth"));
                                txtConnectionNo.setText(product.getString("connectionsallmonth"));

                                float profileNo = Integer.parseInt(product.getString("userleadsallmonth"));
                                float listingNo = Integer.parseInt(product.getString("pageallmonth"));
                                float monthPercent = (profileNo/listingNo)*100;
                                String s = String.format("%.2f", monthPercent);
                                if (monthPercent>0)
                                    txtPercent.setText(s + "%");
                                else
                                    txtPercent.setText("0%");

                                float profileNo1 = Integer.parseInt(product.getString("userleadsall"));
                                float listingNo1 = Integer.parseInt(product.getString("pageall"));
                                float monthPercent1 = (profileNo1/listingNo1)*100;
                                String s1 = String.format("%.2f", monthPercent1);
                                if (monthPercent1>0)
                                    txtPercent1.setText(s1 + "%");
                                else
                                    txtPercent1.setText("0%");
                            }

//                            Toast.makeText(getApplicationContext(), serviceList.toString(), Toast.LENGTH_LONG).show();
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
//                params.put("pid", "11");
                params.put("pid", proId);

                return params;
            }
        };

        //adding our stringrequest to queue
        Volley.newRequestQueue(getApplicationContext()).add(stringRequest);
    }


    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        Intent intent = new Intent(ProSuccessTracker.this , ProProfile.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }
}
