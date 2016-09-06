package com.example.tes.sapper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainMenu extends AppCompatActivity
{
    GameOptions gameOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

    }

    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.newGame:
                Intent intent = new Intent(this, GameActivity.class);
                startActivity(intent);
                break;
            case R.id.options:
                break;
            case R.id.quit:
                finish();
                break;
        }
    }
}
