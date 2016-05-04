package corp.is3.eventikaproject;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import corp.is3.eventikaproject.contentmanager.ContentManager;
import corp.is3.eventikaproject.controllers.ControllerNavigationView;
import corp.is3.eventikaproject.datamanager.DataManager;
import corp.is3.eventikaproject.services.Services;

public class MainActivity extends AppCompatActivity {

    private ControllerNavigationView controllerNavigationView;
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
                .discCache(new UnlimitedDiskCache(getCacheDir()))
                .diskCacheSize(1024 * 1024 * 10).build();
        ImageLoader.getInstance().init(config);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        drawer.addDrawerListener(toggle);

        controllerNavigationView = new ControllerNavigationView(this, navigationView);
        controllerNavigationView.setDrawer(drawer);
        controllerNavigationView.setAvatar(getResources().getDrawable(R.drawable.default_avatar));

        LinearLayout mainContent = (LinearLayout) findViewById(R.id.main_content);

        ContentManager contentManager = new ContentManager(this, mainContent);


        Services.controllerNavigationView = controllerNavigationView;
        Services.contentManager = contentManager;
        Services.dataManager = new DataManager(this);
        Services.dataManager = new DataManager(this);
        long l = System.currentTimeMillis();
        Services.dataManager.load();
        l = System.currentTimeMillis() - l;
        Services.controllerNavigationView.setProfileName(new String[]{l + ""});
    }

    @Override
    protected void onPause() {
        super.onPause();
        Services.dataManager.save();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Services.controllerNavigationView.refresh();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
