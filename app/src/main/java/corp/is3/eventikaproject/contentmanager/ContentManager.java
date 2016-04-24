package corp.is3.eventikaproject.contentmanager;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;

import corp.is3.eventikaproject.R;
import corp.is3.eventikaproject.controllers.ControllerEventBoard;
import corp.is3.eventikaproject.structures.EventInfo;

/* Класс отвечающий за смену наполнения экрана(переключения экранов) и открытия новых окон*/
public class ContentManager {

    /* Активность с которой будет вестись работа*/
    private final Activity ACTIVITY;

    /* Контайнер для контента*/
    private LinearLayout container;

    private ControllerEventBoard controllerEventBoard;


    public ContentManager(Activity activity, LinearLayout container) {
        this.ACTIVITY = activity;
        this.container = container;
    }

    /* Показать страницу мероприятия */
    public void showEventPage(EventInfo eventInfo) {
    }

    /* Показать доску мероприятий*/
    public void showEventBoard() {
        if (controllerEventBoard == null) {
            controllerEventBoard = new ControllerEventBoard(ACTIVITY,
                    (ViewGroup) LinearLayout.inflate(ACTIVITY, R.layout.event_board, null));
        }
        ArrayList<EventInfo> event = new ArrayList<>();
        event.add(new EventInfo());
        event.add(new EventInfo());
        event.add(new EventInfo());
        event.add(new EventInfo());
        event.add(new EventInfo());
        event.add(new EventInfo());
        controllerEventBoard.addEvents(event);

        showScreen(controllerEventBoard.getContent());
    }

    public void showFavoriteEvent() {
        container.removeAllViews();
    }

    private void showScreen(View newScreen) {
        if (newScreen != null) {
            container.removeAllViews();
            container.addView(newScreen);
        }
    }
}
