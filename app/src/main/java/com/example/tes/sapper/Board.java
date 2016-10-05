package com.example.tes.sapper;

import java.util.ArrayList;

public class Board
{
    private int amountOfCells, numColumns, amountOfMines, rows, flagsLeft;

    private ArrayList<ArrayList<CellParam>> cells;
    private ArrayList<CellParam> minedCells;

    public Board(int amountOfCells, int amountOfMines, int rows, int numColumns)
    {
        this.amountOfCells = amountOfCells;
        this.amountOfMines = amountOfMines;
        this.numColumns = numColumns;
        this.rows = rows;
        this.cells = this.fieldCreate();
        this.setMineField();
    }

    private void setMineField()
    {
        int minesAmount = this.amountOfCells / this.amountOfMines;
        int availableRow = this.amountOfCells / this.numColumns;
        this.minedCells = new ArrayList<>();

        for (int i = 0; i < minesAmount; i++)
        {
            int mineRow = (int) (Math.random() * (availableRow));
            int mineCol = (int) (Math.random() * (this.numColumns));

            if(this.getCellById(mineRow, mineCol)!= null && !this.getCellById(mineRow, mineCol).isHaveMine())
            {
                this.minedCells.add(this.getCellById(mineRow, mineCol));
                this.setMineById(mineRow, mineCol);
            }
        }

        this.flagsLeft = this.minedCells.size();
    }

    private ArrayList<ArrayList<CellParam>> fieldCreate()
    {
        ArrayList<ArrayList<CellParam>> field = new ArrayList<>();


        for(int row = 0; row < this.amountOfCells / this.numColumns; row++)
        {
            ArrayList<CellParam> columns = new ArrayList<>();

            for (int column = 0; column < this.numColumns; column++)
            {
                columns.add(new CellParam());
            }

            field.add(columns);
        }

        return field;
    }

    private void setMineById(int row ,int col)
    {
        this.getCellById(row, col).setMine();
    }

    public boolean isCellExists(int row, int column)
    {
        if(row >= 0 && row < this.getRows() && column >= 0 && column < this.getNumColumns())
        {
            return this.cells.get(row).get(column) != null;
        }
        return false;
    }

    public boolean isCellOpened(int row, int col)
    {
        return this.getCellById(row, col).isOpened();
    }

    public void setCellOpened(int row, int col)
    {
        this.getCellById(row, col).setOpened();
    }

    public void setMinesAroundById(int row, int col)
    {
        this.getCellById(row, col).setMinesAround();
    }

    public boolean haveCellMineById(int row, int column)
    {
        return this.getCellById(row, column).isHaveMine();
    }

    public boolean isMinesAround(int row, int col)
    {
        return this.getCellById(row, col).isMinesAround();
    }

    public boolean haveCellFlagById(int row, int col)
    {
        return this.getCellById(row, col).isHaveFlag();
    }

    public void raiseOrPutDownFlagById(int row, int col)
    {
        if(this.flagsLeft < this.minedCells.size() && this.getCellById(row, col).isHaveFlag())
        {
            this.getCellById(row, col).putDownFlag();
            this.flagsLeft++;
        }
        else if(this.flagsLeft > 0  && !this.getCellById(row, col).isHaveFlag())
        {
            this.getCellById(row, col).raiseFlag();
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
        return this.rows;
    }

    public int getAmountOfCells()
    {
        return this.amountOfCells;
    }

    public int getNumColumns()
    {
        return this.numColumns;
    }

    public int getFlagsLeft()
    {
        return flagsLeft;
    }

    private boolean isFlagsNoLeft()
    {
        return this.getFlagsLeft() == 0;
    }

    public boolean isFlaggedCellsAreMined()
    {
        if(this.isFlagsNoLeft())
        {
            for(CellParam cell : this.minedCells)
            {
                if(!cell.isHaveFlag())
                {
                    return false;
                }
            }

            return true;
        }

        return false;
    }
}
