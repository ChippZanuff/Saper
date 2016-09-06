package com.example.tes.sapper;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ImageAdapter extends BaseAdapter
{
    Context context;
    int countOfCell;
    //int[] thumbIds;

    public ImageAdapter(Context context, int numOfCells)
    {
        this.context = context;
        countOfCell = numOfCells;

        //this.thumbIds = new int[numOfCells];
    }

    @Override
    public int getCount()
    {
        return this.countOfCell;
    }

    @Override
    public Object getItem(int i)
    {
        return null;
    }

    @Override
    public long getItemId(int i)
    {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup)
    {
        TextView cell;
        int height = 60;
        int width = 65;

        if(view == null)
        {
            cell = new TextView(this.context);
        }
        else
        {
            cell = (TextView) view;
        }
        cell.setLayoutParams(new AbsListView.LayoutParams(width, height));

        cell.setBackgroundResource(R.drawable.closedcell);

        return cell;
    }

}
