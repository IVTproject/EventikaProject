package corp.is3.eventikaproject;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.security.acl.Group;

import corp.is3.eventikaproject.listeners.ItemSelectListener;
import corp.is3.eventikaproject.ui.ProfileMenu;

public class MainActivity extends AppCompatActivity {


    private ProfileMenu profileMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(getDrawerListener());
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new ItemSelectListener(this));


        LinearLayout layout = (LinearLayout) findViewById(R.id.content_menu);
        ItemMenuCustom itemMenuCustom = new ItemMenuCustom(this);
        itemMenuCustom.setIcon(R.drawable.ic_event_board_24dp);
        itemMenuCustom.setTitle(R.string.event_board);
        layout.addView(itemMenuCustom);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private DrawerLayout.DrawerListener getDrawerListener() {
        return new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                if(profileMenu == null) {
                    profileMenu = new ProfileMenu(MainActivity.this);
                    profileMenu.setProfileName(new String[]{"Исхаков", "Ильнур"});
                    profileMenu.setAvatar(R.drawable.avatar);
                }
            }

            @Override
            public void onDrawerOpened(View drawerView) {
            }

            @Override
            public void onDrawerClosed(View drawerView) {
            }

            @Override
            public void onDrawerStateChanged(int newState) {
            }
        };
    }
}
