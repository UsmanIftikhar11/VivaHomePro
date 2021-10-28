package com.viva.vivahomepro;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.text.Html;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.security.ProviderInstaller;
import com.viva.vivahomepro.R;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class ProFragmentPass extends Fragment {

    private TextView textView , txt_forgotpass ;
    private CheckBox showPass;
    private EditText et_propass;
    private ImageButton img_btnLogin ;
    private ImageView back1 ;
    private String email;
    private FirebaseAuth mAuth ;
    private DatabaseReference mDatabase ;
    private ProgressDialog mProgress ;

    SessionManager sessionManager;

    private static final String URL_LOGIN = "https://vivahomepros.com/mobile-app/login.php";
//    private static String URL_LOGIN = "http://172.16.7.186/vivahomepro/login.php";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.pro_fragment_pass, container, false);

//        try {
//            ProviderInstaller.installIfNeeded(getActivity());
//        } catch (Exception e) {
//            e.getMessage();
//        }

        sessionManager = new SessionManager(getActivity());

        email = getArguments().getString("email");

        Toast.makeText(getActivity(), email, Toast.LENGTH_SHORT).show();

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("ProData");
        mDatabase.keepSynced(true);
        mProgress = new ProgressDialog(getActivity());

        textView = view.findViewById(R.id.txt_forgotPass);
        showPass = view.findViewById(R.id.showPass);
        et_propass = view.findViewById(R.id.et_proPass);
        img_btnLogin = view.findViewById(R.id.img_btnLogin1);
        back1 = view.findViewById(R.id.arrowBack1);

        txt_forgotpass = view.findViewById(R.id.txt_forgotPass);
        txt_forgotpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(getActivity() , ProForgotPass.class);
//                getActivity().startActivity(intent);
//                getActivity().finish();
                String url = "https://vivahomepros.com/pro-forget-password.php";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        //textView.setText(Html.fromHtml(String.format(getString(R.string.termofuse))));

        textView.setText(Html.fromHtml("<p><u>Forgot Password?</u></p>"));

        showPass.setOnCheckedChangeListener((buttonView, isChecked) -> {

            if(isChecked){
                et_propass.setTransformationMethod(null);
                //et_propass.setSelection(end);
                et_propass.setSelection(et_propass.length());
            }else {
                et_propass.setTransformationMethod(new PasswordTransformationMethod());
                //et_propass.setSelection(end);
                et_propass.setSelection(et_propass.length());
            }
        });

        back1.setOnClickListener(v -> {

            ProFragementEmail fragEmail = new ProFragementEmail();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragEmail);
            fragmentTransaction.commit();
        });

        img_btnLogin.setOnClickListener(v -> {
            checkLogin();
           /* Intent intent = new Intent(getActivity(), ProHome.class);
            startActivity(intent);
            getActivity().finish();*/
        });

        return view ;
    }

    private void checkLogin() {

        String pass = et_propass.getText().toString().trim();

        if(!TextUtils.isEmpty(pass)){

            mProgress.setMessage("Checking Login...");
            mProgress.show();

            HttpsTrustManger.allowAllSSL();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_LOGIN,
                    response -> {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("login");

                            if(success.equals("1")){

                                getActivity().getSharedPreferences("AppPrefs" , Context.MODE_PRIVATE).edit().putString("proEmail" , email).apply();

                                for(int i=0 ; i<jsonArray.length(); i++){

                                    JSONObject object = jsonArray.getJSONObject(i);
                                    String proID = object.getString("pid").trim();
                                    String compName = object.getString("cname").trim();
                                    String brandBuilderStatus = object.getString("bbuild").trim();
                                    String category = object.getString("category").trim();

                                    //Toast.makeText(getContext() , "Login Successful\n" + proID+CompName+BrandBuilderStatus, Toast.LENGTH_LONG).show();

                                    mProgress.dismiss();

                                    Intent mainIntent = new Intent(getActivity() , ProHome.class);
                                    mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                                    mainIntent.putExtra("pid" , proID);
//                                    mainIntent.putExtra("cname" , compName);
//                                    mainIntent.putExtra("bbStatus" , brandBuilderStatus);
//                                    mainIntent.putExtra("category" , category);
                                    startActivity(mainIntent);

                                    sessionManager.createSession(compName, proID, brandBuilderStatus , category);
                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();

                            Toast.makeText(getContext() , "Error222:" + e.toString(), Toast.LENGTH_LONG).show();
                        }
                    },
                    error -> {

                        Toast.makeText(getContext() , "Error111:" + error.toString(), Toast.LENGTH_LONG).show();
                    })
            {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {

                    Map<String,String> params = new HashMap<>();
                    params.put("email", email);
                    params.put("pass", pass);

                    return params;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
            requestQueue.add(stringRequest);

        }

        else {

            Toast.makeText(getContext() , "Please enter a valid password", Toast.LENGTH_LONG).show();
        }
/*if(!TextUtils.isEmpty(password)){

            mProgress.setMessage("Checking Login...");
            mProgress.show();

            mAuth.signInWithEmailAndPassword(email , password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if(task.isSuccessful()){

                        mProgress.dismiss();
                        checkUserExist();
                        //Intent mainIntent = new Intent(MainActivity.this , Home.class);
                        //mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        //startActivity(mainIntent);
                    }
                    else {
                        mProgress.dismiss();
                        Toast.makeText(getActivity() , "Email or Password is incorrect " , Toast.LENGTH_LONG).show();
                    }
                }
            });
        }else {
            Toast.makeText(getActivity() , "Please enter password" , Toast.LENGTH_LONG).show();
        }*/

    }

    private void checkUserExist() {

        final String user_id = mAuth.getCurrentUser().getUid();
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot.child(user_id).hasChild("Image1")){

                    Intent mainIntent = new Intent(getActivity() , ProHome.class);
                    mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(mainIntent);

                }
                else {

                    Toast.makeText(getActivity() , "User Not found \n Create account first... " , Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


}
