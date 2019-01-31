package com.example.benura.snakegame2;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;


public class SnakeEngine extends Activity {
    public  static boolean isBack=false;
    public boolean isPause=false;
    public  Button button1;
    public  Display display;
    public Point size;
    SnakeEngines snakeEngine;
    FrameLayout game;
    RelativeLayout GameButtons;//Holder for the buttons
    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snake_engine);
        display = getWindowManager().getDefaultDisplay();
        size = new Point();
        display.getSize(size);
       snakeEngine=new SnakeEngines(SnakeEngine.this,size);
       game=new FrameLayout(this);
       GameButtons=new RelativeLayout(this);
       final Button button=new Button(this);
       button1=new Button(this);
       button1.setText("PAUSE");
       button1.setId(1235);
       button.setText("QUIT");
       button.setId(1234);
        RelativeLayout.LayoutParams params1=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
       RelativeLayout.LayoutParams params=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
       GameButtons.setLayoutParams(params);
       GameButtons.setLayoutParams(params1);
       params1.setMargins(size.x-600,size.y-200,20,5);
       params.setMargins(size.x-280,size.y-200,5,5);
       GameButtons.addView(button1);
       GameButtons.addView(button);
      // params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT,RelativeLayout.TRUE);
      // params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM,RelativeLayout.TRUE);
        button1.setLayoutParams(params1);
       button.setLayoutParams(params);
       game.addView(snakeEngine);
       game.addView(GameButtons);
       setContentView(game);


        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isPause==false){
                    onPause();
                    button1.setText("Resume");
                    isPause=true;
                }

                else{
                    onResume();
                    button1.setText("Pause");
                    isPause=false;

                }
            }
        });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SnakeEngine.this, Menu.class);
                isBack=false;
                finish();
                startActivity(i);
            }
        });

        }

    // Start the thread in snakeEngine
    @Override
    protected void onResume() {
        super.onResume();
        snakeEngine.resume();
    }

    // Stop the thread in snakeEngine
    @Override
    protected void onPause() {
        super.onPause();
        snakeEngine.pause();
    }

    @Override
    public void onBackPressed() {
        //   boolean fromNewActivity=true;
        button1.setText("RESUME");
       // onPause();
        isBack=true;
        Intent mainIntent = new Intent(SnakeEngine.this, Menu.class);
       // mainIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);

        startActivityForResult(mainIntent, 0);
        new CountDownTimer(3000,1000){

            public void onTick(long millisUntilFinished) {

            }

            public void onFinish() {

                button1.setText("PAUSE");
            }

        }.start();

    }

    @Override
    public void onRestoreInstanceState(Bundle bundle){

        super.onRestoreInstanceState(bundle);
        String state=bundle.getString("saved");
        if(state==null){

        }

        else
            Toast.makeText(SnakeEngine.this,"Done",Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onSaveInstanceState(Bundle save) {

        super.onSaveInstanceState(save);

    }



}



