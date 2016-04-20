package corp.is3.eventikaproject.customview;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.Layout;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import corp.is3.eventikaproject.R;

/**
 * Created by Дмитрий on 20.04.2016.
 */
public class LastEventVIew extends RelativeLayout {
    public LastEventVIew(Context context) {
        super(context);
        init(context);
    }

    public LastEventVIew(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LastEventVIew(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void init(Context context) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, 128);
        setLayoutParams(params);
        View image = new View(context);
        image.setBackgroundResource(R.drawable.menu_background);
        image.setLayoutParams(params);

        View v = new View(context);
        v.setBackgroundColor(Color.BLACK);
        v.setAlpha(0.4f);
        v.setLayoutParams(params);

        TextView t = new TextView(context);
        t.setTextColor(Color.WHITE);
        t.setText("Hello world");
        t.setLayoutParams(params);

        addView(image);
        addView(v);
        addView(t);
    }
}
