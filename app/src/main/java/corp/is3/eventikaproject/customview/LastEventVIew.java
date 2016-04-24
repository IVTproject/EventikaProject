package corp.is3.eventikaproject.customview;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import corp.is3.eventikaproject.R;
import corp.is3.eventikaproject.structures.EventInfo;

/* Элементы в боковом меню в разделе "Последние"*/
public class LastEventVIew extends RelativeLayout {

    private View shadow;
    private EventInfo eventInfo;
    private float powerShadow = 0.6f;
    private float textSize = getResources().getDimension(R.dimen.last_event_text_size);
    private int paddingMedium = (int) getResources().getDimension(R.dimen.padding_medium);
    private int paddingSmall = (int) getResources().getDimension(R.dimen.padding_small);

    public LastEventVIew(Context context, EventInfo eventInfo) {
        super(context);
        this.eventInfo = eventInfo == null ? new EventInfo() : eventInfo;
        init(context);
    }

    public EventInfo getEventInfo() {
        return eventInfo;
    }

    public void setShading(float shading) {
        shadow.setAlpha(shading);
    }

    public void setShadingDefault() {
        shadow.setAlpha(powerShadow);
    }

    /* Создание и заполнение информацийей элемента*/
    private void init(Context context) {
        setPadding(0, 0, 0, paddingSmall);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, 128);
        setLayoutParams(params);
        ImageView image = new ImageView(context);
        image.setImageDrawable(eventInfo.image == null ? getResources().getDrawable(R.drawable.not_image) : eventInfo.image);
        image.setScaleType(ImageView.ScaleType.CENTER);
        image.setLayoutParams(params);

        shadow = new View(context);
        shadow.setBackgroundColor(Color.BLACK);
        shadow.setAlpha(powerShadow);
        shadow.setLayoutParams(params);

        TextView nameView = new TextView(context);
        nameView.setPadding(paddingMedium, paddingSmall, 0, 0);
        nameView.setTextColor(Color.WHITE);
        nameView.setText(eventInfo.name == null ? "Без назавания" : eventInfo.name);
        nameView.setTextSize(textSize);
        nameView.setLayoutParams(params);

        StringBuilder periud = new StringBuilder();
        periud.append(eventInfo.beginDate == null ? "1 января 1970 года" : eventInfo.beginDate);
        periud.append(" - ");
        periud.append(eventInfo.endDate == null ? "1 января 1970 года" : eventInfo.endDate);

        TextView dateView = new TextView(context);
        dateView.setPadding(paddingMedium * 2, (int) (paddingSmall * 2 + textSize), 0, 0);
        dateView.setTextColor(Color.WHITE);
        dateView.setText(periud);
        dateView.setLayoutParams(params);

        addView(image);
        addView(shadow);
        addView(nameView);
        addView(dateView);
    }
}
