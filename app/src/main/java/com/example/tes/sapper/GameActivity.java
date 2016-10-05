package com.example.tes.sapper;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class GameActivity extends Activity implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener
{
    private Animation openCell;
    private GridView gridField;
    private Board board;
    private ImageAdapter adapter;
    private GameMechanics mechanics;
    private TextView flagsCounter;
    private final int CELLS_AMOUNT = 132, AMOUNT_OF_MINES = 6, NUM_COLUMNS = 12, ROWS = 11;
    private boolean gameOver;
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

        this.flagsCounter = (TextView) findViewById(R.id.flagCounter);
        this.setFlagsAmount();

        this.mechanics = new GameMechanics(this.board);
    }

    public void onClick(View view)
    {
        if(view.getId() == R.id.retry)
        {
            recreate();
        }
        else if (view.getId() == R.id.menu)
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
            if(!this.board.haveCellFlagById(xCoord, yCoord) && this.board.getFlagsLeft() > 0)
            {
                view.setBackgroundResource(R.drawable.flag);
            }
            else
            {
                view.setBackgroundResource(R.drawable.closedcell);
            }

            this.board.raiseOrPutDownFlagById(xCoord, yCoord);
            this.setFlagsAmount();
        }

        this.winCondition();

        if(this.gameOver)
        {
            this.openField(adapterView);
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

            minesAroundView.get(i).setAnimation(this.openCell);
        }

        emptyCellView.clear();
        minesAroundAmount.clear();
        minesAroundView.clear();
    }

    private void winCondition()
    {
        Log.d("Win phase", this.board.isFlaggedCellsAreMined() + "");
        if(this.board.isFlaggedCellsAreMined())
        {
            this.createMenuAfterGameOver();
        }
    }

    private void setFlagsAmount()
    {
        String flagsLeft = "Flags left: " + String.valueOf(this.board.getFlagsLeft());
        this.flagsCounter.setText(flagsLeft);
    }

    private void setNumAdjacentMines(View iteratedView, int adjacentMinesCounter)
    {
        switch(adjacentMinesCounter)
        {
            case minesAmount.one:
                iteratedView.setBackgroundResource(R.drawable.one);
                break;
            case minesAmount.two:
                iteratedView.setBackgroundResource(R.drawable.two);
                break;
            case minesAmount.three:
                iteratedView.setBackgroundResource(R.drawable.three);
                break;
            case minesAmount.four:
                iteratedView.setBackgroundResource(R.drawable.four);
                break;
            case minesAmount.five:
                iteratedView.setBackgroundResource(R.drawable.five);
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
        LayoutInflater inflater = LayoutInflater.from(this);
        View lay = inflater.inflate(R.layout.gameoverbuttons, layout, false);
        layout.addView(lay);
    }
}