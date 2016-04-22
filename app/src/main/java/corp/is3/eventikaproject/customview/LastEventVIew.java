package corp.is3.eventikaproject.customview;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import corp.is3.eventikaproject.R;
import corp.is3.eventikaproject.listeners.OnTouchClickListener;
import corp.is3.eventikaproject.structures.EventInfo;

/**
 * Created by Дмитрий on 20.04.2016.
 */
public class LastEventVIew extends RelativeLayout {

    private View shadow;
    private Runnable action;
    private EventInfo eventInfo;
    private float powerShadow = 0.4f;
    private float textSize = getResources().getDimension(R.dimen.last_event_text_size);
    private int paddingMedium = (int) getResources().getDimension(R.dimen.padding_medium);
    private int paddingSmall = (int) getResources().getDimension(R.dimen.padding_small);

    public LastEventVIew(Context context, EventInfo eventInfo) {
        super(context);
        this.eventInfo = eventInfo;
        init(context);
    }

    public void init(Context context) {
        setOnTouchListener(createListener());
        setPadding(0, 0, 0, paddingSmall);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, 128);
        setLayoutParams(params);
        View image = new View(context);
        image.setBackground(eventInfo.image);
        image.setLayoutParams(params);

        shadow = new View(context);
        shadow.setBackgroundColor(Color.BLACK);
        shadow.setAlpha(powerShadow);
        shadow.setLayoutParams(params);

        TextView nameView = new TextView(context);
        nameView.setPadding(paddingMedium, paddingSmall, 0, 0);
        nameView.setTextColor(Color.WHITE);
        nameView.setText(eventInfo.name);
        nameView.setTextSize(textSize);
        nameView.setLayoutParams(params);

        TextView dateView = new TextView(context);
        dateView.setPadding(paddingMedium * 2, (int) (paddingSmall * 2 + textSize), 0, 0);
        dateView.setTextColor(Color.WHITE);
        dateView.setText(eventInfo.beginDate + " - " + eventInfo.endDate);
        dateView.setLayoutParams(params);

        addView(image);
        addView(shadow);
        addView(nameView);
        addView(dateView);
    }

    public void setAction(Runnable action) {
        this.action = action;
        setOnTouchListener(createListener());
    }

    private OnTouchClickListener createListener() {
        return new OnTouchClickListener(action) {
            @Override
            public void select() {
                super.select();
                shadow.setAlpha(shadow.getAlpha() - 0.2f);
            }

            @Override
            public void deSelect() {
                super.deSelect();
                shadow.setAlpha(powerShadow);
            }
        };
    }
}
