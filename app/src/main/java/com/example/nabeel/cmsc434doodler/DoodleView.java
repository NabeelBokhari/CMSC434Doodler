package com.example.nabeel.cmsc434doodler;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by Nabeel on 3/9/2016.
 */
public class DoodleView extends View {
    public Paint paintDoodler = new Paint();
    public Path path = new Path();
    public Canvas canvas = new Canvas();

    public LinkedHashMap<Path, Paint> pathPaintMap = new LinkedHashMap<Path, Paint>();

    public DoodleView(Context context) {
        super(context);
        init(null, 0);
    }

    public DoodleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public DoodleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, defStyleAttr);
    }

    private void init(AttributeSet attrs, int defStyle) {
        this.setBackgroundColor(Color.WHITE);
        paintDoodler.setColor(Color.parseColor("#FF9800"));
        paintDoodler.setAntiAlias(true);
        paintDoodler.setStyle(Paint.Style.STROKE);
        paintDoodler.setStrokeWidth(5.0f);

        pathPaintMap.put(path, paintDoodler);
    }

    private void init(float size, int color, int opacity) {
        paintDoodler.setColor(color);
        paintDoodler.setAntiAlias(true);
        paintDoodler.setStyle(Paint.Style.STROKE);
        paintDoodler.setStrokeWidth(size);
        paintDoodler.setAlpha(opacity);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
//
//        Bitmap bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
//        canvas.setBitmap(bitmap);
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for(Path path : pathPaintMap.keySet()) {
            canvas.drawPath(path, pathPaintMap.get(path));
        }
        //canvas.drawPath(path, paintDoodler);
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        float touchX = motionEvent.getX();
        float touchY = motionEvent.getY();

        switch(motionEvent.getAction()){
            case MotionEvent.ACTION_DOWN:
                path = new Path();
                pathPaintMap.put(path, paintDoodler);
                path.moveTo(touchX, touchY);
                break;
            case MotionEvent.ACTION_MOVE:
                path.lineTo(touchX, touchY);
                break;
            case MotionEvent.ACTION_UP:
                break;

        }

        invalidate();
        return true;
    }

    public void clearCanvas() {
        for(Path path : pathPaintMap.keySet()) {
            path.reset();
        }
        /*path = new Path();
        canvas.drawColor(Color.WHITE);*/
        invalidate();
        pathPaintMap.clear();
        path = new Path();
        pathPaintMap.put(path, paintDoodler);
    }

    public void setPaintSize(float width) {
        int oldColor = paintDoodler.getColor();
        int oldOpacity = paintDoodler.getAlpha();
        paintDoodler = new Paint();
        init(width, oldColor, oldOpacity);
        path = new Path();
        pathPaintMap.put(path, paintDoodler);
        //paintDoodler.setStrokeWidth(width);
    }

    public void setPaintColor(int color) {
        float oldSize = paintDoodler.getStrokeWidth();
        int oldOpacity = paintDoodler.getAlpha();
        paintDoodler = new Paint();
        init(oldSize, color, oldOpacity);
        path = new Path();
        pathPaintMap.put(path, paintDoodler);
        //paintDoodler.setColor(color);
    }

    public void setPaintOpacity(int alpha) {
        float oldSize = paintDoodler.getStrokeWidth();
        int oldColor = paintDoodler.getColor();
        paintDoodler = new Paint();
        init(oldSize, oldColor, alpha);
        path = new Path();
        pathPaintMap.put(path, paintDoodler);
    }

    public void undo() {
        if(!pathPaintMap.isEmpty()) {
            Set<Path> pathSet = pathPaintMap.keySet();
            Iterator<Path> iterator = pathSet.iterator();
            Path p = null;
            while (iterator.hasNext()) {
                p = iterator.next();
            }
            pathPaintMap.remove(p);
            invalidate();
        }
    }
}
