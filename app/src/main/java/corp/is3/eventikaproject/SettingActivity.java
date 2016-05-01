package corp.is3.eventikaproject;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import corp.is3.eventikaproject.controllers.settingactivity.ControllerProfileSetting;
import corp.is3.eventikaproject.services.Services;

public class SettingActivity extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;

    private ViewPager mViewPager;
    private static AppCompatActivity compatActivity;
    private static ControllerProfileSetting controllerProfileSetting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        compatActivity = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setteing);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_chevron_left_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
    }

    public void authorization(View v) {
        Services.contentManager.openAuthorization();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_setting, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_setting:
                finish();
                if (controllerProfileSetting != null) {
                    controllerProfileSetting.saveSetting();
                    controllerProfileSetting = null;
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    protected static class PlaceholderFragment extends Fragment {

        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView;
            switch (getArguments().getInt(ARG_SECTION_NUMBER)) {
                case 1:
                    if (Services.dataManager.getUserData().getInformationFromUser().getId() != null)
                        rootView = (controllerProfileSetting =
                                new ControllerProfileSetting(compatActivity,
                                        (ViewGroup) inflater.inflate(R.layout.seting_profile_tab_layout,
                                                container, false))).getContent();
                    else
                        rootView = inflater.inflate(R.layout.offer_to_log, container, false);
                    break;
                case 2:
                    rootView = inflater.inflate(R.layout.seting_app_tab_layout, container, false);
                    break;
                default:
                    rootView = null;
            }
            return rootView;
        }
    }

    protected class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Профиль";
                case 1:
                    return "Приложение";
            }
            return null;
        }
    }
}
