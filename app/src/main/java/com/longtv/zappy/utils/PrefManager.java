package com.longtv.zappy.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.longtv.zappy.network.dto.LoginData;
import com.longtv.zappy.network.dto.Profile;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class PrefManager {
    private static final String MY_PREFERENCES = "LONG_TV";
    private static final String ACCESS_TOKEN = "ACCESS_TOKEN";
    private static final String REFRESH_TOKEN = "REFRESH_TOKEN";
    private static final String IS_LOGIN = "IS_LOGIN";
    private static final String IS_FIRST_START = "IS_FIRST_START";
    private static final String LIST_CATEGORY_FILM = "IS_FIRST_START_FILM";
    private static final String HOME_DATA = "HOME_DATA";
    private static final String USER_DATA = "USER_DATA";
    private static final String PROFILE_DATA = "PROFILE_DATA";
    private static final String HISTORY_SEARCH = "HISTORY_SEARCH";
    private static final String PROFILE_ID = "PROFILE_ID";
    private static final String PROFILE_TOKEN = "PROFILE_TOKEN";

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

    public static void setProfileId(Context context, int id) {
        if (context == null) {
            return;
        }
        SharedPreferences preferences = getPreference(context);
        if (preferences != null) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt(PROFILE_ID, id);
            editor.apply();
        }
    }

    public static int getProfileId(Context context) {
        SharedPreferences preferences = getPreference(context);

        if (preferences != null) {
            return preferences.getInt(PROFILE_ID, 0);
        }

        return 0;
    }

    public static void setProfileToken(Context context, String token) {
        if (context == null) {
            return;
        }
        SharedPreferences preferences = getPreference(context);
        if (preferences != null) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(PROFILE_TOKEN, token);
            editor.apply();
        }
    }

    public static String getProfileToken(Context context) {
        SharedPreferences preferences = getPreference(context);

        if (preferences != null) {
            return preferences.getString(PROFILE_TOKEN, "");
        }

        return "";
    }

    public static List<Profile> getProfileData(Context context) {
        SharedPreferences preferences = getPreference(context);

        if (preferences != null) {
            String data = preferences.getString(PROFILE_DATA, "");
            if (data == null) {
                return null;
            }

            List<Profile> list = new ArrayList<>();
            try {
                Gson gson = new Gson();
                Type type = new TypeToken<List<Profile>>(){}.getType();
                list = gson.fromJson(data, type);
            } catch (Exception e) {
                list = new ArrayList<>();
            }

            return list;
        }

        return new ArrayList<>();
    }

    public static void saveProfileData(Context context, List<Profile> data) {
        if (context == null) {
            return;
        }
        SharedPreferences preferences = getPreference(context);
        if (preferences != null) {
            Gson gson = new Gson();
            String json = gson.toJson(data);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(PROFILE_DATA, json);
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

    public static void saveUserData(Context context, String data) {
        if (context == null) {
            return;
        }
        SharedPreferences preferences = getPreference(context);
        if (preferences != null) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(USER_DATA, data);
            editor.apply();
        }
    }

    public static LoginData getUserData(Context context) {
        SharedPreferences preferences = getPreference(context);

        if (preferences != null) {
            String data = preferences.getString(USER_DATA, "");
            if (data != null && !data.isEmpty()) {
                return new Gson().fromJson(data, LoginData.class);
            }
        }

        return null;
    }

    public static void saveListCategoryFilm(Context context, String data) {
        if (context == null) {
            return;
        }
        SharedPreferences preferences = getPreference(context);
        if (preferences != null) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(LIST_CATEGORY_FILM, data);
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
