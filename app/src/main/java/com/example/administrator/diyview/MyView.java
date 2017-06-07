package com.example.administrator.diyview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Administrator on 2017/6/7 0007.
 */

public class MyView extends View {
    //被覆盖的内容图层
    private Bitmap bpBackground;
    //用来当做覆盖用的图层
    private Bitmap bpForeground;
    //用来当做覆盖用的图层的画布
    private Canvas mCanvas;
    //模拟手指头刮开路径的画笔
    private Paint pathPaint;
    //手指头刮开的路径
    private Path path;

    //用来当做覆盖用的图层的文字画笔
    private Paint contentPaint;
    //用来当做覆盖用的图层的文字内容
    private String content = "刮刮看咯~";



    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        bpBackground= BitmapFactory.decodeResource(getResources(),R.drawable.a7);
        bpForeground=Bitmap.createBitmap(bpBackground.getWidth(),bpBackground.getHeight(),Bitmap.Config.ARGB_8888);

        pathPaint=new Paint();
        pathPaint.setAlpha(0);
        pathPaint.setStyle(Paint.Style.STROKE);
        pathPaint.setStrokeWidth(20);
        //取两层绘制交集，显示下层
        pathPaint.setXfermode(new PorterDuffXfermode((PorterDuff.Mode.DST_IN)));
        pathPaint.setStrokeJoin(Paint.Join.ROUND);
        pathPaint.setStrokeCap(Paint.Cap.ROUND);

        //初始化模拟手指头刮开的路径
        path = new Path();

        mCanvas=new Canvas(bpForeground);
        contentPaint=new Paint();
        contentPaint.setColor(Color.BLUE);
        contentPaint.setTextSize(100);
        contentPaint.setStrokeWidth(20);


        mCanvas.drawColor(Color.GRAY);
        mCanvas.drawText(content,mCanvas.getWidth()/4,mCanvas.getHeight()/2,contentPaint);


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                path.reset();
                path.moveTo(event.getX(),event.getY());
                break;
            case MotionEvent.ACTION_MOVE:
                path.lineTo(event.getX(),event.getY());
                break;
        }
        mCanvas.drawPath(path,pathPaint);
        invalidate();





     return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(bpBackground,0,0,null);
        canvas.drawBitmap(bpForeground,0,0,null);
    }
}
