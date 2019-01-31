package com.example.benura.snakegame2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.inputmethodservice.Keyboard;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.renderscript.ScriptGroup;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import java.util.Random;


public class SnakeEngines extends SurfaceView implements Runnable {

    public int snakey,snakex;
    public static boolean news=false;
    public Context context;
    public Activity activity;
    private  Bitmap bmp;
    public SoundPlayer soundPlayer;
    public static Thread thread = null;

    public enum Heading {UP, RIGHT, DOWN, LEFT}

    private Heading heading = Heading.RIGHT;
    private int screenX;
    private int screenY;

    private int snakeLength;

    private int bobX;
    private int bobY;

    private int blockSize;

    private final int NUM_BLOCKS_WIDE = 40;
    private int numBlocksHigh;

    private long nextFrameTime;

    private final long FPS = 10;

    private final long MILLIS_PER_SECOND = 1000;

    private static int score,score2;

    private int[] snakeXs;
    private int[] snakeYs;

    public volatile boolean isPlaying;

    private Canvas canvas;
    private SurfaceHolder surfaceHolder;
    private int flag=0;

    private Paint paint;

    @Override
    public void run() {

      while (isPlaying) {

               if(updateRequired())
                update();
                draw();

                if(score==1000)
                    EndGame();

        }

        }

