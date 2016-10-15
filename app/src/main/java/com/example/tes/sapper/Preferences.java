package com.example.tes.sapper;

import android.content.SharedPreferences;

import java.io.File;

public class Preferences
{
    private SharedPreferences preferences;
    private Logger log;
    private File settingsFile;
    private int cellsAmount = 132, minesAmount = 6, numRows, numCols = 12;

    public Preferences(SharedPreferences preferences, File settingsFile, Logger log)
    {
        this.log = log;
        this.log.setTAG(getClass().getSimpleName());

        this.settingsFile = settingsFile;
        this.preferences = preferences;
        this.numRows = this.cellsAmount / this.numCols;

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
        return this.numRows;
    }

    public int getNumCols()
    {
        return this.numCols;
    }

    public void setPreferencesValue(String key, int value)
    {
        SharedPreferences.Editor editor = this.preferences.edit();

        editor.putInt(key, value);
        editor.apply();
    }
}
