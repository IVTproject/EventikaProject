package corp.is3.eventikaproject.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import corp.is3.eventikaproject.R;
import corp.is3.eventikaproject.ui.ProfileMenu;

/**
 * Created by Дмитрий on 20.04.2016.
 */
public class CustomMenu extends LinearLayout {

    private final int AVATAR = R.id.avatar_menu;
    private final int NAME_PROFILE = R.id.name_profile;
    private final int HEADER = R.layout.nav_header_main;
    private final int HEIGHT_HEADER =
            (int) getResources().getDimension(R.dimen.nav_header_height);

    private final int[] ICONS = new int[]{R.drawable.ic_event_board_24dp,
            R.drawable.ic_favorites_24dp, R.drawable.ic_group_add_24dp,
            R.drawable.ic_notifications_24dp};

    private final int[] TITLES = new int[]{R.string.event_board, R.string.favorites,
            R.string.group_add, R.string.notifications};

    private final Context CONTEXT;

    private ProfileMenu profileMenu;

    public CustomMenu(Context context) {
        super(context);
        this.CONTEXT = context;
        init();
    }

    public CustomMenu(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.CONTEXT = context;
        init();
    }

    public CustomMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.CONTEXT = context;
        init();
    }

    private void init() {
        LinearLayout linearLayout = new LinearLayout(CONTEXT);
        ScrollView scroll = new ScrollView(CONTEXT);
        setParams(linearLayout);
        setDefaultValue();
        linearLayout.setOrientation(VERTICAL);
        for (int i = 0; i < Math.min(ICONS.length, TITLES.length); i++) {
            ItemMenuCustom itemMenuCustom = new ItemMenuCustom(CONTEXT);
            itemMenuCustom.setIcon(ICONS[i]);
            itemMenuCustom.setTitle(TITLES[i]);
            linearLayout.addView(itemMenuCustom);
        }
        linearLayout.addView(new LastEventVIew(CONTEXT));
        scroll.addView(linearLayout);
        addView(scroll);
    }

    private void setDefaultValue() {
        profileMenu.setAvatar(R.drawable.avatar);
        profileMenu.setProfileName(new String[] {"Исхаков", "Ильнур"});
    }

    private void setParams(LinearLayout linear) {
        setOrientation(VERTICAL);
        LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT);
        setLayoutParams(params);
        LinearLayout header = (LinearLayout) LinearLayout.inflate(CONTEXT, HEADER, null);
        LayoutParams paramsH = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
                HEIGHT_HEADER);
        header.setLayoutParams(paramsH);
        header.setPadding(0, 0, 0, 8);
        linear.addView(header);
        profileMenu = new ProfileMenu(header);
    }
}
