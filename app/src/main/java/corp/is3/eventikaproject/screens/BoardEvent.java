package corp.is3.eventikaproject.screens;

import android.content.Context;
import android.widget.LinearLayout;

/**
 * Created by Дмитрий on 21.04.2016.
 */
public class BoardEvent extends LinearLayout {

    public BoardEvent(Context context) {
        super(context);
        setOrientation(VERTICAL);
        setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
    }
}
