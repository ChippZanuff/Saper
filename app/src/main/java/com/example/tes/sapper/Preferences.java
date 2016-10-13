package com.example.tes.sapper;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.File;

public class Preferences
{
    private SharedPreferences preferences;
    private Logger log;
    private Context context;
    private File settings;
    private final String CELLS_AMOUNT = "Cells Amount", MINES_AMOUNT = "Mines Amount", NUM_ROWS = "Num Rows", NUM_COLS = "Num Cols";
    private int cellsAmount = 132, minesAmount = 6, numRows = 11, numCols = 12;

    public Preferences(Context context, Logger log)
    {
        this.log = log;
        this.log.setTAG(getClass().getSimpleName());
        this.context = context;

        String SETTINGS_FILE = "Settings";
        this.preferences = this.context.getSharedPreferences(SETTINGS_FILE, Context.MODE_PRIVATE);

        String settingsFilePath = this.context.getApplicationInfo().dataDir + "/shared_prefs/" + SETTINGS_FILE + ".xml";
        this.settings = new File(settingsFilePath);

        if(this.settings.delete());

        this.getPreferencesFileValues();

    }

    private void createPreferencesFile()
    {
        SharedPreferences.Editor edit = this.preferences.edit();
        edit.putInt(this.CELLS_AMOUNT, this.getCellsAmount());
        edit.putInt(this.MINES_AMOUNT, this.getMinesAmount());
        edit.putInt(this.NUM_ROWS, this.getNumRows());
        edit.putInt(this.NUM_COLS, this.getNumCols());
        edit.apply();
    }

    private void getPreferencesFileValues()
    {
        if(this.settings.exists())
        {
            this.cellsAmount = this.preferences.getInt(this.CELLS_AMOUNT, this.getCellsAmount());
            this.minesAmount = this.preferences.getInt(this.MINES_AMOUNT, this.getMinesAmount());
            this.numRows = this.preferences.getInt(this.NUM_ROWS, this.getNumRows());
            this.numCols = this.preferences.getInt(this.NUM_COLS, this.getNumCols());
        }
        else
        {
            this.createPreferencesFile();
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

    public int getNumRows()
    {
        return numRows;
    }

    public int getNumCols()
    {
        return numCols;
    }
}
