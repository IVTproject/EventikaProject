package corp.is3.eventikaproject.contentmanager;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import corp.is3.eventikaproject.LoginActivity;
import corp.is3.eventikaproject.MainActivity;
import corp.is3.eventikaproject.R;
import corp.is3.eventikaproject.SettingActivity;
import corp.is3.eventikaproject.controllers.ControllerEventBoard;
import corp.is3.eventikaproject.reuests.CallbackFunction;
import corp.is3.eventikaproject.reuests.QueryDesigner;
import corp.is3.eventikaproject.reuests.QueryManager;
import corp.is3.eventikaproject.structures.EventInfo;

/* Класс отвечающий за смену наполнения экрана(переключения экранов) и открытия новых окон*/
public class ContentManager {

    /* Активность с которой будет вестись работа*/
    private final AppCompatActivity COMPAT_ACTIVITY;

    /* Контайнер для контента*/
    private LinearLayout container;

    private ControllerEventBoard controllerEventBoard;


    public ContentManager(AppCompatActivity compatActivity, LinearLayout container) {
        this.COMPAT_ACTIVITY = compatActivity;
        this.container = container;
    }

    /* Показать страницу мероприятия */
    public void showEventPage(EventInfo eventInfo) {
    }

    /* Показать доску мероприятий*/
    public void showEventBoard() {
        if (controllerEventBoard == null) {
            controllerEventBoard = new ControllerEventBoard(COMPAT_ACTIVITY,
                    (ViewGroup) LinearLayout.inflate(COMPAT_ACTIVITY, R.layout.event_board, null));
            controllerEventBoard.init();
        }
        showScreen(controllerEventBoard.getContent());
    }

    /* Показать доску избранных мероприятий*/
    public void showFavoriteEvent() {
        ControllerEventBoard favoriteBoard = new ControllerEventBoard(COMPAT_ACTIVITY,
                (ViewGroup) LinearLayout.inflate(COMPAT_ACTIVITY, R.layout.event_board, null));
        favoriteBoard.setTypeBoard(ControllerEventBoard.TYPE_BOARD.FAVORITE);
        favoriteBoard.init();
        showScreen(favoriteBoard.getContent());
    }

    /* Открывает активити с настройками(SettingActivity)*/
    public void openSettingProfile() {
        Intent intent = new Intent(COMPAT_ACTIVITY, SettingActivity.class);
        COMPAT_ACTIVITY.startActivity(intent);
    }

    /* Открывает активити с регистрацией(авторизацией), метод вызывается из SettingActivity*/
    public void openAuthorization() {
        Intent intent = new Intent(COMPAT_ACTIVITY, LoginActivity.class);
        COMPAT_ACTIVITY.startActivity(intent);
    }

    private void showScreen(View newScreen) {
        if (newScreen != null) {
            container.removeAllViews();
            container.addView(newScreen);
        }
    }
}
