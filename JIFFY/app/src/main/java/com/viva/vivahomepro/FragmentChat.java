package com.viva.vivahomepro;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
import com.viva.vivahomepro.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class FragmentChat extends Fragment {

    Button btn_send;
    EditText etChat;
    ImageView imgChat;
    TextView txtTitle;
    RecyclerView rvChat;
    private String leadId , userName , pId , uId;

    private final String URL_PRODUCTS = " https://vivahomepros.com/mobile-app/chat.php?chatcall=getmsg";
    ArrayList<ChatModel> serviceList;

    private ArrayList<String> proDetails;
    private SessionManager sessionManager;
    private String proId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        assert getArguments() != null;
        leadId = getArguments().getString("leadId");
        userName = getArguments().getString("userName");
        pId = getArguments().getString("pId");
        uId = getArguments().getString("uId");

        View v = inflater.inflate(R.layout.fragment_chat, container, false);

        btn_send = v.findViewById(R.id.btn_send);
        etChat = v.findViewById(R.id.et_chat);
        imgChat = v.findViewById(R.id.imgChat);
        txtTitle = v.findViewById(R.id.txtTitle);
        rvChat = v.findViewById(R.id.rvChat);

        proDetails = new ArrayList<>();
        sessionManager= new SessionManager(getContext());
        proDetails = sessionManager.getUserDetails();
        proId = proDetails.get(0);

        //getting the recyclerview from xml
        rvChat = v.findViewById(R.id.rvChat);
        /*cv1 = v.findViewById(R.id.cv1);
        cv2 = v.findViewById(R.id.cv2);*/

        rvChat.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false));
        rvChat.setHasFixedSize(true);

        //initializing the productlist
        serviceList = new ArrayList<>();



        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadProducts();

        txtTitle.setText(userName);

        Toast.makeText(getActivity(), leadId + "\n" + pId + "\n" + uId, Toast.LENGTH_LONG).show();

        btn_send.setOnClickListener(v1 -> {

        });
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

                            //traversing through all the object
                            for (int i = 0; i < array.length(); i++) {

                                //getting product object from json array
                                JSONObject product = array.getJSONObject(i);

                                //adding the product to product list
                                serviceList.add(new ChatModel(
                                        product.getString("mid"),
                                        product.getString("leadid"),
                                        product.getString("pid"),
                                        product.getString("uid"),
                                        product.getString("message"),
                                        product.getString("mdate"),
                                        product.getString("whosend"),
                                        product.getString("attachment")
                                ));
                            }
//                            List<ChatModel> chatModels=new ArrayList<>();
//                            ChatModel model = new ChatModel("","","","","","","","");
                            //creating adapter object and setting it to recyclerview
                            ChatAdapter adapter = new ChatAdapter(getContext(), serviceList);
                            rvChat.setAdapter(adapter);
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
                params.put("uid", uId);
                params.put("pid", proId);

                return params;
            }
        };

        //adding our stringrequest to queue
        Volley.newRequestQueue(getContext()).add(stringRequest);
    }

}
