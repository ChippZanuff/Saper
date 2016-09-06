package com.example.tes.sapper;

import java.util.ArrayList;

public class CellMiner
{
    private int amountOfCells;
    private int AMOUNT_OF_MINES;
    private final int COL_NUM = 12;

    public CellMiner(int amountOfCells, int amountOfMines)
    {
        this.amountOfCells = amountOfCells;
        this.AMOUNT_OF_MINES = amountOfMines;
    }

    public ArrayList<CellParam> createBoard()
    {
        int minesAmount = this.amountOfCells / this.AMOUNT_OF_MINES;
        ArrayList<CellParam> cells = this.cellsCreate();

        for (int i = 0; i < minesAmount; i++)
        {
            int minePosition = (int) (Math.random() * this.amountOfCells);

            if(cells.get(minePosition) != null && !cells.get(minePosition).isHaveMine())
            {
                cells.get(minePosition).setMine();
            }
        }

        return cells;
    }

    private ArrayList<CellParam> cellsCreate()
    {
        ArrayList<CellParam> cells = new ArrayList<>();

        int rowPosition = 0;
        int colPosition = 0;

        for (int position = 0; position < amountOfCells; position++)
        {
            cells.add(new CellParam(colPosition, rowPosition));

            if(this.rowIsFull(position))
            {
                rowPosition++;
                colPosition = 0;
                continue;
            }

            colPosition++;
        }

        return cells;
    }

    private boolean rowIsFull(int position)
    {
        return (position + 1) % this.COL_NUM == 0;
    }
}
