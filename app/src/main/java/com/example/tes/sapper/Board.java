package com.example.tes.sapper;

import java.util.ArrayList;

public class Board
{
    private int amountOfCells, numColumns, amountOfMines, rows, flagsLeft;

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
        CellParam cell;

        while(true)
        {
            int mineRow = (int) (Math.random() * (availableRow));
            int mineCol = (int) (Math.random() * (this.numColumns));
            cell = this.getCellById(mineRow, mineCol);

            if(cell!= null && !cell.hasMine())
            {
                cell.setMine();
                this.flagsLeft++;
            }

            if(this.flagsLeft == minesAmount)
            {
                break;
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
        return this.getCellById(row, col).isOpen();
    }

    public void setCellOpened(int row, int col)
    {
        this.getCellById(row, col).makeOpen();
    }

    public void setMinesAroundById(int row, int col)
    {
        this.getCellById(row, col).setMinesAround();
    }

    public boolean haveCellMineById(int row, int column)
    {
        return this.getCellById(row, column).hasMine();
    }

    public boolean isMinesAround(int row, int col)
    {
        return this.getCellById(row, col).isMinesAround();
    }

    public boolean haveCellFlagById(int row, int col)
    {
        return this.getCellById(row, col).hasFlag();
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
