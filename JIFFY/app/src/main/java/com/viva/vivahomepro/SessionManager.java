package com.viva.vivahomepro;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.HashMap;

public class SessionManager {

    SharedPreferences sharedPreferences ;
    public  SharedPreferences.Editor editor ;
    public Context context ;
    int PRIVATE_MODE = 0;

    private static final String PREF_NAME = "LOGIN";
    private static final String LOGIN = "IS_LOGIN" ;
    public static final String COMP_NAME = "COMP_NAME";
    public static final String PRO_ID = "PRO_ID";
    public static final String BRAND_BUILDER = "BRAND_BUILDER";
    public static final String CATEGORY = "CATEGORY";

    public SessionManager(Context context) {
        this.context = context;

        sharedPreferences = context.getSharedPreferences(PREF_NAME , PRIVATE_MODE);
        editor = sharedPreferences.edit();

    }

    public void createSession(String compName , String proID, String brandBuilder , String category){

        editor.putBoolean(LOGIN , true);
        editor.putString(COMP_NAME , compName);
        editor.putString(PRO_ID , proID);
        editor.putString(BRAND_BUILDER , brandBuilder);
        editor.putString(CATEGORY , category);
        editor.apply();
    }

    public boolean isLogin(){

        return sharedPreferences.getBoolean(LOGIN , false);
    }

    public void checkLogin(){

        if(!this.isLogin()){

            Intent i = new Intent(context , ProLoginNew.class);
            context.startActivity(i);
            ((ProHome) context).finish();
        }
    }

    /*public HashMap<String , String> getUserDetails(){

        HashMap<String , String> user = new HashMap<>();
        user.put(COMP_NAME , sharedPreferences.getString(COMP_NAME , null));
        user.put(PRO_ID , sharedPreferences.getString(PRO_ID , null));
        user.put(BRAND_BUILDER , sharedPreferences.getString(BRAND_BUILDER , null));

        return user;
    }*/

    public ArrayList<String> getUserDetails(){

        ArrayList<String> user = new ArrayList<>();
        user.add(sharedPreferences.getString(PRO_ID , null));
        user.add(sharedPreferences.getString(COMP_NAME , null));
        user.add(sharedPreferences.getString(BRAND_BUILDER , null));
        user.add(sharedPreferences.getString(CATEGORY , null));

        return user;
    }

    public void logout(){

        editor.clear();
        editor.commit();
        Intent i = new Intent(context , ProLoginNew.class);
        context.startActivity(i);
        ((ProHome) context).finish();
    }

}
