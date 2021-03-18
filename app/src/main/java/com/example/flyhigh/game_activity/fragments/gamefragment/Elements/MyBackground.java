package com.example.flyhigh.game_activity.fragments.gamefragment.Elements;

import android.content.res.Resources;
import android.graphics.BitmapFactory;

import com.example.flyhigh.R;
import com.example.flyhigh.game_activity.fragments.gamefragment.Elements.Element;
import com.example.flyhigh.main_activity.MainActivity;

public class MyBackground extends Element
{

    public MyBackground(Resources res)
    {
        super(0, 0, BitmapFactory.decodeResource(res, R.drawable.fly_high_bg_2_1), MainActivity.screenWidth, MainActivity.screenHeight);
    }

}
