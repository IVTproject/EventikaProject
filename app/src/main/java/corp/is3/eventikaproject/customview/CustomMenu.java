package corp.is3.eventikaproject.customview;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.github.siyamed.shapeimageview.CircularImageView;

import corp.is3.eventikaproject.R;
import corp.is3.eventikaproject.contentmanager.ContentManager;
import corp.is3.eventikaproject.structures.EventInfo;

/**
 * Created by Дмитрий on 20.04.2016.
 */
public class CustomMenu extends LinearLayout {

    private final int AVATAR = R.id.avatar_menu;
    private final int NAME_PROFILE = R.id.name_profile;
    private final int HEADER = R.layout.nav_header_main;
    private final int HEIGHT_HEADER =
            (int) getResources().getDimension(R.dimen.nav_header_height);

    private final int COUNT_LAST_EVENT = 3;
    private final int[] ICONS = new int[]{R.drawable.ic_event_board_24dp,
            R.drawable.ic_favorites_24dp, R.drawable.ic_group_add_24dp,
            R.drawable.ic_notifications_24dp};

    private final int[] TITLES = new int[]{R.string.event_board, R.string.favorites,
            R.string.group_add, R.string.notifications};

    private final Context CONTEXT;

    private CircularImageView avatar;
    private TextView nameProfile;
    private DrawerLayout drawer;
    private LinearLayout lastEvents;
    private ContentManager contentManager;
    private int paddingSmall = (int) getResources().getDimension(R.dimen.padding_small);
    private int paddingMedium = (int) getResources().getDimension(R.dimen.padding_medium);

    public CustomMenu(Context context, DrawerLayout drawer, ContentManager contentManager) {
        super(context);
        this.contentManager = contentManager;
        this.CONTEXT = context;
        this.drawer = drawer;
        init();
    }

    public void visitedEvent(EventInfo eventInfo) {
        LastEventVIew eventVIew = new LastEventVIew(CONTEXT, eventInfo);
        if (lastEvents.getChildCount() < COUNT_LAST_EVENT) {
            lastEvents.addView(eventVIew);
        } else {
            lastEvents.removeViewAt(COUNT_LAST_EVENT - 1);
            lastEvents.addView(eventVIew, 0);
        }
        eventVIew.setAction(actionClickLastEvent(eventInfo.id));
    }

    private void init() {
        LinearLayout linearLayout = new LinearLayout(CONTEXT);
        ScrollView scroll = new ScrollView(CONTEXT);
        lastEvents = new LinearLayout(CONTEXT);
        setParams();
        linearLayout.addView(initHeader());
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
        setDefaultValue();
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
        setAvatar(getResources().getDrawable(R.drawable.avatar));
        setProfileName(new String[]{"Исхаков", "Ильнур"});
    }

    private void setParams() {
        setOrientation(VERTICAL);
        LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT);
        setLayoutParams(params);
        lastEvents.setOrientation(VERTICAL);
        lastEvents.setLayoutParams(params);
    }

    private LinearLayout initHeader() {

        LinearLayout header = (LinearLayout) LinearLayout.inflate(CONTEXT, HEADER, null);
        LayoutParams paramsH = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
                HEIGHT_HEADER);
        header.setLayoutParams(paramsH);
        header.setPadding(0, 0, 0, paddingSmall);

        avatar = (CircularImageView) header.findViewById(R.id.avatar_menu);
        nameProfile = (TextView) header.findViewById(R.id.name_profile);

        LinearLayout linearLayout = (LinearLayout) header.findViewById(R.id.profile);
        linearLayout.setPadding(paddingSmall, paddingSmall, paddingSmall, paddingSmall);

        return header;
    }

    private Runnable actionClickLastEvent(final int id) {
        return new Runnable() {
            @Override
            public void run() {
                drawer.closeDrawer(GravityCompat.START);
            }
        };
    }

    private Runnable actionClickItemMenu(final int number) {
        return new Runnable() {
            @Override
            public void run() {
                switch (number) {
                    case 0:
                        contentManager.showEventBoard();
                        break;
                }
                drawer.closeDrawer(GravityCompat.START);
            }
        };
    }

    private boolean setAvatar(Drawable image) {
        if(avatar != null) {
            avatar.setImageDrawable(image);
            return true;
        }
        return false;
    }

    private boolean setProfileName(String[] name) {
        if(name != null) {
            String result = "";
            for(String part : name)
                result += part + " ";
            if(nameProfile != null) {
                nameProfile.setText(result);
                return true;
            }
        }
        return false;
    }
}
