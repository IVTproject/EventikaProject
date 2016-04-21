package corp.is3.eventikaproject.listeners;

import android.graphics.Color;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Дмитрий on 21.04.2016.
 */
public class OnTouchClickListener implements View.OnTouchListener {

    private Runnable runnable;
    private Float xDown = null;
    private Float yDown = null;
    private boolean isClicked = false;

    public OnTouchClickListener(Runnable runnable) {
        xDown = 0f;
        yDown = 0f;
        this.runnable = runnable;
    }

    public void select() {
        isClicked = true;
    }

    public void deSelect() {
        isClicked = false;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                xDown = event.getX();
                yDown = event.getY();
                select();
                break;
            case MotionEvent.ACTION_UP:
                action();
                deSelect();
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

    private void action() {
        if(runnable != null && isClicked)
            runnable.run();
    }

    private boolean isDistantion(float x1, float y1, float x2, float y2) {
        return (float) Math.abs(Math.pow(Math.abs(x1 - x2), 2) - Math.pow(Math.abs(y1 - y2), 2)) > 30;
    }
}
