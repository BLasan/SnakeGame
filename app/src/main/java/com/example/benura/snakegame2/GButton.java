package com.example.benura.snakegame2;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.view.SurfaceHolder;
import android.view.SurfaceView;



public class GButton extends SurfaceView {

    SurfaceHolder surfaceHolder;
    public Matrix btn_matrix = new Matrix();

    public RectF btn_rect;

    float width;
    float height;
    Bitmap bg;


    public GButton(Context context,float width, float height)
    {
        super(context);
        this.width = width;
        this.height = height;
        this.bg = BitmapFactory.decodeResource(getResources(), R.drawable.ic_android);


        btn_rect = new RectF(0, 0, width, height);
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

    }


    public void setPosition(float x, float y)
    {
        btn_matrix.setTranslate(x, y);
        btn_matrix.mapRect(btn_rect);
    }

    public void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.drawBitmap(bg, btn_matrix, null);
    }
}
