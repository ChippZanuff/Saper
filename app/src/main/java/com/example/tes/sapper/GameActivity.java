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
    private ImageAdapter adapter;
    private CellMiner cellMiner;
    private ArrayList<CellParam> cells;
    private GameMechanics mechanics;
    private final int CELLS_AMOUNT = 132, AMOUNT_OF_MINES = 6;
    private int retryId, quitId;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gamefield);

        this.cellMiner = new CellMiner(CELLS_AMOUNT, AMOUNT_OF_MINES);

        this.adapter = new ImageAdapter(this, this.CELLS_AMOUNT);

        this.gridField = (GridView) findViewById(R.id.field);
        this.gridField.setAdapter(this.adapter);
        this.gridField.setOnItemClickListener(this);
        this.gridField.setOnItemLongClickListener(this);

        this.openCell = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.opencell);
        this.cells = this.cellMiner.createBoard();

        this.mechanics = new GameMechanics(this.cells, openCell);
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
        if(this.mechanics.isCellOpened(cellPosition));
        {
            if(!this.cells.get(cellPosition).isHaveFlag())
            {
                view.setBackgroundResource(R.drawable.flag);
            }
            else
            {
                view.setBackgroundResource(R.drawable.closedcell);
            }

            this.cells.get(cellPosition).raiseOrPutDownFlag();
        }
        return true;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int cellPosition, long l)
    {
        if(!this.mechanics.isCellOpened(cellPosition) && !this.cells.get(cellPosition).isHaveFlag())
        {
            this.mechanics.clickedCellHaveNoMine(cellPosition, view);

            if(this.mechanics.clickedCellHaveMine(cellPosition, view, adapterView))
            {
                this.createMenuAfterGameOver();
            }
            this.cells.get(cellPosition).setOpened();
        }
    }

    private void createMenuAfterGameOver()
    {
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