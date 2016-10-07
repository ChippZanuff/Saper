package com.example.tes.sapper;

import android.view.View;
import android.widget.AdapterView;

import java.util.ArrayList;

public class GameMechanics
{
    private Board board;
    private ArrayList<View> emptyCellView;
    private ArrayList<View> minesAroundView;
    private ArrayList<Integer> minesValue;
    private int adjacentMinesCounter;
    private final int LEFT = -1, RIGHT = 1, TOP = -1, BOTTOM = 1;

    public GameMechanics(Board board)
    {
        this.emptyCellView = new ArrayList<>();
        this.minesAroundView = new ArrayList<>();
        this.minesValue = new ArrayList<>();
        this.board = board;
        this.adjacentMinesCounter = 0;
    }

    public void cellsIteration(int row, int col, AdapterView<?> adapterView)
    {
        CellParam cell;
        ArrayList<CellParam> adjacentMinesCells = new ArrayList<>();
        int transformedIndex = row * this.board.getNumColumns() + col;
        View iteratedView = adapterView.getChildAt(transformedIndex);

        cell = this.board.getCellById(row, col);
        if (!cell.isCellVisitedByIterator() && !cell.isOpen() && !cell.hasFlag() && !cell.hasMine())
        {
            this.selectAdjacentCells(adjacentMinesCells, row, col);

            this.checkMines(adjacentMinesCells, row, col);

            if (!cell.isMinesAround())
            {
                this.emptyCellView.add(iteratedView);
            } else
            {
                this.minesAroundView.add(iteratedView);
                this.minesValue.add(this.adjacentMinesCounter);
                cell.setMinesAround();
            }

            this.adjacentMinesCounter = 0;

            if (cell.isMinesAround())
            {
                return;
            }

            if (col < this.board.getNumColumns() - 1)
            {
                cell.makeOpen();
                this.cellsIteration(row, col + this.RIGHT, adapterView);
            }

            if (row < this.board.getRows() - 1)
            {
                cell.makeOpen();
                this.cellsIteration(row + this.BOTTOM, col, adapterView);
            }

            if (row >= 1)
            {
                cell.makeOpen();
                this.cellsIteration(row + this.TOP, col, adapterView);
            }

            if (col >= 1)
            {
                cell.makeOpen();
                this.cellsIteration(row, col + this.LEFT, adapterView);
            }


            if (col < this.board.getNumColumns() - 1 && row < this.board.getRows() - 1)
            {
                cell.makeOpen();
                this.cellsIteration(row + this.BOTTOM, col + this.RIGHT, adapterView);
            }

            if (col >= 1 && row < this.board.getRows() - 1)
            {
                cell.makeOpen();
                this.cellsIteration(row + this.BOTTOM, col + this.LEFT, adapterView);
            }

            if (col < this.board.getNumColumns() - 1 && row >= 1)
            {
                cell.makeOpen();
                this.cellsIteration(row + this.TOP, col + this.RIGHT, adapterView);
            }

            if (col >= 1 && row >= 1)
            {
                cell.makeOpen();
                this.cellsIteration(row + this.TOP, col + this.LEFT, adapterView);
            }

            cell.setCellVisitedByIterator();
        }
    }

    public ArrayList<View> getEmptyCellView()
    {
        return this.emptyCellView;
    }

    public ArrayList<View> getMinesAroundView()
    {
        return this.minesAroundView;
    }

    public ArrayList<Integer> getMinesValue()
    {
        return this.minesValue;
    }

    private void selectAdjacentCells(ArrayList<CellParam> adjacentMinesCells, int row, int col)
    {
        this.AddCellParam(adjacentMinesCells, this.board.getCellById(row + this.TOP, col));
        this.AddCellParam(adjacentMinesCells, this.board.getCellById(row + this.TOP, col + this.RIGHT));
        this.AddCellParam(adjacentMinesCells, this.board.getCellById(row + this.TOP, col + this.LEFT));

        this.AddCellParam(adjacentMinesCells, this.board.getCellById(row + this.BOTTOM, col + this.RIGHT));
        this.AddCellParam(adjacentMinesCells, this.board.getCellById(row + this.BOTTOM, col));
        this.AddCellParam(adjacentMinesCells, this.board.getCellById(row + this.BOTTOM, col + this.LEFT));

        this.AddCellParam(adjacentMinesCells, this.board.getCellById(row, col + this.LEFT));
        this.AddCellParam(adjacentMinesCells, this.board.getCellById(row, col + this.RIGHT));
    }

    private void AddCellParam(ArrayList<CellParam> adjacentMinesCells, CellParam cell)
    {
        if (cell != null)
        {
            adjacentMinesCells.add(cell);
        }
    }

    private void checkMines(ArrayList<CellParam> adjacentMinesCells, int row, int col)
    {
        for (CellParam iteratedCell : adjacentMinesCells)
        {
            if (iteratedCell.hasMine())
            {
                this.adjacentMinesCounter++;
            }
        }

        if (this.adjacentMinesCounter > 0)
        {
            CellParam cell = this.board.getCellById(row, col);
            cell.setMinesAround();
        }
    }

    public int transformToCoordRow(int cellPosition)
    {
        return cellPosition / this.board.getNumColumns();
    }

    public int transformToCoordCol(int cellPosition)
    {
        return cellPosition % this.board.getNumColumns();
    }
}