package com.longtv.zappy.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.longtv.zappy.base.BaseActivity;
import com.longtv.zappy.network.dto.ContentType;
import com.longtv.zappy.network.dto.LoginData;
import com.longtv.zappy.network.dto.Profile;
import com.longtv.zappy.network.dto.UsageTime;
import com.longtv.zappy.service.NotificationService;

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
    private static final String GOLDS = "GOLDS";
    private static final String TIME_ON_FILM = "TIME_ON_FILM";
    private static final String TIME_ON_MUSIC = "TIME_ON_MUSIC";
    private static final String TIME_ON_BOOK = "TIME_ON_BOOK";
    private static final String USAGE_TIME = "USAGE_TIME";
    private static final String PASSWORD = "PASSWORD";
    private static final String CACHE_HOME = "CACHE_HOME";
    private static final String CACHE_FILM = "CACHE_FILM";
    private static final String CACHE_MUSIC = "CACHE_MUSIC";
    private static final String CACHE_BOOK = "CACHE_BOOK";
    private static final String CURRENT_NOTIFICATION_ID = "CURRENT_NOTIFICATION_ID";
    private static final String TAG = "anth";

    public synchronized static SharedPreferences getPreference(Context context) {
        if (context != null) {
            return context.getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE);
        }
        return null;
    }

    public static void saveUsageTime(Context context, String data) {
        if (context == null) {
            return;
        }
        SharedPreferences preferences = getPreference(context);
        if (preferences != null) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(USAGE_TIME, data);
            editor.apply();
        }
    }

    public static void savePassword(Context context, String password) {
        if (context == null) {
            return;
        }
        SharedPreferences preferences = getPreference(context);
        if (preferences != null) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(PASSWORD, password);
            editor.apply();
        }
    }

    public static String getPassword(Context context) {
        SharedPreferences preferences = getPreference(context);

        if (preferences != null) {
            return preferences.getString(PASSWORD, "");
        }

        return "";
    }

    public static List<UsageTime> getUsageTime(Context context) {
        SharedPreferences preferences = getPreference(context);

        if (preferences != null) {
            String data = preferences.getString(USAGE_TIME, "");
            if (data == null) {
                return null;
            }

            List<UsageTime> list = new ArrayList<>();
            try {
                Gson gson = new Gson();
                Type type = new TypeToken<List<UsageTime>>(){}.getType();
                list = gson.fromJson(data, type);
            } catch (Exception e) {
                list = new ArrayList<>();
            }

            return list;
        }

        return new ArrayList<>();


    }

    public static void saveTimeOnFilm(Context context, long time) {
        if (context == null) {
            return;
        }
        Log.e(TAG, "saveTimeOnFilm: " + time );
        SharedPreferences preferences = getPreference(context);
        if (preferences != null) {
            SharedPreferences.Editor editor = preferences.edit();
            long timeOld = preferences.getLong(TIME_ON_FILM, 0);
            editor.putLong(TIME_ON_FILM, timeOld + time);
            editor.apply();
        }
    }

    public static void saveTimeOnMusic(Context context, long time) {
        if (context == null) {
            return;
        }
        SharedPreferences preferences = getPreference(context);
        if (preferences != null) {
            SharedPreferences.Editor editor = preferences.edit();
            long timeOld = preferences.getLong(TIME_ON_MUSIC, 0);
            editor.putLong(TIME_ON_MUSIC, timeOld + time);
            editor.apply();
        }
    }

    public static void saveTimeOnBook(Context context, long time) {
        if (context == null) {
            return;
        }
        SharedPreferences preferences = getPreference(context);
        if (preferences != null) {
            SharedPreferences.Editor editor = preferences.edit();
            long timeOld = preferences.getLong(TIME_ON_BOOK, 0);
            editor.putLong(TIME_ON_BOOK, timeOld + time);
            editor.apply();
        }
    }

    public static long getTimeOnFilm(Context context) {
        SharedPreferences preferences = getPreference(context);

        if (preferences != null) {
            return preferences.getLong(TIME_ON_FILM, 0);
        }

        return 0;
    }

    public static long getTimeOnMusic(Context context) {
        SharedPreferences preferences = getPreference(context);

        if (preferences != null) {
            return preferences.getLong(TIME_ON_MUSIC, 0);
        }

        return 0;
    }

    public static long getTimeOnBook(Context context) {
        SharedPreferences preferences = getPreference(context);

        if (preferences != null) {
            return preferences.getLong(TIME_ON_BOOK, 0);
        }

        return 0;
    }

    public static void saveGold(Context context, int gold) {
        if (context == null) {
            return;
        }
        SharedPreferences preferences = getPreference(context);
        if (preferences != null) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt(GOLDS, gold);
            editor.apply();
        }
    }

    public static int getGold(Context context) {
        SharedPreferences preferences = getPreference(context);

        if (preferences != null) {
            return preferences.getInt(GOLDS, 0);
        }

        return 0;
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
    public static void saveToken(Context context, String refreshToken) {
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

    public static String getToken(Context context) {
        SharedPreferences preferences = getPreference(context);

        if (preferences != null) {
            return preferences.getString(REFRESH_TOKEN, "");
        }

        return "";
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

    public static void saveCacheFilm(Context context, List<ContentType> data) {
        if (context == null) {
            return;
        }
        SharedPreferences preferences = getPreference(context);
        if (preferences != null) {
            Gson gson = new Gson();
            String json = gson.toJson(data);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(CACHE_FILM, json);
            editor.apply();
        }
    }

    public static List<ContentType> getCacheFilm(Context context) {
        SharedPreferences preferences = getPreference(context);

        if (preferences != null) {
            String data = preferences.getString(CACHE_FILM, "");
            if (data == null) {
                return null;
            }

            List<ContentType> list = new ArrayList<>();
            try {
                Gson gson = new Gson();
                Type type = new TypeToken<List<ContentType>>(){}.getType();
                list = gson.fromJson(data, type);
            } catch (Exception e) {
                list = new ArrayList<>();
            }

            return list;
        }

        return new ArrayList<>();
    }

    public static void saveCacheMusic(Context context, List<ContentType> data) {
        if (context == null) {
            return;
        }
        SharedPreferences preferences = getPreference(context);
        if (preferences != null) {
            Gson gson = new Gson();
            String json = gson.toJson(data);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(CACHE_MUSIC, json);
            editor.apply();
        }
    }

    public static List<ContentType> getCacheMusic(Context context) {
        SharedPreferences preferences = getPreference(context);

        if (preferences != null) {
            String data = preferences.getString(CACHE_MUSIC, "");
            if (data == null) {
                return null;
            }

            List<ContentType> list = new ArrayList<>();
            try {
                Gson gson = new Gson();
                Type type = new TypeToken<List<ContentType>>(){}.getType();
                list = gson.fromJson(data, type);
            } catch (Exception e) {
                list = new ArrayList<>();
            }

            return list;
        }

        return new ArrayList<>();
    }

    public static void saveCacheBook(Context context, List<ContentType> data) {
        if (context == null) {
            return;
        }
        SharedPreferences preferences = getPreference(context);
        if (preferences != null) {
            Gson gson = new Gson();
            String json = gson.toJson(data);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(CACHE_BOOK, json);
            editor.apply();
        }
    }

    public static List<ContentType> getCacheBook(Context context) {
        SharedPreferences preferences = getPreference(context);

        if (preferences != null) {
            String data = preferences.getString(CACHE_BOOK, "");
            if (data == null) {
                return null;
            }

            List<ContentType> list = new ArrayList<>();
            try {
                Gson gson = new Gson();
                Type type = new TypeToken<List<ContentType>>(){}.getType();
                list = gson.fromJson(data, type);
            } catch (Exception e) {
                list = new ArrayList<>();
            }

            return list;
        }

        return new ArrayList<>();
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


    public static void saveCurrentNotificationId(Context context, int id) {
        if (context == null) {
            return;
        }
        SharedPreferences preferences = getPreference(context);
        if (preferences != null) {
            Log.e(TAG, "saveCurrentNotificationId: " + id );
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt(CURRENT_NOTIFICATION_ID, id);
            editor.apply();
        }
    }

    public static int getCurrentNotificationId(Context context) {
        SharedPreferences preferences = getPreference(context);

        if (preferences != null) {
            return preferences.getInt(CURRENT_NOTIFICATION_ID, 0);
        }

        return 0;
    }

    public static void clearUserData(Context viewContext) {
        if (viewContext == null) {
            return;
        }
        SharedPreferences preferences = getPreference(viewContext);
        if (preferences != null) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.clear();
            editor.commit();
            editor.putBoolean(IS_FIRST_START, false);
            editor.apply();
        }
    }
}
