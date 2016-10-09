package com.example.tes.sapper;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.File;

public class Preferences
{
    private SharedPreferences preferences;
    private Context context;

    public Preferences(Context context)
    {
        this.context = context;
        this.preferences = context.getSharedPreferences(context.getResources().getString(R.string.settings), Context.MODE_PRIVATE);
        this.isSettingsFileExists();
    }

    public void saveNewIntValue(String key, int value)
    {
        SharedPreferences.Editor editor = this.preferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public int getIntValue(int id)
    {
        String key = this.castStringIdValue(id);
        return preferences.getInt(key, 0);
    }

    private void isSettingsFileExists()
    {
        File settings = new File(this.context.getApplicationContext().getApplicationInfo().dataDir + "/shared_prefs/" + this.castStringIdValue(R.string.settings) + ".xml");

        if(!settings.exists())
        {
            this.saveNewIntValue(this.castStringIdValue(R.string.amountofcells), this.castIntegerIdValue(R.integer.amountofcells));
            this.saveNewIntValue(this.castStringIdValue(R.string.amountofmines), this.castIntegerIdValue(R.integer.amountofmines));
            this.saveNewIntValue(this.castStringIdValue(R.string.numcolumns), this.castIntegerIdValue(R.integer.numcolumns));
            this.saveNewIntValue(this.castStringIdValue(R.string.numrows), this.castIntegerIdValue(R.integer.numrows));
        }
    }

    private String castStringIdValue(int id)
    {
        return this.context.getResources().getString(id);
    }

    private int castIntegerIdValue(int id)
    {
        return this.context.getResources().getInteger(id);
    }
}
