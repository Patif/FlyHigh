package com.example.flyhigh.game_activity.fragments.gamefragment.Elements;

import android.content.res.Resources;
import android.graphics.BitmapFactory;

import com.example.flyhigh.R;
import com.example.flyhigh.main_activity.MainActivity;

import java.util.concurrent.Semaphore;

public class Player extends Element
{

    private final float BASIC_BALLOON_STEP = 5;
    public Semaphore semaphore = new Semaphore(1);

    public Player(int x, int y, int length, int width, Resources res)
    {
        super(x, y, BitmapFactory.decodeResource(res, R.drawable.model_1), width, length);
    }

    public void slideLeft(int speed)
    {
        if (getX() > 0)
            setX((int) Math.max(0, getX() - BASIC_BALLOON_STEP * speed));
    }

    public void slideRight(int speed)
    {
        if (getX() < MainActivity.screenWidth - getBitmap().getWidth())
            setX((int) Math.min(MainActivity.screenWidth - getBitmap().getWidth(), getX() - BASIC_BALLOON_STEP * speed));
    }
}
