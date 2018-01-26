package com.example.rajeshpalkar.a08;

import android.content.Context;
import android.content.SharedPreferences;

public class IntroManager {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;


    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";

    public IntroManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences("first", 0);
        editor = pref.edit();
    }

    public void setFirstTimeLaunch(boolean isFirst) {
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirst);
        editor.commit();
    }

    public boolean isFirstTimeLaunch() {
        return pref.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }

}
