package com.example.benura.snakegame2;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

public class Menu extends AppCompatActivity {
    private ProgressBar progressBar;
    public Button button1,button2,button3;
    public  boolean mpOverPrepared = false;
    public MediaPlayer mpOver;
    public Bitmap bitmap;
    public Context context;
    public static boolean isClick=false,isClicks=false;
    private SoundPlayer soundPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        soundPlayer=new SoundPlayer(Menu.this);
        progressBar=(ProgressBar)findViewById(R.id.progressBar2);
        button1 = (Button) findViewById(R.id.start);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressBar.setVisibility(View.VISIBLE);
                new CountDownTimer(3000, 1000) {

                    public void onTick(long millisUntilFinished) {

                    }

                    public void onFinish() {

                        Intent i = new Intent(Menu.this, SnakeEngine.class);
                        finish();
                        startActivity(i);
                    }

                }.start();
            }
        });


        button2=findViewById(R.id.quit);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAffinity();
            }
        });

        ImageButton imageButton=findViewById(R.id.sound);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isClick==false) {
                    isClick=true;
                    isClicks=false;
                    Toast.makeText(Menu.this,"Sound off",Toast.LENGTH_SHORT).show();
                    AudioManager mgr = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
                    mgr.setStreamMute(AudioManager.STREAM_SYSTEM, false);

                }

                else{
                    isClicks=true;
                    isClick=false;
                    Toast.makeText(Menu.this,"Sounnd on",Toast.LENGTH_SHORT).show();
                    soundPlayer.destroy();

                }

            }
        });

    }
    public boolean getVariable()
    {

        return isClicks;
    }

}

