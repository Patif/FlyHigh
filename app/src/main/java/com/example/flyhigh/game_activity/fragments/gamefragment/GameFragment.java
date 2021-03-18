package com.example.flyhigh.game_activity.fragments.gamefragment;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.flyhigh.R;
import com.example.flyhigh.main_activity.MainActivity;

import java.util.List;
import java.util.Objects;


public class GameFragment extends Fragment implements SensorEventListener
{

    private Handler handler = null;
    private GameView gameView;
    private TextView scoreTextView, check;
    private SensorManager sm;
    private List<Sensor> accelerometers;
    private int speed = 0;
    private int score;

    private boolean wasPaused;

    public interface OnPauseGame
    {
        void onPressPause();
        void onLosing();
    }

    public OnPauseGame aListener;

    public GameFragment()
    {

    }

    @Override
    public void onAttach(@NonNull Context context)
    {
        super.onAttach(context);
        try
        {
            aListener = (OnPauseGame) context;
        }
        catch (ClassCastException e)
        {
            throw new RuntimeException();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_game, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstance)
    {

        super.onActivityCreated(savedInstance);
        score = 0;
        gameView = Objects.requireNonNull(getView()).findViewById(R.id.game_fragment_game_view);
        gameView.init(this);
        scoreTextView = getView().findViewById(R.id.game_fragment_score_text_view);
        sm = (SensorManager) Objects.requireNonNull(getActivity()).getSystemService(Context.SENSOR_SERVICE);
        assert sm != null;
        accelerometers = sm.getSensorList(Sensor.TYPE_ACCELEROMETER);
        check = getView().findViewById(R.id.speed);
        ImageButton button = getView().findViewById(R.id.game_fragment_pause_button);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                aListener.onPressPause();
            }
        });
    }

    private Runnable scoreCounter = new Runnable()
    {

        @Override
        public void run()
        {
            update();
            handler.postDelayed(this, 1000);
            score++;
        }

        public void update()
        {
            scoreTextView.setText(Objects.requireNonNull(getActivity()).getResources().getString(R.string.score_text, score));
        }

    };

    private Runnable leftAction = new Runnable() {
        @Override
        public void run() {
            try {
                gameView.getBalloon().semaphore.acquire();
                gameView.getBalloon().slideLeft(speed);
                handler.postDelayed(this, 10);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
            finally {
                gameView.getBalloon().semaphore.release();
            }
        }
    };

    private Runnable rightAction = new Runnable() {
        @Override
        public void run() {
            try {
                gameView.getBalloon().semaphore.acquire();
                gameView.getBalloon().slideRight(speed);
                handler.postDelayed(this, 10);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
            finally {
                gameView.getBalloon().semaphore.release();
            }
        }
    };

    @Override
    public void onSensorChanged(SensorEvent event)
    {
        int newSpeed = Math.abs(event.values[0]) < 0.7f ? 0 : Math.round(event.values[0]) + 1;
        if (newSpeed != speed) {
            if (Integer.signum(newSpeed) != Integer.signum(speed)) {
                if (speed <= 0 && newSpeed >= 0) {
                    handler.removeCallbacks(rightAction);
                    if (newSpeed != 0)
                        handler.post(leftAction);
                } else {
                    handler.removeCallbacks(leftAction);
                    if (newSpeed != 0)
                        handler.post(rightAction);
                }
            }
            speed = newSpeed;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy)
    {

    }

    public void pauseGame()
    {
        if(!wasPaused)
        {
            sm.unregisterListener(this);
            handler.removeCallbacksAndMessages(null);
            handler = null;
            gameView.pause();
            wasPaused = true;
        }
    }

    public void resumeGame()
    {
        handler = new Handler();
        if(!accelerometers.isEmpty())
            sm.registerListener(this, accelerometers.get(0), SensorManager.SENSOR_DELAY_UI);
        gameView.resume();
        handler.post(scoreCounter);
        wasPaused = false;
    }

    public int getScore()
    {
        return score;
    }

    public Handler getHandler()
    {
        return handler;
    }
}
