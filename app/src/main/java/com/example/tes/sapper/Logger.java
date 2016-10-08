package com.example.tes.sapper;

import android.util.Log;

public class Logger
{
    public void info(String tag, String msg)
    {
        if(BuildConfig.USE_LOG)
        {
            Log.i(tag, msg);
        }
    }

    public void error(String tag, String msg, Exception e)
    {
        if(BuildConfig.USE_LOG)
        {
            Log.e(tag, msg, e);
        }
    }
}
