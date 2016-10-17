package com.example.tes.sapper;

import android.content.SharedPreferences;

import java.io.File;

public class Preferences
{
    private SharedPreferences preferences;
    private Logger log;
    private File settingsFile;
    private int cellsAmount = 132;
    private int minesAmount = 6;
    private int numCols = 12;
    private int numRows;

    public Preferences(SharedPreferences preferences, File settingsFile, Logger log)
    {
        this.log = log;
        this.log.setTAG(getClass().getSimpleName());

        this.settingsFile = settingsFile;
        this.preferences = preferences;


        this.getPreferencesFileValues();
    }

    private void getPreferencesFileValues()
    {
        if(this.settingsFile.exists())
        {
            this.cellsAmount = this.preferences.getInt("Cells Amount", this.getCellsAmount());
            this.minesAmount = this.preferences.getInt("Mines Amount", this.getMinesAmount());
        }
    }

    public int getCellsAmount()
    {
        return this.cellsAmount;
    }

    public int getMinesAmount()
    {
        return this.minesAmount;
    }

    public int getNumRows()
    {
        return this.cellsAmount / this.numCols;
    }

    public int getNumCols()
    {
        return this.numCols;
    }

    private void setPreferencesValue(String key, int value)
    {
        SharedPreferences.Editor editor = this.preferences.edit();

        editor.putInt(key, value);
        editor.apply();
    }

    public void setPreferencesCellsAmount(int value)
    {
        this.setPreferencesValue("Cells Amount", value);
    }

    public void setPreferencesMinesAmount(int value)
    {
        this.setPreferencesValue("Mines Amount", value);
    }

    public int getMineCounter()
    {
        return this.cellsAmount / this.minesAmount;
    }

    public int getStartingPosition()
    {
        return 0;
    }
}
