package com.example.tes.sapper;

import java.util.ArrayList;

public class Board
{
    private int amountOfCells, numColumns, amountOfMines, rows;

    private ArrayList<ArrayList<CellParam>> cells;

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

        for (int i = 0; i < minesAmount; i++)
        {
            int mineRow = (int) (Math.random() * (availableRow - 1));
            int mineCol = (int) (Math.random() * (this.numColumns));

            if(this.getCellById(mineRow, mineCol)!= null && !this.getCellById(mineRow, mineCol).isHaveMine())
            {
                this.setMineById(mineRow, mineCol);
            }
        }
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
        return this.cells.get(row).get(column) != null;
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
        this.getCellById(row, col).raiseOrPutDownFlag();
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
}
