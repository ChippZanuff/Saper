package com.example.tes.sapper;

import android.view.View;
import android.view.animation.Animation;
import android.widget.AdapterView;

import java.util.ArrayList;

public class GameMechanics
{
    private ArrayList<CellParam> cells;
    private Animation openCell;

    public GameMechanics(ArrayList<CellParam> cells, Animation openCell)
    {
        this.openCell = openCell;
        this.cells = cells;
    }

    public boolean isCellOpened(int cellPosition)
    {
        return this.cells.get(cellPosition).isOpened();
    }

    private void openCell(View view)
    {
        view.setBackgroundResource(R.drawable.openedcell);
        view.startAnimation(this.openCell);
    }

    public boolean clickedCellHaveMine(int cellPosition, View view, AdapterView<?> adapterView)
    {
        if(this.cells.get(cellPosition) != null && this.cells.get(cellPosition).isHaveMine())
        {
            view.startAnimation(this.openCell);
            this.openField(adapterView);
            return true;
        }
        return false;
    }

    public void clickedCellHaveNoMine(int cellPosition, View view)
    {
        if(this.cells.get(cellPosition) != null && !this.cells.get(cellPosition).isHaveMine())
        {
            this.openCell(view);
        }
    }

    private void openField(AdapterView<?> adapterView)
    {
        for (int i = 0; i < this.cells.size(); i++)
        {
            if(this.cells.get(i).isOpened())
            {
                continue;
            }

            View child = adapterView.getChildAt(i);
            this.cells.get(i).setOpened();

            if(this.cells.get(i).isHaveMine())
            {
                child.setBackgroundResource(R.drawable.mine);
            }

            if(this.cells.get(i).isHaveFlag() || this.cells.get(i).isHaveMine() && this.cells.get(i).isHaveFlag())
            {
                child.setBackgroundResource(R.drawable.flag);
            }

            if(!this.cells.get(i).isHaveMine())
            {
                child.setBackgroundResource(R.drawable.openedcell);
            }
            child.startAnimation(this.openCell);
        }
    }

    public boolean setFlagOnBoard(View view, boolean cellOpened, boolean isHaveFlag)
    {
        if(!cellOpened)
        {
            return false;
        }

        if(!isHaveFlag)
        {
            view.setBackgroundResource(R.drawable.flag);
        }
        else
        {
            view.setBackgroundResource(R.drawable.closedcell);
        }
        return true;
    }
}
