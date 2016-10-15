package com.example.tes.sapper;

import java.util.ArrayList;

public class Board
{
    private int flagsLeft;
    private Logger log;
    private ArrayList<ArrayList<CellParam>> cells;
    private Preferences preferences;

    public Board(Preferences preferences, Logger log)
    {
        this.preferences = preferences;
        this.log = log;
        this.log.setTAG(getClass().getSimpleName());
        this.cells = this.fieldCreate();
        this.setMineField();
    }

    private void setMineField()
    {
        CellParam cell;

        while(true)
        {
            int mineRow = (int) (Math.random() * (this.preferences.getNumRows()));
            int mineCol = (int) (Math.random() * (this.preferences.getNumCols()));
            cell = this.getCellById(mineRow, mineCol);

            if(cell!= null && !cell.hasMine())
            {
                cell.setMine();
                this.flagsLeft++;
            }

            if(this.flagsLeft == this.preferences.getMineCounter())
            {
                break;
            }
        }
    }

    private ArrayList<ArrayList<CellParam>> fieldCreate()
    {
        ArrayList<ArrayList<CellParam>> field = new ArrayList<>();
        int count = 0;

        while(count < this.preferences.getCellsAmount())
        {
            ArrayList<CellParam> columns = new ArrayList<>();

            for (int column = 0; column < this.preferences.getNumCols(); column++)
            {
                columns.add(new CellParam());
            }

            field.add(columns);
            count++;
        }

        return field;
    }

    public boolean isCellExists(int row, int column)
    {
        return row >= this.preferences.getZeroPoint()
                && row < this.getRows()
                && column >= this.preferences.getZeroPoint()
                && column < this.getNumColumns()
                && this.cells.get(row).get(column) != null;
    }

    public void raiseOrPutDownFlagById(CellParam cell)
    {
        if(cell.hasFlag())
        {
            cell.putDownFlag();
            this.flagsLeft++;
        }
        else if(this.flagsLeft > 0  && !cell.hasFlag())
        {
            cell.raiseFlag();
            this.flagsLeft--;
        }
    }

    public CellParam getCellById(int row ,int col)
    {
        if(this.isCellExists(row, col))
        {
            return this.cells.get(row).get(col);
        }

        return null;
    }

    public int getRows()
    {
        return this.preferences.getNumRows();
    }

    public int getAmountOfCells()
    {
        return this.preferences.getCellsAmount();
    }

    public int getNumColumns()
    {
        return this.preferences.getNumCols();
    }

    public int getFlagsLeft()
    {
        return flagsLeft;
    }

    public boolean isAllCellsDemined()
    {
        if(this.flagsLeft > 0)
        {
            return false;
        }

        CellParam cell;
        for (int i = 0; i < this.cells.size(); i++)
        {
            for (int j = 0; j < this.cells.get(i).size(); j++)
            {
                cell = this.cells.get(i).get(j);

                if (cell.hasFlag() && !cell.hasMine())
                {
                    return false;
                }
            }
        }

        return true;
    }
}
