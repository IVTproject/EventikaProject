package corp.is3.eventikaproject.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import corp.is3.eventikaproject.R;
import corp.is3.eventikaproject.listeners.OnTouchClickListenerBase;

/* Пункт меню*/
public class ItemMenuCustom extends LinearLayout {

    private int padding_medium;
    private int padding_high;

    private Runnable action;
    private Context context;
    private ImageView icon;
    private TextView title;

    public ItemMenuCustom(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public ItemMenuCustom(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ItemMenuCustom);

        setTitle(typedArray.getString(R.styleable.ItemMenuCustom_itmText));
        setIcon(typedArray.getDrawable(R.styleable.ItemMenuCustom_itmIcon));
    }

    public ItemMenuCustom(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs);
    }


    /* Устанавливает дейсвтие совершаемое при нажатии на пункт*/
    public void setAction(Runnable action) {
        this.action = action;
    }

    public void setIcon(Drawable resource) {
        icon = new ImageView(context);
        icon.setImageDrawable(resource);
        addViews();
    }

    public void setTitle(int text) {
        title = new TextView(context);
        title.setText(text);
        title.setPadding(padding_high, 0, 0, 0);
        addViews();
    }

    public void setTitle(String text) {
        title = new TextView(context);
        title.setText(text);
        title.setPadding(padding_high, 0, 0, 0);
        addViews();
    }

    private void addViews() {
        if (icon != null && title != null) {
            removeAllViews();
            addView(icon);
            addView(title);
        }
    }

    private void init() {
        LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT);
        setLayoutParams(params);
        padding_medium = (int) getResources().getDimension(R.dimen.padding_medium);
        padding_high = (int) getResources().getDimension(R.dimen.padding_high);
        setPadding(padding_medium, padding_medium, padding_medium, padding_medium);
    }
}
