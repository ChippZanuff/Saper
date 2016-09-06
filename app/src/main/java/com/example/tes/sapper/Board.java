package com.example.tes.sapper;

import java.util.ArrayList;

public class Board
{
    private ArrayList<CellParam> cells;

    public Board(ArrayList<CellParam> cells)
    {
        this.cells = cells;
    }

    public boolean existsCellById(int id)
    {
        return this.getCellById(id) != null;
    }

    public boolean hasCellMineById(int id)
    {
        return this.existsCellById(id) && this.getCellById(id).isHaveMine();
    }

    public CellParam getCellById(int id)
    {
        return this.cells.get(id);
    }

    public void setMineById(int id)
    {
        if (this.existsCellById(id)) {
            this.getCellById(id).setMine();
        }
    }
}
