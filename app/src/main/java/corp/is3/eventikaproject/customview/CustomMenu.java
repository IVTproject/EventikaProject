package corp.is3.eventikaproject.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.shapes.Shape;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

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
    private DrawerLayout drawer;
    private LinearLayout lastEvents;
    private int paddingSmall = (int) getResources().getDimension(R.dimen.padding_small);
    private int paddingMedium = (int) getResources().getDimension(R.dimen.padding_medium);

    public CustomMenu(Context context, DrawerLayout drawer) {
        super(context);
        this.CONTEXT = context;
        this.drawer = drawer;
        init();
        visitedEvent();
        visitedEvent();
    }

    public void visitedEvent() {
        Drawable image = getResources().getDrawable(R.drawable.menu_background);
        LastEventVIew eventVIew = new LastEventVIew(CONTEXT, image, "Мероприятие 1", "24 июня 2016 - 26 июня 2016");
        if (lastEvents.getChildCount() < 3) {
            lastEvents.addView(eventVIew);
        } else {
            lastEvents.removeViewAt(2);
            lastEvents.addView(eventVIew, 0);
        }
    }

    private void init() {
        LinearLayout linearLayout = new LinearLayout(CONTEXT);
        ScrollView scroll = new ScrollView(CONTEXT);
        setParams(linearLayout);
        setDefaultValue();
        linearLayout.setOrientation(VERTICAL);
        for (int i = 0; i < Math.min(ICONS.length, TITLES.length); i++) {
            ItemMenuCustom itemMenuCustom = new ItemMenuCustom(CONTEXT, i);
            itemMenuCustom.setIcon(ICONS[i]);
            itemMenuCustom.setTitle(TITLES[i]);
            itemMenuCustom.setAction(actionClickItemMenu(i));
            linearLayout.addView(itemMenuCustom);
        }
        linearLayout.addView(nextBlock(getResources().getString(R.string.lasts)));
        linearLayout.addView(lastEvents);
        scroll.addView(linearLayout);
        addView(scroll);
    }

    private Runnable actionClickItemMenu(final int id) {
        return new Runnable() {
            @Override
            public void run() {
                drawer.closeDrawer(GravityCompat.START);
            }
        };
    }

    private View nextBlock(String text) {
        LinearLayout nextBlock = new LinearLayout(CONTEXT);
        nextBlock.setOrientation(VERTICAL);
        nextBlock.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        ImageView line = new ImageView(CONTEXT);
        line.setPadding(0, paddingSmall, 0, paddingSmall);
        line.setBackgroundResource(R.drawable.line);

        TextView label = new TextView(CONTEXT);
        label.setPadding(paddingMedium, paddingSmall, 0, paddingMedium);
        label.setText(text);
        label.setTextColor(Color.GRAY);
        nextBlock.addView(line);
        nextBlock.addView(label);
        return nextBlock;
    }

    private void setDefaultValue() {
        profileMenu.setAvatar(R.drawable.avatar);
        profileMenu.setProfileName(new String[]{"Исхаков", "Ильнур"});
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
        header.setPadding(0, 0, 0, paddingSmall);
        linear.addView(header);
        profileMenu = new ProfileMenu(header);
        lastEvents = new LinearLayout(CONTEXT);
        lastEvents.setOrientation(VERTICAL);
        lastEvents.setLayoutParams(params);
    }
}
