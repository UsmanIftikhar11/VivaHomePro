package com.viva.vivahomepro;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.Map;

public class FragmentServiceRequest extends Fragment {

    TextView txtService, txtYes, txtNo;
    boolean isServiceAccepted;
    private String leadId;

    private final String URL_PRODUCTS = " https://vivahomepros.com/mobile-app/service-request-detail.php";
    ArrayList<ServiceRequestModel> serviceList;
    private RecyclerView rvServiceRequestDetails;

    private ArrayList<String> proDetails;
    private SessionManager sessionManager;
    private String proId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        assert getArguments() != null;
        leadId = getArguments().getString("leadId");

        View v = inflater.inflate(R.layout.fragment_service_request, container, false);

        proDetails = new ArrayList<>();
        sessionManager= new SessionManager(getActivity());
        proDetails = sessionManager.getUserDetails();
        proId = proDetails.get(0);

        //getting the recyclerview from xml
        rvServiceRequestDetails = v.findViewById(R.id.rvServiceRequestDetails);
        /*cv1 = v.findViewById(R.id.cv1);
        cv2 = v.findViewById(R.id.cv2);*/
        rvServiceRequestDetails.setHasFixedSize(true);
        rvServiceRequestDetails.setLayoutManager(new LinearLayoutManager(getActivity()));

        //initializing the productlist
        serviceList = new ArrayList<>();

        loadProducts();

        txtService = v.findViewById(R.id.txtService);
        txtYes = v.findViewById(R.id.txtYes);
        txtNo = v.findViewById(R.id.txtNo);

        isServiceAccepted = requireActivity().getSharedPreferences("AUTHENTICATION_FILE_NAME", Context.MODE_PRIVATE).getBoolean(leadId, false);

        setVisbilities(isServiceAccepted);

        txtYes.setOnClickListener(v1 -> {
            txtYes.setVisibility(View.GONE);
            txtNo.setVisibility(View.GONE);
            txtService.setVisibility(View.GONE);
            isServiceAccepted = true;
            requireActivity().getSharedPreferences("AUTHENTICATION_FILE_NAME", Context.MODE_PRIVATE).edit().putBoolean(leadId, isServiceAccepted).apply();
        });

        txtNo.setOnClickListener(v1 -> {
            txtYes.setVisibility(View.GONE);
            txtNo.setVisibility(View.GONE);
            txtService.setVisibility(View.GONE);
            isServiceAccepted = false;
            requireActivity().getSharedPreferences("AUTHENTICATION_FILE_NAME", Context.MODE_PRIVATE).edit().putBoolean(leadId, isServiceAccepted).apply();
        });


        return v;
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
                            JSONArray array  = object.getJSONArray("srdetails");
                            //JSONArray array = new JSONArray(response);

                            //traversing through all the object
                            for (int i = 0; i < array.length(); i++) {

                                //getting product object from json array
                                JSONObject product = array.getJSONObject(i);

                                //adding the product to product list
                                serviceList.add(new ServiceRequestModel(
                                        product.getString("ques")
                                ));
                            }

                            //creating adapter object and setting it to recyclerview
                            ServiceRequestAdapter adapter = new ServiceRequestAdapter(getActivity(), serviceList);
                            rvServiceRequestDetails.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getActivity(), "Error1:  "+e, Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), "Error:  "+error, Toast.LENGTH_SHORT).show();
                    }
                })

        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params = new HashMap<>();
                params.put("leadid", leadId);

                return params;
            }
        };

        //adding our stringrequest to queue
        Volley.newRequestQueue(getActivity()).add(stringRequest);
    }

    private void setVisbilities(boolean isServiceAccepted) {
        if (isServiceAccepted) {
            txtYes.setVisibility(View.GONE);
            txtNo.setVisibility(View.GONE);
            txtService.setVisibility(View.GONE);
        } else {
            txtYes.setVisibility(View.VISIBLE);
            txtNo.setVisibility(View.VISIBLE);
            txtService.setVisibility(View.VISIBLE);
        }
    }

}
