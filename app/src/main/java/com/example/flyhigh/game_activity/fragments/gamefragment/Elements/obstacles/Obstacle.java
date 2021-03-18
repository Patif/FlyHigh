package com.example.flyhigh.game_activity.fragments.gamefragment.Elements.obstacles;


import android.graphics.Bitmap;
import android.graphics.Rect;

import com.example.flyhigh.game_activity.fragments.gamefragment.Elements.Element;

public abstract class Obstacle extends Element
{

    public Obstacle(int width, int length, Bitmap bitmap)
    {
        super(0, -bitmap.getHeight(), bitmap, width, length);
    }

}
