package com.example.tes.sapper;

public class GameOptions
{
    private final int DEFAULT_FIELD_CELLS_AMOUNT = 108;
    private int fieldCellsAmount;

    public GameOptions(int fieldCellsAmount)
    {
        this.fieldCellsAmount = fieldCellsAmount;
    }

    public int getFieldCellsAmount()
    {
        return this.fieldCellsAmount;
    }

    public int getDEFAULT_FIELD_CELLS_AMOUNT()
    {
        return this.DEFAULT_FIELD_CELLS_AMOUNT;
    }
}
