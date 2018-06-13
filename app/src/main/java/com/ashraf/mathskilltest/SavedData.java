package com.ashraf.mathskilltest;

import android.app.AlarmManager;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class SavedData {
    private static final String APP_PREFS_NAME = "SavedData";

    private SharedPreferences appSharedPrefs;
    private SharedPreferences.Editor prefsEditor;

    private static final String BEST_SCORE = "bestScore";


    public SavedData(Context context)
    {
        this.appSharedPrefs = context.getSharedPreferences(APP_PREFS_NAME, Context.MODE_PRIVATE);
        this.prefsEditor = appSharedPrefs.edit();
    }


    public void saveBestScore(int score){
        prefsEditor.putInt(BEST_SCORE,score);
        prefsEditor.commit();
    }
    public int getBestScore(){
        return appSharedPrefs.getInt(BEST_SCORE,0);
    }

}
