package com.example.tes.sapper;

import android.content.SharedPreferences;

import java.io.File;

public class Preferences
{
    private SharedPreferences preferences;
    private Logger log;
    private File settingsFile;
    private final String CELLS_AMOUNT = "Cells Amount", MINES_AMOUNT = "Mines Amount";
    private int cellsAmount = 132, minesAmount = 6;

    public Preferences(SharedPreferences preferences,File settingsFile, Logger log)
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
            this.cellsAmount = this.preferences.getInt(this.CELLS_AMOUNT, this.getCellsAmount());
            this.minesAmount = this.preferences.getInt(this.MINES_AMOUNT, this.getMinesAmount());
        }
    }

    public int getCellsAmount()
    {
        return cellsAmount;
    }

    public int getMinesAmount()
    {
        return minesAmount;
    }
}
