package com.example.flyhigh.game_activity.fragments.gamefragment;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceView;
import android.widget.ImageView;

import com.example.flyhigh.game_activity.fragments.gamefragment.Elements.Player;
import com.example.flyhigh.game_activity.fragments.gamefragment.Elements.MyBackground;
import com.example.flyhigh.game_activity.fragments.gamefragment.Elements.obstacles.Blade;
import com.example.flyhigh.game_activity.fragments.gamefragment.Elements.obstacles.Obstacle;
import com.example.flyhigh.main_activity.MainActivity;

import java.util.Random;

public class GameView extends SurfaceView implements Runnable
{
    private boolean isPlaying = false;
    private MyBackground bg1, bg2;
    private Paint paint;
    private Thread thread;
    private Obstacle obstacle = null;
    private Player balloon;
    private Obstacle[] obstacles;
    private Random r;
    private final int floorHeight = MainActivity.screenHeight/30;
    private int offset = MainActivity.screenHeight/200;

    private GameFragment fragment;

    public GameView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public void init(GameFragment fragment)
    {
        this.fragment = fragment;
        this.r = new Random();
        this.bg1 = new MyBackground(getResources());
        this.bg2 = new MyBackground(getResources());
        this.paint = new Paint();
        this.obstacles = new Obstacle[1];
        int player_width = MainActivity.screenWidth/8;
        int player_height = (int) (MainActivity.screenHeight/7.5f);

        this.balloon = new Player((MainActivity.screenWidth - player_width)/2, MainActivity.screenHeight - MainActivity.navigationBarHeight - floorHeight - player_height, player_height, player_width, getResources());
        obstacles[0] = new Blade(getResources());
        bg2.setY(-bg2.getBitmap().getHeight());
    }

    @Override
    public void run() {
        if (obstacle == null)
            spawnObstacle();
        while (isPlaying)
        {
            try {
                balloon.semaphore.acquire();
                draw();
                if(Rect.intersects(obstacle.getCollisionShape(), balloon.getCollisionShape()))
                {
                    isPlaying = false;
                    fragment.getHandler().postAtFrontOfQueue(new Runnable() {
                        @Override
                        public void run() {
                            fragment.aListener.onLosing();
                        }
                    });
                    return;
                }
                update();
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
            finally {
                balloon.semaphore.release();
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void update()
    {
        bg1.setY(bg1.getY() + offset);
        bg2.setY(bg2.getY() + offset);
        obstacle.setY(obstacle.getY() + offset);
        if(obstacle.getY() - MainActivity.screenHeight > 0)
            spawnObstacle();
        if (bg1.getY() - bg1.getBitmap().getHeight() > 0)
        {
            bg1.setY(-bg1.getBitmap().getHeight() + bg2.getY());
            speedUp();
        }
        else if (bg2.getY() - bg2.getBitmap().getHeight() > 0)
        {
            bg2.setY(-bg2.getBitmap().getHeight() + bg1.getY());
            speedUp();
        }
    }

    public void draw()
    {
        if(getHolder().getSurface().isValid())
        {
            Canvas canvas = getHolder().lockCanvas();
            bg1.drawElement(canvas, paint);
            bg2.drawElement(canvas, paint);
            obstacle.drawElement(canvas, paint);
            balloon.drawElement(canvas, paint);
            getHolder().unlockCanvasAndPost(canvas);
        }
    }

    public void resume()
    {
        isPlaying = true;
        thread = new Thread(this);
        thread.start();
    }

    public void pause()
    {
        isPlaying = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void spawnObstacle()
    {
        obstacle = obstacles[r.nextInt(obstacles.length)];
        obstacle.setX(r.nextInt(MainActivity.screenWidth- obstacle.getBitmap().getWidth()));
        obstacle.setY(-obstacle.getBitmap().getHeight());
    }

    public void speedUp()
    {
        int maximumSpeed = 60;
        if(offset < maximumSpeed)
            offset++;
    }

    public Player getBalloon()
    {
        return balloon;
    }

}
