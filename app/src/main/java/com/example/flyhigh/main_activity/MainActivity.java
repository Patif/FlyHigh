package com.example.flyhigh.main_activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.flyhigh.game_activity.GameActivity;
import com.example.flyhigh.R;

public class MainActivity extends AppCompatActivity {

    public static int highscore;
    public static int screenWidth, screenHeight;
    public static SharedPreferences data;
    public final static String dataKey = "highscore";
    public static int navigationBarHeight;
    private TextView highscoreTextView;
    private Button exitButton, playButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.GameActivityTheme);
        super.onCreate(savedInstanceState);
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        setContentView(R.layout.activity_main);
        screenWidth = getResources().getDisplayMetrics().widthPixels;
        screenHeight = getResources().getDisplayMetrics().heightPixels;
        int resourceId = getResources().getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0)
            navigationBarHeight = getResources().getDimensionPixelSize(resourceId);
        highscoreTextView = findViewById(R.id.main_menu_highscore);
        data = getSharedPreferences(dataKey, MODE_PRIVATE);
        final Intent startGameIntent = new Intent(getApplicationContext(), GameActivity.class);
        playButton = findViewById(R.id.main_menu_play_button);
        playButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                playButton.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.cloud_grey));
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                startActivity(startGameIntent);
            }
        });
        exitButton = findViewById(R.id.main_menu_exit_button);
        exitButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                exitButton.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.cloud_grey));
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finish();
            }
        });
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        highscore = data.getInt(dataKey, 0);
        highscoreTextView.setText(this.getResources().getString(R.string.highscore_text, highscore));
        playButton.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.cloud));
        exitButton.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.cloud));
    }
}