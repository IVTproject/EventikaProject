package corp.is3.eventikaproject.contentmanager;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import corp.is3.eventikaproject.R;
import corp.is3.eventikaproject.customview.CustomMenu;
import corp.is3.eventikaproject.factory.BlockFactory;
import corp.is3.eventikaproject.structures.EventInfo;

/**
 * Created by Дмитрий on 21.04.2016.
 */
public class ContentManager {

    private final Activity ACTIVITY;

    private LinearLayout container;
    private LinearLayout contentLayout;
    private NavigationView contentMenu;
    private DrawerLayout drawerLayout;
    private BlockFactory blockFactory;

    private CustomMenu menu;

    public ContentManager(Activity context, LinearLayout contentLayout, NavigationView contentMenu, DrawerLayout drawerLayout) {
        this.ACTIVITY = context;
        this.container = contentLayout;
        this.drawerLayout = drawerLayout;
        this.contentMenu = contentMenu;
        blockFactory = new BlockFactory(ACTIVITY, this);
        this.menu = new CustomMenu(context, drawerLayout, this);
        init();
    }

    public void showEventBoard() {
        EventInfo event = new EventInfo();
        event.id = 1;
        event.image = ACTIVITY.getResources().getDrawable(R.drawable.test);
        event.name = "Мероприятие 1";
        event.beginDate = "24 июня 2016 год";
        event.endDate = "26 июня 2016 год";
        event.address = "г.Москва, ул.Большая Лубянка, дом 1/3";
        contentLayout.addView(blockFactory.createEventCard(event));
    }

    public void openEvent(EventInfo eventInfo) {
        menu.visitedEvent(eventInfo);
    }

    public void setAvatar(Drawable avatar) {
        menu.setAvatar(avatar);
    }

    private void init() {
        contentMenu.addView(menu);
        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);

        contentLayout = new LinearLayout(ACTIVITY);
        contentLayout.setOrientation(LinearLayout.VERTICAL);
        contentLayout.setLayoutParams(params);

        ScrollView scrollView = new ScrollView(ACTIVITY);
        scrollView.setLayoutParams(params);
        scrollView.addView(contentLayout);

        container.addView(scrollView);
    }
}
