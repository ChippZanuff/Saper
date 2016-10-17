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
    private Logger log;
    private int adjacentMinesCounter;

    private Preferences preferences;

    public GameMechanics(Board board, Logger log, Preferences preferences)
    {
        this.preferences = preferences;
        this.emptyCellView = new ArrayList<>();
        this.minesAroundView = new ArrayList<>();
        this.minesValue = new ArrayList<>();
        this.board = board;
        this.adjacentMinesCounter = 0;
        this.log = log;
        this.log.setTAG(getClass().getSimpleName());
    }

    public void cellsIteration(int row, int col, AdapterView<?> adapterView)
    {
        CellParam cell;
        ArrayList<CellParam> adjacentMinesCells = new ArrayList<>();
        int transformedIndex = row * this.preferences.getNumCols() + col;
        View iteratedView = adapterView.getChildAt(transformedIndex);

        cell = this.board.getCellById(row, col);
        if (!cell.isCellVisitedByIterator() && !cell.isOpen() && !cell.hasFlag() && !cell.hasMine())
        {
            ArrayList<Point> points = this.board.findPointsAroundCell(row, col);

            for (Point point : points)
            {
                adjacentMinesCells.add(this.board.getCellById(point.getRow(), point.getColumn()));
            }

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

            for (Point point : points)
            {
                cell.makeOpen();
                this.cellsIteration(point.getRow(), point.getColumn(), adapterView);
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

    private void checkMines(ArrayList<CellParam> adjacentMinesCells, int row, int col)
    {
        for (CellParam iteratedCell : adjacentMinesCells)
        {
            if (iteratedCell.hasMine())
            {
                this.adjacentMinesCounter++;
            }
        }

        if (this.adjacentMinesCounter > this.preferences.getStartingPosition())
        {
            CellParam cell = this.board.getCellById(row, col);
            cell.setMinesAround();
        }
    }

    public int transformToCoordRow(int cellPosition)
    {
        return cellPosition / this.preferences.getNumCols();
    }

    public int transformToCoordCol(int cellPosition)
    {
        return cellPosition % this.preferences.getNumCols();
    }
}