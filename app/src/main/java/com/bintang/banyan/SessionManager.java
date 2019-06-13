package com.bintang.banyan;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.bintang.banyan.Activity.Main.MainActivity;
import com.bintang.banyan.Activity.SplashActivity;

import java.util.HashMap;

public class SessionManager {
    public static final String NAME = "NAME";
    public static final String EMAIL = "EMAIL";
    public static final String TTL = "TTL";
    public static final String ALAMAT = "ALAMAT";
    public static final String NOTELP = "NOTELP";
    public static final String ID = "ID";

    private static final String PREF_NAME = "LOGIN";
    private static final String LOGIN = "IS_LOGIN";
    public SharedPreferences.Editor editor;
    public Context context;
    SharedPreferences sharedPreferences;
    int PRIVATE_MODE = 0;

    public SessionManager(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);

        editor = sharedPreferences.edit();
    }

    public void createSession(String name, String email, String ttl, String alamat, String notelp, String id) {
        editor.putBoolean(LOGIN, true);
        editor.putString(NAME, name);
        editor.putString(EMAIL, email);
        editor.putString(TTL, ttl);
        editor.putString(ALAMAT, alamat);
        editor.putString(NOTELP, notelp);
        editor.putString(ID, id);
        editor.apply();
    }

    public boolean isLogin() {
        return sharedPreferences.getBoolean(LOGIN, false);
    }

    public void checkLogin() {
        if (!this.isLogin()) {
            this.logout();
            Intent intent = new Intent(context, SplashActivity.class);
            context.startActivity(intent);
            ((MainActivity) context).finish();
        }
    }

    public HashMap<String, String> getUserDetail() {
        HashMap<String, String> user = new HashMap<>();
        user.put(NAME, sharedPreferences.getString(NAME, null));
        user.put(EMAIL, sharedPreferences.getString(EMAIL, null));
        user.put(ID, sharedPreferences.getString(ID, null));

        return user;
    }

    public void clearSession() {
        editor.clear();
        editor.commit();
        editor.apply();
    }

    public void logout() {
        editor.clear();
        editor.commit();
        Intent intent = new Intent(context, SplashActivity.class);
        context.startActivity(intent);
        ((MainActivity) context).finish();
    }

}
