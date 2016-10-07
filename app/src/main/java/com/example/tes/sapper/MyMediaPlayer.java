package com.example.tes.sapper;

import android.content.Context;
import android.media.MediaPlayer;

public class MyMediaPlayer
{
    private MediaPlayer mediaPlayer;
    private int explosion, backgroundMusic;

    public MyMediaPlayer()
    {
        this.explosion = R.raw.explosion;
        this.backgroundMusic = R.raw.vabankmp3;
    }

    public void release()
    {
        if(this.mediaPlayer != null)
        {
            try
            {
                this.mediaPlayer.release();
                mediaPlayer = null;
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    public void playExplosion(Context context)
    {
        this.release();
        this.mediaPlayer = MediaPlayer.create(context, this.explosion);
        this.mediaPlayer.start();
    }

    public void playBGMusic(Context context)
    {
        this.mediaPlayer = MediaPlayer.create(context, this.backgroundMusic);
        this.mediaPlayer.start();
    }

    public void pause()
    {
        if(this.mediaPlayer == null)
        {
            return;
        }

        this.mediaPlayer.pause();
    }

    public void resume()
    {
        if(this.mediaPlayer == null)
        {
            return;
        }

        this.mediaPlayer.start();
    }
}
