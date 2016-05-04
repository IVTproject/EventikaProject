package corp.is3.eventikaproject.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import corp.is3.eventikaproject.R;

/* Пункт меню с иконкой справа. Позже надо объеденить с классом ItemMenuCustom*/
@Deprecated
public class ItemSettingArrow extends RelativeLayout {

    private TextView title;
    private ImageView arrow;

    public ItemSettingArrow(Context context) {
        this(context, null, 0);
    }

    public ItemSettingArrow(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ItemSettingArrow(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
        if(attrs != null) {

            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ItemSettingArrow);

            setTitle(typedArray.getString(R.styleable.ItemSettingArrow_nameItm));
            Drawable icon = typedArray.getDrawable(R.styleable.ItemSettingArrow_iconItm);
            if(icon != null)
                setIcon(icon);
        }
    }

    private void setIcon(Drawable icon) {
        arrow.setImageDrawable(icon);
    }

    private void setTitle(String title) {
        this.title.setText(title);
    }

    private void init(Context context) {
        RelativeLayout.LayoutParams layoutParams =
                new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        RelativeLayout.LayoutParams textViewParams =
                new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        RelativeLayout.LayoutParams imageViewParams =
                new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        int padding = (int) getResources().getDimension(R.dimen.padding_medium);
        setPadding(padding, padding, padding, padding);

        imageViewParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        arrow = new ImageView(context);
        arrow.setImageResource(R.drawable.ic_expand_more_black_24dp);
        arrow.setLayoutParams(imageViewParams);

        title = new TextView(context);
        title.setLayoutParams(textViewParams);

        setLayoutParams(layoutParams);
        addView(title);
        addView(arrow);

    }
}
