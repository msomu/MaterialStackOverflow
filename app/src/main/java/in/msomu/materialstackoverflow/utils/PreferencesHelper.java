package in.msomu.materialstackoverflow.utils;

import android.content.SharedPreferences;

import in.msomu.materialstackoverflow.AppController;

/**
 * Created by msomu on 30/06/2016.
 * Prefernce Helper for the whole app
 */
public class PreferencesHelper {


    private static final String IS_LOGIN_PREFS = "Is_Login";

    private static final String PREF_KEY_USER_ID = "UserId";

    public static Boolean getBoolean(String key, Boolean defValue) {
        return AppController.getPreferences().getBoolean(key, defValue);
    }

    public static void putBoolean(String key, Boolean value) {
        SharedPreferences.Editor editor = AppController.getPreferences().edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public static String getString(String key, String defValue) {
        return AppController.getPreferences().getString(key, defValue);
    }

    public static void putString(String key, String value) {
        SharedPreferences.Editor editor = AppController.getPreferences().edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static int getInt(String key, int defValue) {
        return AppController.getPreferences().getInt(key, defValue);
    }

    public static void putInt(String key, int value) {
        SharedPreferences.Editor editor = AppController.getPreferences().edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public static boolean getLoginCheck() {
        return getBoolean(IS_LOGIN_PREFS, false);
    }

    public static void setLoginCheck(boolean tested) {
        putBoolean(IS_LOGIN_PREFS, tested);
    }

    public static String getUserID() {
        return getString(PREF_KEY_USER_ID, "");
    }

    public static void setUserID(String value) {
         putString(PREF_KEY_USER_ID, value);
    }

}

