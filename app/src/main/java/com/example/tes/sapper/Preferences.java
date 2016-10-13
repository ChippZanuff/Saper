package com.example.tes.sapper;

import android.content.SharedPreferences;

import java.io.File;

public class Preferences
{
    private SharedPreferences preferences;
    private Logger log;
    private File settingsFile;
    private final String CELLS_AMOUNT = "Cells Amount", MINES_AMOUNT = "Mines Amount";
    private int cellsAmount = 132, minesAmount = 6, numRows, numCols;

    public Preferences(SharedPreferences preferences, File settingsFile, int numCols, Logger log)
    {
        this.log = log;
        this.log.setTAG(getClass().getSimpleName());

        this.settingsFile = settingsFile;
        this.preferences = preferences;
        this.numCols = numCols;
        this.setRows();

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

    private void setRows()
    {
        this.numRows = this.cellsAmount / this.numCols;
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
}
