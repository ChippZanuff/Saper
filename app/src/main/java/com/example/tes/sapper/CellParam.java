package com.example.tes.sapper;

public class CellParam
{
    private boolean mine, flag, isOpened, isMinesAround, isCellVisitedByIterator;

    public CellParam()
    {
        this.setDefaultParams();
    }

    private void setDefaultParams()
    {
        this.flag = false;
        this.mine = false;
        this.isOpened = false;
        this.isMinesAround = false;
        this.isCellVisitedByIterator = false;
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

    public boolean isOpened()
    {
        return isOpened;
    }

    public void setOpened()
    {
        isOpened = true;
    }

    public void setCellVisitedByIterator()
    {
        isCellVisitedByIterator = true;
    }

    public boolean isCellVisitedByIterator()
    {
        return isCellVisitedByIterator;
    }

    public boolean isMinesAround()
    {
        return isMinesAround;
    }

    public void setMinesAround()
    {
        this.isMinesAround= true;
    }
}


