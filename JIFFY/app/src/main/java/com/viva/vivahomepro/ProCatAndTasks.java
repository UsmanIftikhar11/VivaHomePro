package com.viva.vivahomepro;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.viva.vivahomepro.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ProCatAndTasks extends AppCompatActivity {

    private CheckBox check, check1 , check11, check12;
    private boolean c = true;
    private boolean c1 = true;
    private boolean c11 = true;
    private boolean c12 = true;

    private final String URL_PRODUCTS = "http://192.168.10.8/vivahomepro/cat-and-tasks.php";

    //a list to store all the products
    ArrayList<String> serviceList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pro_cat_and_tasks);

        ImageView imgClose = findViewById(R.id.imgClose);
        check = findViewById(R.id.imgCheck);
        check1 = findViewById(R.id.imgCheck1);
        //check11 = findViewById(R.id.imgCheck11);
        //check12 = findViewById(R.id.imgCheck12);

        serviceList = new ArrayList<>();

        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProCatAndTasks.this , ProProfile.class);
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
                            JSONArray array  = object.getJSONArray("login");
                            //JSONArray array = new JSONArray(response);
                            Log.d("dlaa", "active : "+String.valueOf(array));

                            //traversing through all the object
                            for (int i = 0; i < array.length(); i++) {

                                //getting product object from json array
                                JSONObject product = array.getJSONObject(i);

                                //adding the product to product list
                                serviceList.add(product.getString("tname"));
                            }

                            Toast.makeText(getApplicationContext(), serviceList.toString(), Toast.LENGTH_LONG).show();
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
                params.put("category", "Basement Renovation");

                return params;
            }
        };

        //adding our stringrequest to queue
        Volley.newRequestQueue(getApplicationContext()).add(stringRequest);
    }


    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        Intent intent = new Intent(ProCatAndTasks.this , ProProfile.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }
}
