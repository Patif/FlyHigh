package com.example.flyhigh.game_activity.fragments.gamefragment.Elements.obstacles;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

import com.example.flyhigh.R;
import com.example.flyhigh.main_activity.MainActivity;

public class Blade extends Obstacle
{
    private Bitmap[] blades = new Bitmap[8];
    private int index = 0;
    public Blade(Resources res)
    {
        super(MainActivity.screenWidth/4, MainActivity.screenWidth/4, BitmapFactory.decodeResource(res, R.drawable.blade_1));
        Log.i("Blade size", String.valueOf(getBitmap().getWidth()));
        blades[0] = getBitmap();
        blades[1] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(res, R.drawable.blade_2), getBitmap().getWidth(), getBitmap().getHeight(), false);
        blades[2] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(res, R.drawable.blade_3), getBitmap().getWidth(), getBitmap().getHeight(), false);
        blades[3] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(res, R.drawable.blade_4), getBitmap().getWidth(), getBitmap().getHeight(), false);
        blades[4] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(res, R.drawable.blade_5), getBitmap().getWidth(), getBitmap().getHeight(), false);
        blades[5] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(res, R.drawable.blade_6), getBitmap().getWidth(), getBitmap().getHeight(), false);
        blades[6] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(res, R.drawable.blade_7), getBitmap().getWidth(), getBitmap().getHeight(), false);
        blades[7] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(res, R.drawable.blade_8), getBitmap().getWidth(), getBitmap().getHeight(), false);
    }

    @Override
    public synchronized void drawElement(Canvas canvas, Paint paint)
    {
        setBitmap(blades[index++]);
        if(index == blades.length)
            index = 0;
        super.drawElement(canvas, paint);
    }

    @Override
    public Rect getCollisionShape()
    {
        int offset = 23 * (getBitmap().getWidth()/256);
        return new Rect(getX() + offset, getY() + offset, getX() + getBitmap().getWidth() - offset, getY() + getBitmap().getHeight() - offset);
    }
}
