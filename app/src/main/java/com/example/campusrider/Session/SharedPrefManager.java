package com.example.campusrider.Session;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.campusrider.Model.User;

public class SharedPrefManager {
    private static String SHARED_PREF_NAME="campusRider";
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    Context context;

    public SharedPrefManager(Context context) {
        this.context = context;
    }

    public void saveUser(User user){
        sharedPreferences=context.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();
        editor.putInt("rider_id",user.getRider_id());
        editor.putString("rider_name",user.getRider_name());
        editor.putInt("rider_phone",user.getRider_phone());
        editor.putString("rider_email",user.getRider_email());
        editor.putString("area ",user.getArea());
        editor.putString("rider_password",user.getRider_password());
        editor.putString("rider_image",user.getRider_image());
        editor.putInt("rider_status",user.getRider_status());
        editor.putBoolean("logged",true);
        editor.apply();
    }

    public boolean isLoggedIn(){
        sharedPreferences=context.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean("logged",false);
    }

    public User getUser(){
        sharedPreferences=context.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return new User(sharedPreferences.getInt("rider_id",-1),
                sharedPreferences.getString("rider_name",null),
                sharedPreferences.getInt("rider_phone",-1),
                sharedPreferences.getString("rider_email",null),
                sharedPreferences.getString("area",null),
                sharedPreferences.getString("rider_password",null),
                sharedPreferences.getString("rider_image",null),
                sharedPreferences.getInt("rider_status",-1)
        );
    }
    public void logout(){
        sharedPreferences=context.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}
