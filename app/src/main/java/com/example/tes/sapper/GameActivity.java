package com.example.tes.sapper;

import android.app.Activity;
import android.os.Bundle;
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
    private Logger log;
    private MediaPlayer myMediaPlayer;
    private TextView flagsCounter;
    private final int CELLS_AMOUNT = 132, AMOUNT_OF_MINES = 6, NUM_COLUMNS = 12, ROWS = 11;
    private boolean gameOver, isVictory;
    private enum minesAmount
    {;
        private static final int one = 1, two = 2, three = 3, four = 4, five = 5;
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        this.myMediaPlayer.release();
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        this.myMediaPlayer.pause();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        this.myMediaPlayer.resume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gamefield);
        this.myMediaPlayer = new MediaPlayer(new Logger());
        this.myMediaPlayer.playBGMusic(this);

        this.log = new Logger();
        this.log.setTAG(getClass().getSimpleName());

        this.adapter = new ImageAdapter(this, this.CELLS_AMOUNT, new Logger());
        this.gameOver = false;

        this.gridField = (GridView) findViewById(R.id.field);
        this.gridField.setAdapter(this.adapter);
        this.gridField.setOnItemClickListener(this);
        this.gridField.setOnItemLongClickListener(this);

        this.openCell = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.opencell);
        this.board = new Board(this.CELLS_AMOUNT, this.AMOUNT_OF_MINES, this.ROWS, this.NUM_COLUMNS, new Logger());

        this.flagsCounter = (TextView) findViewById(R.id.flagCounter);
        this.setFlagsAmount();

        this.mechanics = new GameMechanics(this.board, new Logger());
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
        CellParam cell = this.board.getCellById(xCoord, yCoord);

        if(cell == null || cell.isOpen())
        {
            this.log.info("Cell " + xCoord + ", " + yCoord + " is opened or equals null, end method");
            return true;
        }

        if(!cell.hasFlag() && this.board.getFlagsLeft() > 0)
        {
            this.log.info("On cell " + xCoord + "," + yCoord + "flag image is set");
            view.setBackgroundResource(R.drawable.flag);
        }
        else
        {
            this.log.info("On cell " + xCoord + "," + yCoord + "closed cell image is set");
            view.setBackgroundResource(R.drawable.closedcell);
        }

        this.board.raiseOrPutDownFlagById(cell);
        this.setFlagsAmount();

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

        if(clickedCell.hasMine() && !clickedCell.hasFlag())
        {
            this.log.info("Mined cell " + xCoord + "," + yCoord + "is clicked, lose acquired");
            this.myMediaPlayer.playExplosion(this);
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
        if(this.board.isAllCellsDemined())
        {
            this.log.info("All mines deactivated, victory acquired");
            this.isVictory = true;
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
        CellParam cell;

        for (int i = 0; i < this.board.getAmountOfCells(); i++)
        {
            xCoord = i / this.board.getNumColumns();
            yCoord = i % this.board.getNumColumns();
            cell = this.board.getCellById(xCoord, yCoord);

            if(cell.isOpen() || cell.isMinesAround())
            {
                continue;
            }

            View child = adapterView.getChildAt(i);
            cell.makeOpen();

            if(cell.hasMine())
            {
                child.setBackgroundResource(R.drawable.mine);
            }

            if(cell.hasFlag())
            {
                child.setBackgroundResource(R.drawable.flag);
            }

            if(cell.hasMine() && cell.hasFlag())
            {
                child.setBackgroundResource(R.drawable.demined);
            }

            if(!cell.hasMine())
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

            Animation scaleCongrats = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.scale);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(wrapContent, wrapContent);
            params.gravity = gravity;

            LinearLayout layout = new LinearLayout(this);
            layout.setLayoutParams(params);

            this.createButtonsAfterGameOver(layout);

            LinearLayout llMain = (LinearLayout) findViewById(R.id.gameActivityLinearLayout);
            llMain.addView(layout);
            TextView congrats = (TextView) findViewById(R.id.congrats);
            congrats.setVisibility(View.VISIBLE);

            if(this.isVictory)
            {
                congrats.setText(R.string.win);
            } else
            {
                congrats.setText(R.string.lose);
            }

            congrats.startAnimation(scaleCongrats);
        }
    }

    private void createButtonsAfterGameOver(LinearLayout layout)
    {
        LayoutInflater inflater = LayoutInflater.from(this);
        View lay = inflater.inflate(R.layout.gameoverbuttons, layout, false);
        layout.addView(lay);
    }
}