package com.example.tes.sapper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainMenu extends AppCompatActivity
{
    GameOptions gameOptions;
    private Logger log;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        this.log = new Logger();
        this.log.setTAG(getClass().getSimpleName());
    }

    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.newGame:
                this.log.info("New game button was clicked");
                Intent intent = new Intent(this, GameActivity.class);
                startActivity(intent);
                break;
            case R.id.options:
                this.log.info("Options button was clicked");
                break;
            case R.id.quit:
                this.log.info("Quit button was clicked");
                finish();
                break;
        }
    }
}