    public void pause() {
        isPlaying = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            // Error
        }
    }

    public void resume() {
        isPlaying = true;
        thread = new Thread(this);
        thread.start();
    }

    public void newGame() {
        // Start with a single snake segment
        snakeLength = 1;
        snakeXs[0] = 0;
        snakeYs[0] = numBlocksHigh / 2;

        spawnBob();

        score = 0;


        nextFrameTime = System.currentTimeMillis();
    }


    public SnakeEngines(Context context, Point size) {
        super(context);
        activity= (Activity) context;
        context = context;
        screenX = size.x;
        screenY = size.y;
        this.bmp = BitmapFactory.decodeResource(getResources(), R.drawable.ic_android);
        surfaceHolder = getHolder();
        surfaceHolder.addCallback(new SurfaceHolder.Callback() {

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) { }

            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                Canvas canvas = holder.lockCanvas();
                if (canvas != null) {
                    draw(canvas);
                    holder.unlockCanvasAndPost(canvas);
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) { }

        });

        blockSize = screenX / NUM_BLOCKS_WIDE;

        numBlocksHigh = screenY / blockSize;

        soundPlayer=new SoundPlayer(context);

        paint = new Paint();

        // If you score 200 you are rewarded with a crash achievement!
        snakeXs = new int[200];
        snakeYs = new int[200];

      newGame();
    }


    public void spawnBob() {

            Random random = new Random();
            bobX = random.nextInt(NUM_BLOCKS_WIDE - 1) + 1;
            bobY = random.nextInt(numBlocksHigh - 1) + 1;

    }

    private void eatBob(){

        soundPlayer.attack();
        if(score%70==0&&score!=0)
        snakeLength=snakeLength+2;

        else
            snakeLength++;

        spawnBob();

        if(flag==0)
        score = score + 10;

        else
            score=score+30;

    }

    private void moveSnake(){
        // Move the body

        for (int i = snakeLength; i > 0; i--) {
            snakeXs[i] = snakeXs[i - 1];
            snakeYs[i] = snakeYs[i - 1];

        }

        switch (heading) {
            case UP:
                snakeYs[0]--;
                snakey=snakeYs[0];
                break;

            case RIGHT:
                snakeXs[0]++;
                snakex=snakeXs[0];
                break;

            case DOWN:
                snakeYs[0]++;
                snakey=snakeYs[0];
                break;

            case LEFT:
                snakeXs[0]--;
                snakex=snakeXs[0];
                break;
        }
    }

    public void update() {
        // Did the head of the snake eat Bob?
        if (snakeXs[0] == bobX && snakeYs[0] == bobY) {
            eatBob();
        }


        moveSnake();

        if (detectDeath()) {
            soundPlayer.playhit();
            EndGame();
            //newGame();
        }

       else if(news) {
            newGame1();
           // EndGame();
            news = false;
        }

    }

    private boolean detectDeath(){
        // Has the snake died?
        boolean dead = false;

        if (snakeXs[0] == -1) news = true;
        if (snakeXs[0] >= NUM_BLOCKS_WIDE) news = true;
        if (snakeYs[0] == -1) news = true;
        if (snakeYs[0] == numBlocksHigh) news = true;

        for (int i = snakeLength - 1; i > 0; i--) {
            if ((i > 4) && (snakeXs[0] == snakeXs[i]) && (snakeYs[0] == snakeYs[i])) {
                dead = true;
            }
        }

        return dead;
    }

   public void draw() {

       if (surfaceHolder.getSurface().isValid()) {
           canvas = surfaceHolder.lockCanvas();

           canvas.drawColor(Color.argb(255, 26, 182, 144));

           paint.setColor(Color.argb(255, 255, 255, 255));



           paint.setTextSize(90);
           canvas.drawText("Score:" + score, 20, 70, paint);


           for (int i = 0; i < snakeLength; i++) {
               canvas.drawRect(snakeXs[i] * blockSize,
                       (snakeYs[i] * blockSize),
                       (snakeXs[i] * blockSize) + blockSize,
                       (snakeYs[i] * blockSize) + blockSize,
                       paint);
           }

           // Set the color of the paint to draw Bob red
           paint.setColor(Color.argb(255, 255, 25, 25));

           if(score==0||score%70!=0) {
               canvas.drawCircle(bobX * blockSize, bobY * blockSize, blockSize / 2, paint);
               flag=0;
           }

           else if(score%70==0&&score!=0) {

               canvas.drawCircle(bobX * blockSize, bobY * blockSize, blockSize, paint);

               flag=1;
           }

        /*   canvas.drawRect(bobX * blockSize,
                   (bobY * blockSize),
                   (bobX * blockSize) + blockSize,
                   (bobY * blockSize) + blockSize,
                   paint);   */

           // Unlock the canvas and reveal the graphics for this frame
           surfaceHolder.unlockCanvasAndPost(canvas);
       }
   }


    public boolean updateRequired() {


        if(nextFrameTime <= System.currentTimeMillis()){

            nextFrameTime =System.currentTimeMillis() + MILLIS_PER_SECOND / FPS;

            return true;
        }

        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {

        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_UP:
                if (motionEvent.getX() >= screenX / 2) {
                    switch(heading){
                        case UP:
                            heading = Heading.RIGHT;
                            break;
                        case RIGHT:
                            heading = Heading.DOWN;
                            break;
                        case DOWN:
                            heading = Heading.LEFT;
                            break;
                        case LEFT:
                            heading = Heading.UP;
                            break;
                    }
                } else {
                    switch(heading){
                        case UP:
                            heading = Heading.LEFT;
                            break;
                        case LEFT:
                            heading = Heading.DOWN;
                            break;
                        case DOWN:
                            heading = Heading.RIGHT;
                            break;
                        case RIGHT:
                            heading = Heading.UP;
                            break;
                    }
                }
        }
        return true;
    }

    public void EndGame(){

       Intent i = new Intent(activity, EndGame.class);
       Bundle bundle=new Bundle();
       bundle.putInt("score",score);
       i.putExtras(bundle);
        activity.finish();
        activity.startActivity(i);

    }

    public void newGame1() {
        // Start with a single snake segment
        if(snakeXs[0]>=NUM_BLOCKS_WIDE) {
            snakeXs[0] = 0;
            snakeYs[0] =snakey;
        }

        else if(snakeXs[0]==-1){

            snakeXs[0] = NUM_BLOCKS_WIDE;
            snakeYs[0] = snakey;

        }

        else if(snakeYs[0]>=numBlocksHigh){

            snakeXs[0] = snakex;
            snakeYs[0] =0;
        }

        else if(snakeYs[0]==-1){

            snakeXs[0] = snakex;
            snakeYs[0] =numBlocksHigh;

        }
        nextFrameTime = System.currentTimeMillis();
    }


}




