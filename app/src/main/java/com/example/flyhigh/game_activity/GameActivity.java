package com.example.flyhigh.game_activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.WindowManager;

import com.example.flyhigh.R;
import com.example.flyhigh.game_activity.fragments.gamefragment.GameView;
import com.example.flyhigh.game_activity.fragments.gamefragment.GameFragment;
import com.example.flyhigh.game_activity.fragments.PauseFragment;
import com.example.flyhigh.main_activity.MainActivity;


public class GameActivity extends AppCompatActivity  implements PauseFragment.OnButtonSelected, GameFragment.OnPauseGame
{

    private PauseFragment pf;
    private GameFragment gf;
    private FragmentTransaction ft;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        ft = getSupportFragmentManager().beginTransaction();
        pf = new PauseFragment();
        gf = new GameFragment();
        ft.add(R.id.game_activity_layout, pf);
        ft.detach(pf);
        ft.add(R.id.game_activity_layout, gf);
        ft.commit();
        setContentView(R.layout.activity_game);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        if(pf.isVisible())
        {
            ft = getSupportFragmentManager().beginTransaction();
            ft.detach(pf);
            ft.commit();
        }
        gf.resumeGame();
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        gf.pauseGame();
    }

    @Override
    public void onSelect(int option)
    {
        switch(option)
        {
            case 0:
                ft = getSupportFragmentManager().beginTransaction();
                ft.detach(pf);
                ft.commit();
                gf.resumeGame();
                break;
            case 1:
                finish();
        }
    }

    @Override
    public void onPressPause()
    {
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        gf.pauseGame();
        ft = getSupportFragmentManager().beginTransaction();
        ft.attach(pf);
        ft.commit();
    }

    @Override
    public void onLosing()
    {
        gf.pauseGame();
        if(MainActivity.data.getInt(MainActivity.dataKey, 0) < gf.getScore())
        {
            SharedPreferences.Editor editor = MainActivity.data.edit();
            editor.putInt(MainActivity.dataKey, gf.getScore());
            editor.apply();
        }
        finish();
    }
}