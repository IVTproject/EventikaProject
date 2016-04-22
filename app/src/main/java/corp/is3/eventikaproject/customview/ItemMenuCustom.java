package corp.is3.eventikaproject.customview;

import android.content.Context;
import android.graphics.Color;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import corp.is3.eventikaproject.R;
import corp.is3.eventikaproject.listeners.OnTouchClickListener;

/**
 * Created by Дмитрий on 20.04.2016.
 */
public class ItemMenuCustom extends LinearLayout {

    public final int ID;

    private int padding_medium;
    private int padding_high;

    private Runnable action;
    private Context context;
    private ImageView icon;
    private TextView title;

    public ItemMenuCustom(Context context, int id) {
        super(context);
        this.context = context;
        this.ID = id;
        init();
    }

    public void setAction(Runnable action) {
        this.action = action;
        this.setOnTouchListener(createListener());
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
        if (icon != null && title != null) {
            removeAllViews();
            addView(icon);
            addView(title);
        }
    }

    private void init() {
        this.setOnTouchListener(createListener());
        LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT);
        setLayoutParams(params);
        padding_medium = (int) getResources().getDimension(R.dimen.padding_medium);
        padding_high = (int) getResources().getDimension(R.dimen.padding_high);
        setPadding(padding_medium, padding_medium, padding_medium, padding_medium);
    }

    private OnTouchClickListener createListener() {
        return new OnTouchClickListener(action) {
            @Override
            public void select() {
                super.select();
                setBackgroundColor(Color.LTGRAY);
            }

            @Override
            public void deSelect() {
                super.deSelect();
                setBackground(null);
            }
        };
    }
}
