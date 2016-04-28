package corp.is3.eventikaproject.customview;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
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
        //setPadding(0, 0, 0, paddingSmall);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        LinearLayout.LayoutParams paramsV = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        params.topMargin = paddingSmall / 2;
        params.bottomMargin = paddingSmall / 2;
        setLayoutParams(params);
        ImageView image = new ImageView(context);
        image.setImageDrawable(eventInfo.getImage() == null ? getResources().getDrawable(R.drawable.default_img) : eventInfo.getImage());
        image.setScaleType(ImageView.ScaleType.CENTER_CROP);
        image.setLayoutParams(params);


        shadow = new View(context);
        shadow.setBackgroundColor(Color.BLACK);
        shadow.setAlpha(powerShadow);
        shadow.setLayoutParams(paramsV);

        TextView nameView = new TextView(context);
        nameView.setPadding(paddingMedium, paddingSmall, 0, 0);
        nameView.setTextColor(Color.WHITE);
        nameView.setText(eventInfo.getName() == null ? "Без назавания" : eventInfo.getName());
        nameView.setTextSize(textSize);
        nameView.setLayoutParams(params);

        StringBuilder periud = new StringBuilder();
        periud.append(eventInfo.getBeginDate() == null ? "1 января 1970 года" : eventInfo.getBeginDate());
        periud.append(" - ");
        periud.append(eventInfo.getEndDate() == null ? "1 января 1970 года" : eventInfo.getEndDate());

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
