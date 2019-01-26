package com.example.benura.snakegame2;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.widget.Toast;

import static android.widget.Toast.*;

public class SoundPlayer extends Menu{

    private static SoundPool soundPool;
    private static int hit,attack,destroy;
    public Context con;
    public Menu menu;

    public SoundPlayer(Context context){
        con=context;
        soundPool=new SoundPool(2, AudioManager.STREAM_MUSIC,0);
        hit=soundPool.load(context,R.raw.snakehit2,1);
        attack=soundPool.load(context,R.raw.snakeatt,2);
        destroy=soundPool.load(context,R.raw.destroyer,2);
        menu=new Menu();

    }

    public void playhit(){
        boolean isOk=menu.getVariable();
        if(isOk)

        soundPool.play(hit,1.0f,1.0f,1,0,3.0f);
    }

    public void attack(){
        boolean isOk=menu.getVariable();
        if(isOk)
        soundPool.play(attack,1.0f,1.0f,1,0,3.0f);
    }
    public void destroy(){

        boolean isOk=menu.getVariable();
        if(isOk)
        soundPool.play(destroy,1.0f,1.0f,1,2,3.0f);
    }
}
