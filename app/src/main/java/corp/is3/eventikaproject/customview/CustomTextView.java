package corp.is3.eventikaproject.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import corp.is3.eventikaproject.R;

/**
 * Created by Дмитрий on 25.04.2016.
 */
public class CustomTextView extends TextView {

    private Context context;

    public CustomTextView(Context context) {
        super(context);
        this.context = context;
    }

    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomTextView);

        changeFont(typedArray.getString(R.styleable.CustomTextView_font));
    }

    public CustomTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs);
    }

    public void changeFont(String font) {
        Typeface face = Typeface.createFromAsset(context.getAssets(), font);
        setTypeface(face);
    }
}
