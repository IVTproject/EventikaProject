package corp.is3.eventikaproject.customview;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import corp.is3.eventikaproject.R;

/**
 * Created by Дмитрий on 20.04.2016.
 */
public class ItemMenuCustom extends LinearLayout implements View.OnTouchListener {

    private int padding_medium;
    private int padding_high;

    private Context context;
    private ImageView icon;
    private TextView title;

    private Float xDown = null;
    private Float yDown = null;

    public ItemMenuCustom(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public ItemMenuCustom(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public ItemMenuCustom(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    public void setIcon(int resource) {
        icon = new ImageView(context);
        icon.setImageResource(resource);
        addViews();
    }

    public void setTitle(int text) {
        title = new TextView(context);
        title.setText(text);
        title.setPadding(padding_medium, 0, 0, 0);
        addViews();
    }

    private void addViews() {
        if(icon != null && title != null) {
            removeAllViews();
            addView(icon);
            addView(title);
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                xDown = event.getX();
                yDown = event.getY();
                setBackgroundColor(Color.LTGRAY);
                break;
            case MotionEvent.ACTION_UP:
                xDown = null;
                yDown = null;
                setBackground(null);
                break;
            case MotionEvent.ACTION_MOVE:
                if (isDistantion(event.getX(), event.getY(), xDown, yDown)) {
                    setBackground(null);
                }
                break;
            default:
                setBackground(null);
                break;

        }
        return true;
    }

    private boolean isDistantion(float x1, float y1, float x2, float y2) {
        return (float) Math.abs(Math.pow(Math.abs(x1 - x2), 2) - Math.pow(Math.abs(y1 - y2), 2)) > 30;
    }

    private void init() {
        this.setOnTouchListener(this);
        LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT);
        setLayoutParams(params);
        padding_medium = (int) getResources().getDimension(R.dimen.padding_medium);
        padding_high = (int) getResources().getDimension(R.dimen.padding_high);
        setPadding(padding_medium, padding_medium, padding_medium, padding_medium);
    }
}
