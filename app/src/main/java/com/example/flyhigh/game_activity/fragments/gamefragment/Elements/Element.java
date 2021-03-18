package com.example.flyhigh.game_activity.fragments.gamefragment.Elements;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public abstract class Element
{
    private int x,y;
    private Bitmap bitmap;

    public Element(int x, int y, Bitmap bitmap, int width, int height)
    {
        this.x = x;
        this.y = y;
        this.bitmap = Bitmap.createScaledBitmap(bitmap, width, height, false);
    }

    public int getX()
    {
        return x;
    }

    public void setX(int x)
    {
        this.x = x;
    }

    public int getY()
    {
        return y;
    }

    public void setY(int y)
    {
        this.y = y;
    }

    public Bitmap getBitmap()
    {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap)
    {
        this.bitmap = bitmap;
    }

    public Rect getCollisionShape()
    {
        return new Rect(getX(), getY(), getX() + getBitmap().getWidth(), getY() + getBitmap().getHeight());
    }

    public synchronized void drawElement(Canvas canvas, Paint paint)
    {
        canvas.drawBitmap(bitmap, x, y, paint);
    }
}
