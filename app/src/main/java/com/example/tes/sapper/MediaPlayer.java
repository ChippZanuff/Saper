package com.example.tes.sapper;

import android.content.Context;

public class MediaPlayer
{
    private android.media.MediaPlayer mediaPlayer;
    private int explosion, backgroundMusic;
    private Logger log;
    private final String TAG = this.getClass().getSimpleName();

    public MediaPlayer(Logger log)
    {
        this.log = log;
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
                log.error(this.TAG, "Release method exception", e);
            }
        }
    }

    public void playExplosion(Context context)
    {
        this.release();
        this.mediaPlayer = android.media.MediaPlayer.create(context, this.explosion);
        this.mediaPlayer.start();
    }

    public void playBGMusic(Context context)
    {
        this.mediaPlayer = android.media.MediaPlayer.create(context, this.backgroundMusic);
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
