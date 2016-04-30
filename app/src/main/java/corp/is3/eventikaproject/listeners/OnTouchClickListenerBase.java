package corp.is3.eventikaproject.listeners;

import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;

import java.util.Timer;
import java.util.TimerTask;

/* Слушатель позволяющий оповещать пользователя о нажатии на объект,
 * переопределяя методы select и deSelect. Определение клика по объекту -
 * вызывается Runnable передающийся в конструктор*/
public abstract class OnTouchClickListenerBase implements View.OnTouchListener {

    private Handler handler;
    private Timer timer;
    private Float xDown = null;
    private Float yDown = null;
    private boolean isClicked = false;

    public OnTouchClickListenerBase() {
        xDown = 0f;
        yDown = 0f;
        this.handler = new Handler();
        this.timer = new Timer();
    }

    /* Переопределяемый метод, действие при нажатии (выделение)*/
    public abstract void select(View v);

    /* Переопределяемый метод, действие при ухода с объекта (возвращение прежнего состояния)*/
    public abstract void deSelect(View v);

    /* Переопределяемый метод, действие по клику*/
    public abstract void click(View v, boolean longClick);

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                xDown = event.getX();
                yDown = event.getY();
                action_down(v);
                break;
            case MotionEvent.ACTION_UP:
                action_up(v);
                break;
            case MotionEvent.ACTION_MOVE:
                if (isDistance(event.getX(), event.getY(), xDown, yDown)) {
                    action_move(v);
                }
                break;
            default:
                isClicked = false;
                deSelect(v);
                break;

        }
        return true;
    }

    private void action_down(final View v) {
        isClicked = true;
        if(timer != null)
            timer.cancel();
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if(isClicked)
                            select(v);
                        timer = null;
                    }
                });
            }
        }, 100);
    }

    private void action_move(View v) {
        isClicked  = false;
        if(timer != null)
            timer.cancel();
        deSelect(v);
    }

    private void action_up(final View v) {
        if(isClicked) {
            select(v);
            if(timer != null)
                timer.cancel();
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            deSelect(v);
                            timer = null;
                        }
                    });
                }
            }, 100);
            click(v, false);
        }
    }

    private boolean isDistance(float x1, float y1, float x2, float y2) {
        return (float) Math.abs(Math.pow(Math.abs(x1 - x2), 2) - Math.pow(Math.abs(y1 - y2), 2)) > 30;
    }
}
