package com.example.tes.sapper;

public class CellParam
{
    private boolean mine, flag, isOpened;
    private int colPosition, rowPosition;

    public CellParam(int col, int row)
    {
        this.setDefaultParams(col, row);
    }

    private void setDefaultParams(int col, int row)
    {
        this.flag = false;
        this.mine = false;
        this.isOpened = false;
        this.colPosition = col;
        this.rowPosition = row;
    }

    public boolean isHaveMine()
    {
        return mine;
    }

    public void setMine()
    {
        this.mine = true;
    }

    public boolean isHaveFlag()
    {
        return flag;
    }

    public void raiseOrPutDownFlag()
    {
        this.flag = ! this.flag;
    }

    public int getColPosition()
    {
        return colPosition;
    }

    public int getRowPosition()
    {
        return rowPosition;
    }

    public boolean isOpened()
    {
        return isOpened;
    }

    public void setOpened()
    {
        isOpened = true;
    }
}


