package corp.is3.eventikaproject.controllers;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.siyamed.shapeimageview.CircularImageView;

import corp.is3.eventikaproject.R;
import corp.is3.eventikaproject.customview.LastEventVIew;
import corp.is3.eventikaproject.datamanager.UserData;
import corp.is3.eventikaproject.listeners.OnTouchClickListenerBase;
import corp.is3.eventikaproject.services.Services;
import corp.is3.eventikaproject.structures.EventInfo;

/* Контроллер для работы с боковым меню*/
public class ControllerNavigationView extends BasicController {

    /* id аватара(ImageView) находящегося в header*/
    private final int AVATAR = R.id.avatar_menu;
    /* id имени пользователя(TextView) находящегося в header*/
    private final int NAME_PROFILE = R.id.name_profile;
    /* Кнопка настройки профиля*/
    private final int SETTING_BUTTON = R.id.icon_setting;
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

    private ImageView settingButton;

    public ControllerNavigationView(AppCompatActivity compatActivity, ViewGroup content) {
        super(compatActivity, content);
        avatar = (CircularImageView) content.findViewById(AVATAR);
        nameProfile = (TextView) content.findViewById(NAME_PROFILE);
        lastEvents = (LinearLayout) content.findViewById(CONTAINER_LASTS_EVENT);
        settingButton = (ImageView) content.findViewById(SETTING_BUTTON);
        initListeners();
        setCountLastEvent(3);
    }

    /* Добавляет DrawerLayout что бы происходило закрытие меню после выбора пункта*/
    public void setDrawer(DrawerLayout drawer) {
        this.drawer = drawer;
    }

    /* Устанавливает колличество показываемых последних мероприятий*/
    public void setCountLastEvent(int count) {
        count_last_event = count;
    }

    /* Добавляет пункт в блоке "Последние посещеные"*/
    public void visitedEvent(EventInfo eventInfo) {
        LastEventVIew eventVIew = new LastEventVIew(getAppCompatActivity(), eventInfo);
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

    @Override
    public void refresh() {
        UserData ud = Services.dataManager.getUserData();
        ud.loadAvatarImageView(avatar);
        String[] profileName = new String[]{ud.getInformationFromUser().getLastName(),
                ud.getInformationFromUser().getFistName()};
        setProfileName(profileName);
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

    /* Создает все нужные слушатели. Позже нужно изменить*/
    @Deprecated
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

        settingButton.setOnTouchListener(new OnTouchClickListenerBase() {
            @Override
            public void select(View v) {
               // settingButton.setScaleX(1.2f);
               // settingButton.setScaleY(1.2f);
            }

            @Override
            public void deSelect(View v) {
               // settingButton.setScaleX(1.0f);
               // settingButton.setScaleY(1.0f);
            }

            @Override
            public void click(View v, boolean longClick) {
                Services.contentManager.openSettingProfile();
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
}
