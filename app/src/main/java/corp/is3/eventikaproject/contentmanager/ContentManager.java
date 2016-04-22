package corp.is3.eventikaproject.contentmanager;

import android.content.Context;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import corp.is3.eventikaproject.customview.CustomMenu;

/**
 * Created by Дмитрий on 21.04.2016.
 */
public class ContentManager {

    private LinearLayout container;
    private LinearLayout contentLayout;
    private NavigationView contentMenu;
    private DrawerLayout drawerLayout;
    private Context context;

    private CustomMenu menu;

    public ContentManager(Context context, LinearLayout contentLayout, NavigationView contentMenu, DrawerLayout drawerLayout) {
        this.context = context;
        this.container = contentLayout;
        this.drawerLayout = drawerLayout;
        this.contentMenu = contentMenu;
        this.menu = new CustomMenu(context, drawerLayout, this);
        init();
    }

    public void showEventBoard() {

    }

    private void init() {
        contentMenu.addView(menu);
        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);

        contentLayout = new LinearLayout(context);
        contentLayout.setOrientation(LinearLayout.VERTICAL);
        contentLayout.setLayoutParams(params);

        ScrollView scrollView = new ScrollView(context);
        scrollView.setLayoutParams(params);
        scrollView.addView(contentLayout);

        container.addView(scrollView);
    }
}
