package corp.is3.eventikaproject;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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

import java.io.IOException;

import corp.is3.eventikaproject.controllers.settingactivity.ControllerProfileSetting;
import corp.is3.eventikaproject.services.Services;

/*Активити настроек*/
public class SettingActivity extends AppCompatActivity {

    public static final int SELECT_PHOTO = 1;

    private SectionsPagerAdapter mSectionsPagerAdapter;

    private ViewPager mViewPager;
    private static AppCompatActivity compatActivity;
    private static ControllerProfileSetting controllerProfileSetting;

    private boolean recreateActivity = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        compatActivity = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setteing);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (recreateActivity) {
            recreateActivity = false;
            recreate();
        }
    }

    /*Результат выбора аватара*/
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        switch (requestCode) {
            case SELECT_PHOTO:
                if (resultCode == RESULT_OK) {
                    Uri selectedImage = intent.getData();
                    try {
                        controllerProfileSetting.
                                setAvatar(MediaStore.Images.Media.getBitmap(getContentResolver(),
                                        selectedImage));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
        }
    }

    /*Октрывает активити(окно) с регситрацией*/
    public void authorization(View v) {
        recreateActivity = true;
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
                if (controllerProfileSetting != null &&
                        controllerProfileSetting.saveSetting()) {
                    controllerProfileSetting = null;
                    finish();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /*Инициализация экранов настроек и их контролеров*/
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

    /*Создание экранов настроек*/
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
