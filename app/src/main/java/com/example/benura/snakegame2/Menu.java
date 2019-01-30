package com.example.benura.snakegame2;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

public class Menu extends AppCompatActivity {
    public static String username;
    private ProgressBar progressBar;
    public Button button1,button2,button3,resume;
    public  boolean mpOverPrepared = false;
    public Bitmap bitmap;
    public Context context;
    public static boolean isClick=false,isClicks=false;
    public static boolean isPlay=false,isDone=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
       // soundPlayer=new SoundPlayer(Menu.this);
        final MediaPlayer mp2 = MediaPlayer.create(this, R.raw.destroyer);
        if(isDone) {
            mp2.start();
            isPlay=true;
        }
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
                        isPlay=false;
                        mp2.stop();
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
                mp2.stop();
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
                    if(isPlay) {
                        mp2.pause();
                        isPlay = false;
                        isDone=false;
                    }

                }

                else{
                    isClicks=true;
                    isClick=false;
                    Toast.makeText(Menu.this,"Sounnd on",Toast.LENGTH_SHORT).show();
                    if(!isPlay) {
                        mp2.start();
                        isPlay=true;
                        isDone=true;
                    }

                }

            }
        });

        button3=findViewById(R.id.score);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isPlay=false;
                mp2.stop();
                Intent i=new Intent(Menu.this,DisplayScore.class);
                finish();
                startActivity(i);

            }
        });

        resume=findViewById(R.id.resume);
        resume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent(Menu.this,DisplayAbout.class);
                finish();
                startActivity(i);

            }
        });

    }
    public boolean getVariable()
    {

        return isClicks;
    }

}

