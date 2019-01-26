package com.example.benura.snakegame2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.SurfaceHolder;
import android.view.View;

import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class SnakeEngine extends Activity {
    public boolean isPause=false;
    FrameLayout frameLayout;
    RelativeLayout buttons;
    SnakeEngines snakeEngine;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snake_engine);
        final Button button=new Button(this);
        button.setText("Pause");
        button.setPadding(100,50,100,50);
        button.setTextColor(Color.argb(222, 100, 10, 10));
        button.setTextSize(15);
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
            snakeEngine = new SnakeEngines(SnakeEngine.this, size);

              LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
             button.setGravity(Gravity.CENTER);
             params.setMargins(1000,2000,5,5);
             button.setLayoutParams(params);

             button.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {

                     if(isPause==false){
                         onPause();
                         button.setText("Resume");
                         isPause=true;
                     }

                     else{
                         onResume();
                         button.setText("Pause");
                         isPause=false;

                     }
                 }
             });

        final Button button1=new Button(this);
        button1.setText("BACk");
        button1.setPadding(100,50,100,50);
        button1.setTextColor(Color.argb(222, 100, 10, 10));
        button1.setTextSize(15);
        button1.setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params1.setMargins(20,2000,5,5);
        button1.setLayoutParams(params);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SnakeEngine.this, Menu.class);
               finish();
               startActivity(i);
            }
        });



            setContentView(snakeEngine);
            addContentView(button1,params);
            addContentView(button,params1);

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


}



