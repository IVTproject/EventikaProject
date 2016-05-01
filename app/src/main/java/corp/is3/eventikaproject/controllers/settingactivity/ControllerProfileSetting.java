package corp.is3.eventikaproject.controllers.settingactivity;

import android.content.res.Resources;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import corp.is3.eventikaproject.R;
import corp.is3.eventikaproject.controllers.BasicController;
import corp.is3.eventikaproject.dialogframes.DialogSelector;
import corp.is3.eventikaproject.dialogframes.SelectItemsListener;
import corp.is3.eventikaproject.listeners.OnTouchClickListenerBase;
import corp.is3.eventikaproject.services.Services;
import corp.is3.eventikaproject.structures.UserInfo;

public class ControllerProfileSetting extends BasicController implements SettingController {

    private final int FIELD_INTEREST = R.id.select_field_interest;

    private final int CITYS = R.id.select_citys;

    private ArrayList<View> selectors;

    private Resources r;

    private String[] listInterest;
    private String[] listCity;
    private String[] selectedInterest;
    private String[] selectedCity;


    public ControllerProfileSetting(AppCompatActivity compatActivity, ViewGroup content) {
        super(compatActivity, content);
        selectors = new ArrayList<>();
        r = compatActivity.getResources();
        loadSetting();
        initFields();
        initListener();
    }

    @Override
    public void refresh() {
        loadSetting();
    }

    @Override
    public void loadSetting() {
        UserInfo info = Services.dataManager.getUserData().getInformationFromUser();
        listInterest = Services.dataManager.getLoadableData().getListInterests();
        listCity = Services.dataManager.getLoadableData().getListCity();
        selectedInterest = info.getInterest();
        selectedCity = info.getCitys();
    }

    @Override
    public void saveSetting() {
        UserInfo info = Services.dataManager.getUserData().getInformationFromUser();
        info.setInterest(selectedInterest);
        info.setCitys(selectedCity);
    }

    private void initFields() {
        selectors.add(getContent().findViewById(FIELD_INTEREST));
        selectors.add(getContent().findViewById(CITYS));
    }

    private void initListener() {
        for (View v : selectors)
            v.setOnTouchListener(new OnTouchClickListenerBase() {
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
                    if (!longClick)
                        actionSelector(v.getId());
                }
            });
    }

    private void actionSelector(int id) {
        switch (id) {
            case FIELD_INTEREST:
                showDialogInterest();
                break;
            case CITYS:
                showDialogCitys();
                break;
        }
    }

    private void showDialogSelector(String title, String[] items, String[] selectedItems, SelectItemsListener listener) {
        DialogSelector dialog = new DialogSelector();
        dialog.setListItems(items);
        dialog.setSelectedItems(selectedItems);
        dialog.setTitle(title);
        dialog.setSelectItemsListener(listener);
        dialog.show(getAppCompatActivity().getSupportFragmentManager(), "");
    }

    private void showDialogInterest() {
        showDialogSelector(r.getString(R.string.field_interest),
                listInterest,
                selectedInterest,
                actionSelectedInterest());
    }

    private void showDialogCitys() {
        showDialogSelector(r.getString(R.string.citys),
                listCity,
                selectedCity,
                actionSelectedCitys());
    }

    private SelectItemsListener actionSelectedInterest() {
        return new SelectItemsListener() {
            @Override
            public void ok(boolean[] condition) {
                selectedInterest = createSelectedArray(listInterest, condition);
            }

            @Override
            public void cancel(boolean[] condition) {

            }
        };
    }

    private SelectItemsListener actionSelectedCitys() {
        return new SelectItemsListener() {
            @Override
            public void ok(boolean[] condition) {
                selectedCity = createSelectedArray(listCity, condition);
            }

            @Override
            public void cancel(boolean[] condition) {

            }
        };
    }

    private String[] createSelectedArray(String[] fullArray, boolean[] mask) {
        int countSelect = 0;
        for (boolean res : mask)
            if (res)
                countSelect++;
        String[] selectedArray = new String[countSelect];
        for (int i = 0, j = 0; i < mask.length && j < countSelect; i++) {
            if (mask[i]) {
                selectedArray[j] = fullArray[i];
                j++;
            }
        }
        return selectedArray;
    }
}
