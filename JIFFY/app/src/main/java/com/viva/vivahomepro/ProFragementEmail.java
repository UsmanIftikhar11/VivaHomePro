package com.viva.vivahomepro;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.viva.vivahomepro.R;

public class ProFragementEmail extends Fragment {

    private TextView textView /*, txtTerm */, txtByUsing;
    private ImageButton login ;
    private ImageView back ;
    private EditText et_proEmail;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.pro_fragement_email, container, false);


        textView = view.findViewById(R.id.txt_term);
        login = view.findViewById(R.id.img_btnLogin);
        et_proEmail = view.findViewById(R.id.et_proEmail);
        back = view.findViewById(R.id.arrowBack);
        txtByUsing = view.findViewById(R.id.txt_byUsing);
        /*txtTerm = view.findViewById(R.id.txt_term);

        txtTerm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity() , TermsOfUse.class);
                intent.putExtra("link" , "term");
                getActivity().startActivity(intent);
                getActivity().finish();
            }
        });*/

        //textView.setText(Html.fromHtml(String.format(getString(R.string.termofuse))));

        textView.setText(Html.fromHtml("<u>terms of use</u>."));
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://vivahomepros.com/terms.php";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        login.setOnClickListener(v -> {

            String email = et_proEmail.getText().toString().trim();

            if(!TextUtils.isEmpty(email)){

                ProFragmentPass fragPass = new ProFragmentPass();
                Bundle emailBundle = new Bundle();
                emailBundle.putString("email" , String.valueOf(email));
                fragPass.setArguments(emailBundle);
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, fragPass).addToBackStack(null);
                fragmentTransaction.commit();
            }
            else {
                Toast.makeText(getActivity() , "Please enter your email address" , Toast.LENGTH_LONG).show();
            }
        });

        back.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity() , ProWelcome.class);
            getActivity().startActivity(intent);
            getActivity().finish();
        });

        return view ;
    }
}
