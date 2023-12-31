package com.longtv.zappy.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefManager {


    private static final String MY_PREFERENCES = "LONG_TV";
    private static final String ACCESS_TOKEN = "ACCESS_TOKEN";
    private static final String REFRESH_TOKEN = "REFRESH_TOKEN";
    private static final String IS_LOGIN = "IS_LOGIN";
    private static final String IS_FIRST_START = "IS_FIRST_START";
    private static final String LIST_CATEGORY = "IS_FIRST_START";
    private static final String HOME_DATA = "HOME_DATA";
    private static final String HISTORY_SEARCH = "HISTORY_SEARCH";

    public synchronized static SharedPreferences getPreference(Context context) {
        if (context != null) {
            return context.getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE);
        }
        return null;
    }

    public static void saveAccessTokenInfo(Context context, String accessToken) {
        if (context == null) {
            return;
        }
        SharedPreferences preferences = getPreference(context);
        if (preferences != null) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(ACCESS_TOKEN, accessToken);
            editor.apply();
        }
    }
    public static void saveRefreshTokenInfo(Context context, String refreshToken) {
        if (context == null) {
            return;
        }
        SharedPreferences preferences = getPreference(context);
        if (preferences != null) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(REFRESH_TOKEN, refreshToken);
            editor.apply();
        }
    }

    public static String getAccessToken(Context context) {
        SharedPreferences preferences = getPreference(context);

        if (preferences != null) {
            return preferences.getString(ACCESS_TOKEN, "");
        }

        return "";
    }

    public static void saveListCategory(Context context, String data) {
        if (context == null) {
            return;
        }
        SharedPreferences preferences = getPreference(context);
        if (preferences != null) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(LIST_CATEGORY, data);
            editor.apply();
        }
    }


    public static void setLogin(Context context, boolean login) {
        if (context == null) {
            return;
        }
        SharedPreferences preferences = getPreference(context);
        if (preferences != null) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean(IS_LOGIN, login);
            editor.apply();
        }
    }

    public static boolean isLogged(Context context) {
        SharedPreferences preferences = getPreference(context);

        if (preferences != null) {
            return preferences.getBoolean(IS_LOGIN, false);
        }

        return false;
    }

    public static boolean isFirstStart(Context context) {
        SharedPreferences preferences = getPreference(context);

        if (preferences != null) {
            return preferences.getBoolean(IS_FIRST_START, true);
        }

        return false;
    }

    public static void setIsFirstStart(Context context, boolean firstStart) {
        if (context == null) {
            return;
        }
        SharedPreferences preferences = getPreference(context);
        if (preferences != null) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean(IS_FIRST_START, firstStart);
            editor.apply();
        }
    }

    public static void clearHomeCache(Context context) {
        if (context == null) {
            return;
        }
        SharedPreferences preferences = getPreference(context);
        if (preferences != null) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.remove(HOME_DATA);
            editor.apply();
        }
    }


}
