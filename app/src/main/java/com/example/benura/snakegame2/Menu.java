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

import static com.example.benura.snakegame2.SnakeEngines.thread;

public class Menu extends AppCompatActivity{

    private ProgressBar progressBar;
    public Button start,quit,button3,resume;
    public ImageButton about;
    public static boolean isClick=false,isClicks=true;
    public static boolean isPlay=false,isDone=true;
    public MediaPlayer mp2;
    SnakeEngine snakeEngine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
       // soundPlayer=new SoundPlayer(Menu.this);
        mp2 = MediaPlayer.create(Menu.this, R.raw.destroyer);
        if(isDone) {
            mp2.start();
            isPlay=true;
        }

        progressBar=(ProgressBar)findViewById(R.id.progressBar2);
        start = (Button) findViewById(R.id.start);
        start.setOnClickListener(new View.OnClickListener() {
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


        quit=findViewById(R.id.quit);
        quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp2.stop();
                finishAffinity();
            }
        });

        ImageButton sound=findViewById(R.id.sound);
        sound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isClick==false) {
                    isClick=true;

                    Toast.makeText(Menu.this,"Sound off",Toast.LENGTH_SHORT).show();
                    if(isPlay) {
                        mp2.pause();
                        isPlay = false;
                        isDone=false;
                        isClicks=false;
                    }

                }

                else{

                    isClick=false;
                    Toast.makeText(Menu.this,"Sounnd on",Toast.LENGTH_SHORT).show();
                    if(!isPlay) {
                        mp2.start();
                        isClicks=true;
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

        about=findViewById(R.id.about);
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isPlay=false;
                mp2.stop();
                Intent i=new Intent(Menu.this,DisplayAbout.class);
                finish();
                startActivity(i);

            }
        });
        snakeEngine=new SnakeEngine();

        resume=findViewById(R.id.resume);

       /* if(!snakeEngine.getBack()){

            Bundle bundle=getIntent().getExtras();
            String key=bundle.getString("key");
            if(key=="true") {
                resume.setVisibility(View.VISIBLE);
                isBack=false;
            }

        }*/
        resume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isPlay=false;
                mp2.stop();

                Intent mainIntent = new Intent(Menu.this, SnakeEngine.class);
                mainIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivityForResult(mainIntent, 0);


            }
        });


    }

    @Override
    public void onBackPressed() {
     //   boolean fromNewActivity=true;
        System.exit(0);
       finishAffinity();

    }


    public boolean getVariable()
    {

        return isClicks;
    }

    @Override
    protected void onResume() {
        if (SnakeEngine.isBack){
            resume.setVisibility(View.VISIBLE);
        }

        else
            resume.setVisibility(View.INVISIBLE);
        super.onResume();
    }

}

