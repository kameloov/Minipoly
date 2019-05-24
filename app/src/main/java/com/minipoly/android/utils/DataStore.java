package com.minipoly.android.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.minipoly.android.entity.User;

import java.lang.reflect.Type;

public class DataStore {
    private static final String APP_PREFS = "APP_PREFS";
    private static final String LOGIN_INFO = "USER_DATA";
    private static final String CONTACT_INFO = "CONTACT_INFO";
    private static final String DEVICE_TOKEN = "DEVICE_TOKEN";
    private static Gson gson = new Gson();

    private static SharedPreferences getPrefs(Context context) {
        return context.getSharedPreferences(APP_PREFS, Context.MODE_PRIVATE);
    }

    private static <T> T load(Context context, Class<T> clazz, String key) {
        String json = getPrefs(context).getString(key, null);
        return json != null ? gson.fromJson(json, clazz) : null;
    }

    private static <T> T load(Context context, Type type, String key) {
        String json = getPrefs(context).getString(key, null);
        return json != null ? (T) gson.fromJson(json, type) : null;
    }

    private static void save(Context context, String key, Object object) {
        String s = gson.toJson(object);
        getPrefs(context).edit().putString(key, s).commit();
    }


    public static User getUserInfo(Context context) {
        return load(context, User.class, LOGIN_INFO);
    }

    public static void saveUserInfo(Context context, User user) {
        save(context, LOGIN_INFO, user);
    }

    public static void logout(Context context) {
        getPrefs(context).edit().remove(LOGIN_INFO).commit();
    }


    public static void saveContactData(Context context, String data) {
        getPrefs(context).edit().putString(CONTACT_INFO, data).commit();
    }

    public static void saveDeviceToken(Context context, String token) {
        getPrefs(context).edit().putString(DEVICE_TOKEN, token).commit();
    }

    public static String getDeviceToken(Context context) {
        return getPrefs(context).getString(DEVICE_TOKEN, "");
    }

    public static String getContactData(Context context) {
        return getPrefs(context).getString(CONTACT_INFO, "");
    }

}
