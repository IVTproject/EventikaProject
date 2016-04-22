package corp.is3.eventikaproject.listeners;

import android.graphics.Color;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Дмитрий on 21.04.2016.
 */
public class OnTouchClickListener implements View.OnTouchListener {

    private Runnable runnable;
    private Handler handler;
    private Timer timer;
    private Float xDown = null;
    private Float yDown = null;
    private boolean isClicked = false;

    public OnTouchClickListener(Runnable runnable) {
        xDown = 0f;
        yDown = 0f;
        this.runnable = runnable;
        this.handler = new Handler();
        this.timer = new Timer();
    }

    public void select() {

    }

    public void deSelect() {
        timer.cancel();
        timer = new Timer();
        isClicked = false;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                xDown = event.getX();
                yDown = event.getY();
                action_up();
                break;
            case MotionEvent.ACTION_UP:
                action();
                break;
            case MotionEvent.ACTION_MOVE:
                if (isDistantion(event.getX(), event.getY(), xDown, yDown)) {
                    deSelect();
                }
                break;
            default:
                deSelect();
                break;

        }
        return true;
    }

    private void action_up() {
        isClicked = true;
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        select();
                    }
                });
            }
        }, 100);
    }

    private void action_down() {
        isClicked = true;
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        deSelect();
                    }
                });
            }
        }, 500);
    }

    private void action() {
        if (runnable != null && isClicked) {
            timer.cancel();
            timer = new Timer();
            select();
            action_down();
            runnable.run();
        }
    }

    private boolean isDistantion(float x1, float y1, float x2, float y2) {
        return (float) Math.abs(Math.pow(Math.abs(x1 - x2), 2) - Math.pow(Math.abs(y1 - y2), 2)) > 50;
    }
}
