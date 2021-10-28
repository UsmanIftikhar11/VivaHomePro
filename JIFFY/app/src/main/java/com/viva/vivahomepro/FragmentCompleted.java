package com.viva.vivahomepro;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FragmentCompleted extends Fragment {

//    private final String URL_PRODUCTS = "http://192.168.10.8/vivahomepro/leads4.php";
private final String URL_PRODUCTS = " https://vivahomepros.com/mobile-app/leads4.php";

    List<ServiceComplete> serviceList2;

    //the recyclerview
    private RecyclerView rvServiceRequest2;

    private ArrayList<String> proDetails;
    private SessionManager sessionManager;
    private String proId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_completed, container, false);

        proDetails = new ArrayList<>();
        sessionManager= new SessionManager(getActivity());
        proDetails = sessionManager.getUserDetails();
        proId = proDetails.get(0);

        //getting the recyclerview from xml
        rvServiceRequest2 = v.findViewById(R.id.rvServiceRequest2);
        rvServiceRequest2.setHasFixedSize(true);
        rvServiceRequest2.setLayoutManager(new LinearLayoutManager(getActivity()));

        //initializing the productlist
        serviceList2 = new ArrayList<>();

        //this method will fetch and parse json
        //to display it in recyclerview
        loadProducts();

        return v;
    }

    private void loadProducts() {

        /*
         * Creating a String Request
         * The request type is GET defined by first parameter
         * The URL is defined in the second parameter
         * Then we have a Response Listener and a Error Listener
         * In response listener we will get the JSON response as a String
         * */
        HttpsTrustManger.allowAllSSL();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_PRODUCTS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //converting the string to json array object
                            JSONObject object = new JSONObject(response);
                            JSONArray array  = object.getJSONArray("message");
                            //JSONArray array = new JSONArray(response);

                            //traversing through all the object
                            for (int i = 0; i < array.length(); i++) {

                                //getting product object from json array
                                JSONObject product = array.getJSONObject(i);

//                                serviceList2.clear();
                                //adding the product to product list
                                serviceList2.add(new ServiceComplete(
                                        product.getString("tname"),
                                        product.getString("ul_name"),
                                        product.getString("ul_uid"),
                                        product.getString("lead_id"),
                                        product.getString("u_pic"),
                                        product.getString("date")
                                ));
                            }

//                            Log.d("dlaa", "Completed : "+String.valueOf(array));

                            //creating adapter object and setting it to recyclerview
                            ServiceCompleteAdapter adapter = new ServiceCompleteAdapter(getActivity(), serviceList2);
                            rvServiceRequest2.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getActivity(), "Error1:  "+e, Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "Error:  "+error, Toast.LENGTH_SHORT).show();
                    }
                })

        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params = new HashMap<>();
                params.put("pid", proId);
                params.put("tstatus", "Complete");

                return params;
            }
        };

        //adding our stringrequest to queue
        Volley.newRequestQueue(getActivity()).add(stringRequest);
    }
}
