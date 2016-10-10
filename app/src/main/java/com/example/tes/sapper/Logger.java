package com.example.tes.sapper;

import android.util.Log;

public class Logger
{
    private String TAG;

    public void info(String msg)
    {
        Log.i(this.TAG, msg);
    }

    public void error(String msg, Exception e)
    {
        Log.e(this.TAG, msg, e);
    }

    public void setTAG(String tag)
    {
        this.TAG = tag;
    }
}
