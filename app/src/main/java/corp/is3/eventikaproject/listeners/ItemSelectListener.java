package corp.is3.eventikaproject.listeners;

import android.app.Activity;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;

import corp.is3.eventikaproject.R;

/**
 * Created by Дмитрий on 20.04.2016.
 */
public class ItemSelectListener
        implements NavigationView.OnNavigationItemSelectedListener {

    private Activity activity;
    private DrawerLayout drawer;

    public ItemSelectListener(Activity activity) {
        this.activity = activity;
        drawer = (DrawerLayout) activity.findViewById(R.id.drawer_layout);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
