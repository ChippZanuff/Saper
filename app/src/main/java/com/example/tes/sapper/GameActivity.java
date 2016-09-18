package com.example.tes.sapper;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class GameActivity extends Activity implements AdapterView.OnItemClickListener, View.OnClickListener, AdapterView.OnItemLongClickListener
{
    private Animation openCell;
    private GridView gridField;
    private Board board;
    private ImageAdapter adapter;
    private GameMechanics mechanics;
    private final int CELLS_AMOUNT = 132, AMOUNT_OF_MINES = 6, NUM_COLUMNS = 12, ROWS = 11;
    private boolean gameOver;
    private int retryId, quitId;
    private enum minesAmount
    {;
        private static final int one = 1, two = 2, three = 3, four = 4, five = 5;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gamefield);

        this.adapter = new ImageAdapter(this, this.CELLS_AMOUNT);
        this.gameOver = false;

        this.gridField = (GridView) findViewById(R.id.field);
        this.gridField.setAdapter(this.adapter);
        this.gridField.setOnItemClickListener(this);
        this.gridField.setOnItemLongClickListener(this);

        this.openCell = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.opencell);
        this.board = new Board(this.CELLS_AMOUNT, this.AMOUNT_OF_MINES, this.ROWS, this.NUM_COLUMNS);

        this.mechanics = new GameMechanics(this.board);
    }

    @Override
    public void onClick(View view)
    {
        if(view.getId() == this.retryId)
        {
            recreate();
        }
        else if (view.getId() == this.quitId)
        {
            finish();
        }
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int cellPosition, long l)
    {
        int xCoord = this.mechanics.transformToCoordRow(cellPosition);
        int yCoord = this.mechanics.transformToCoordCol(cellPosition);

        if(!this.board.isCellOpened(xCoord, yCoord));
        {
            if(!this.board.haveCellFlagById(xCoord, yCoord))
            {
                view.setBackgroundResource(R.drawable.flag);
            }
            else
            {
                view.setBackgroundResource(R.drawable.closedcell);
            }

            this.board.raiseOrPutDownFlagById(xCoord, yCoord);
        }
        return true;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int cellPosition, long l)
    {
        int xCoord = this.mechanics.transformToCoordRow(cellPosition);
        int yCoord = this.mechanics.transformToCoordCol(cellPosition);

        CellParam clickedCell = this.board.getCellById(xCoord,yCoord);

        if(clickedCell.isHaveMine() && !clickedCell.isHaveFlag())
        {
            this.openField(adapterView);
            this.createMenuAfterGameOver();
            return;
        }

        this.mechanics.cellsIteration(xCoord, yCoord, adapterView);

        ArrayList<View> emptyCellView = this.mechanics.getEmptyCellView();

        for(View cell : emptyCellView)
        {
            cell.setBackgroundResource(R.drawable.openedcell);
            cell.startAnimation(this.openCell);
        }
        
        ArrayList<View> minesAroundView = this.mechanics.getMinesAroundView();
        ArrayList<Integer> minesAroundAmount = this.mechanics.getMinesValue();

        for(int i = 0; i < minesAroundView.size(); i++)
        {
            this.setNumAdjacentMines(minesAroundView.get(i), minesAroundAmount.get(i));
        }

        emptyCellView.clear();
        minesAroundAmount.clear();
        minesAroundView.clear();

    }

    private void setNumAdjacentMines(View iteratedView, int adjacentMinesCounter)
    {
        switch(adjacentMinesCounter)
        {
            case minesAmount.one:
                iteratedView.setBackgroundResource(R.drawable.one);
                iteratedView.setAnimation(this.openCell);
                break;
            case minesAmount.two:
                iteratedView.setBackgroundResource(R.drawable.two);
                iteratedView.setAnimation(this.openCell);
                break;
            case minesAmount.three:
                iteratedView.setBackgroundResource(R.drawable.three);
                iteratedView.setAnimation(this.openCell);
                break;
            case minesAmount.four:
                iteratedView.setBackgroundResource(R.drawable.four);
                iteratedView.setAnimation(this.openCell);
                break;
            case minesAmount.five:
                iteratedView.setBackgroundResource(R.drawable.five);
                iteratedView.setAnimation(this.openCell);
                break;
        }
    }

    private void openField(AdapterView<?> adapterView)
    {
        int xCoord;
        int yCoord;

        for (int i = 0; i < this.board.getAmountOfCells(); i++)
        {
            xCoord = i / this.board.getNumColumns();
            yCoord = i % this.board.getNumColumns();

            if(this.board.isCellOpened(xCoord, yCoord) || this.board.isMinesAround(xCoord, yCoord))
            {
                continue;
            }

            View child = adapterView.getChildAt(i);
            this.board.setCellOpened(xCoord, yCoord);

            if(this.board.haveCellMineById(xCoord, yCoord))
            {
                child.setBackgroundResource(R.drawable.mine);
            }

            if(this.board.haveCellFlagById(xCoord, yCoord) || this.board.haveCellMineById(xCoord, yCoord) && this.board.haveCellFlagById(xCoord, yCoord))
            {
                child.setBackgroundResource(R.drawable.flag);
            }

            if(!this.board.haveCellMineById(xCoord, yCoord))
            {
                child.setBackgroundResource(R.drawable.openedcell);
            }

            child.startAnimation(this.openCell);
        }
    }

    private void createMenuAfterGameOver()
    {
        if(!this.gameOver)
        {
            this.gameOver = true;
            int wrapContent = LinearLayout.LayoutParams.WRAP_CONTENT;
            int gravity = Gravity.RIGHT;
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(wrapContent, wrapContent);
            params.gravity = gravity;

            LinearLayout layout = new LinearLayout(this);
            layout.setLayoutParams(params);

            this.createButtonsAfterGameOver(layout);

            LinearLayout llMain = (LinearLayout) findViewById(R.id.gameActivityLinearLayout);
            llMain.addView(layout);
        }
    }

    private void createButtonsAfterGameOver(LinearLayout layout)
    {
        Button retry = new Button(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1)
        {
            this.retryId = View.generateViewId();
            retry.setId(this.retryId);
        }
        retry.setText(R.string.retry);
        retry.setTextSize(40);
        retry.setOnClickListener(this);

        Button quit = new Button(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1)
        {
            this.quitId = View.generateViewId();
            quit.setId(this.quitId);
        }
        quit.setText(R.string.quit);
        quit.setTextSize(40);
        quit.setOnClickListener(this);

        layout.addView(retry);
        layout.addView(quit);
    }
}