package com.minipoly.android.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.minipoly.android.entity.Category;
import com.minipoly.android.entity.User;

import java.lang.reflect.Type;
import java.util.List;

public class LocalData {
    private static final String APP_PREFS = "APP_PREFS";
    private static final String LOGIN_INFO = "USER_DATA";
    private static final String CONTACT_INFO = "CONTACT_INFO";
    private static final String DEVICE_TOKEN = "DEVICE_TOKEN";
    private static final String CATEGORIES = "CATEGORIES";
    private static Gson gson = new Gson();
    private static Context context;

    public static void init(Context c) {
        context = c;
    }

    private static SharedPreferences getPrefs() {
        return context.getSharedPreferences(APP_PREFS, Context.MODE_PRIVATE);
    }

    private static <T> T load(Class<T> clazz, String key) {
        String json = getPrefs().getString(key, null);
        return json != null ? gson.fromJson(json, clazz) : null;
    }

    private static <T> T load(Type type, String key) {
        String json = getPrefs().getString(key, null);
        return json != null ? (T) gson.fromJson(json, type) : null;
    }

    private static void save(String key, Object object) {
        String s = gson.toJson(object);
        getPrefs().edit().putString(key, s).commit();
    }


    public static User getUserInfo() {
        return load(User.class, LOGIN_INFO);
    }


    public static List<Category> getCategories() {
        Type t = new TypeToken<List<Category>>() {
        }.getType();
        return load(t, CATEGORIES);
    }

    public static void saveCategories(List<Category> categories) {
        save(CATEGORIES, categories);
    }

    public static void saveUserInfo(User user) {
        save(LOGIN_INFO, user);
    }

    public static void logout() {
        getPrefs().edit().remove(LOGIN_INFO).commit();
    }


    public static void saveContactData(String data) {
        getPrefs().edit().putString(CONTACT_INFO, data).commit();
    }

    public static void saveDeviceToken(String token) {
        getPrefs().edit().putString(DEVICE_TOKEN, token).commit();
    }

    public static String getDeviceToken() {
        return getPrefs().getString(DEVICE_TOKEN, "");
    }

    public static String getContactData() {
        return getPrefs().getString(CONTACT_INFO, "");
    }

}
