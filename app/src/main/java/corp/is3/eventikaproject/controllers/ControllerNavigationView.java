package corp.is3.eventikaproject.controllers;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.siyamed.shapeimageview.CircularImageView;

import corp.is3.eventikaproject.R;
import corp.is3.eventikaproject.customview.LastEventVIew;
import corp.is3.eventikaproject.listeners.OnTouchClickListenerBase;
import corp.is3.eventikaproject.services.Services;
import corp.is3.eventikaproject.structures.EventInfo;

/* Контроллер для работы с боковым меню*/
public class ControllerNavigationView extends BaseController {

    /* id аватара(ImageView) находящегося в header*/
    private final int AVATAR = R.id.avatar_menu;
    /* id имени пользователя(TextView) находящегося в header*/
    private final int NAME_PROFILE = R.id.name_profile;
    /* id блока в который буду складироваться последние посещеные мероприятия*/
    private final int CONTAINER_LASTS_EVENT = R.id.container_last_event;

    /* Список индентификоторов пунктов меню*/
    private final int[] LIST_ITEM_MENU = new int[]{R.id.im_event_board,
            R.id.im_favorites, R.id.im_group_add, R.id.im_notifications};

    /* Колличество отображающихся последни мероприятий */
    private int count_last_event = 3;

    private CircularImageView avatar;
    private TextView nameProfile;
    private DrawerLayout drawer;
    private LinearLayout lastEvents;

    public ControllerNavigationView(Context context, ViewGroup content) {
        super(context, content);
        avatar = (CircularImageView) content.findViewById(AVATAR);
        avatar.setImageResource(R.drawable.menu_background);
        nameProfile = (TextView) content.findViewById(NAME_PROFILE);
        lastEvents = (LinearLayout) content.findViewById(CONTAINER_LASTS_EVENT);
        initListeners();
        setCountLastEvent(3);
    }

    public void setDrawer(DrawerLayout drawer) {
        this.drawer = drawer;
    }

    public void setCountLastEvent(int count) {
        count_last_event = count;
    }

    /* Добавляет пункт в блоке "Последние посещеные"*/
    public void visitedEvent(EventInfo eventInfo) {
        LastEventVIew eventVIew = new LastEventVIew(getContext(), eventInfo);
        if (lastEvents.getChildCount() < count_last_event) {
            lastEvents.addView(eventVIew);
        } else {
            lastEvents.removeViewAt(count_last_event - 1);
            lastEvents.addView(eventVIew, 0);
        }
        eventVIew.setOnTouchListener(new OnTouchClickListenerBase() {
            @Override
            public void select(View v) {
                ((LastEventVIew) v).setShading(0.4f);
            }

            @Override
            public void deSelect(View v) {
                ((LastEventVIew) v).setShadingDefault();
            }

            @Override
            public void click(View v, boolean longClick) {
                ((LastEventVIew) v).getEventInfo();
            }
        });
    }

    public boolean setAvatar(Drawable image) {
        if (avatar != null) {
            avatar.setImageDrawable(image);
            return true;
        }
        return false;
    }

    public boolean setProfileName(String[] name) {
        if (name != null) {
            String result = "";
            for (String part : name)
                result += part + " ";
            if (nameProfile != null) {
                nameProfile.setText(result);
                return true;
            }
        }
        return false;
    }

    private void initListeners() {
        for (int id : LIST_ITEM_MENU)
            getContent().findViewById(id).setOnTouchListener(new OnTouchClickListenerBase() {
                @Override
                public void select(View v) {
                    v.setBackgroundColor(Color.LTGRAY);
                }

                @Override
                public void deSelect(View v) {
                    v.setBackground(null);
                }

                @Override
                public void click(View v, boolean longClick) {
                    actionClickItemMenu(v);
                }
            });


        avatar.setOnTouchListener(new OnTouchClickListenerBase() {
            @Override
            public void select(View v) {

            }

            @Override
            public void deSelect(View v) {

            }

            @Override
            public void click(View v, boolean longClick) {
            }
        });
    }

    private void actionClickItemMenu(View v) {
        if (drawer != null)
            drawer.closeDrawer(GravityCompat.START);

        switch (v.getId()) {
            case R.id.im_event_board:
                Services.contentManager.showEventBoard();
                break;
            case R.id.im_favorites:
                Services.contentManager.showFavoriteEvent();
                break;
            case R.id.im_group_add:
                break;
            case R.id.im_notifications:
                break;
        }

    }

    /* Удалить, создано для теста*/
    @Deprecated
    private void setDefaultValue() {
        //setAvatar(res.getDrawable(R.drawable.avatar));
        //setProfileName(new String[]{"Исхаков", "Ильнур"});
    }

    @Override
    public void refresh() {

    }
}
