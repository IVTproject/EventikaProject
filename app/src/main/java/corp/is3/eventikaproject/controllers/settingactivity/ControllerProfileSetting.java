package corp.is3.eventikaproject.controllers.settingactivity;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.github.siyamed.shapeimageview.CircularImageView;

import java.util.ArrayList;

import corp.is3.eventikaproject.R;
import corp.is3.eventikaproject.SettingActivity;
import corp.is3.eventikaproject.controllers.BasicController;
import corp.is3.eventikaproject.dialogframes.DialogSelector;
import corp.is3.eventikaproject.dialogframes.SelectItemsListener;
import corp.is3.eventikaproject.listeners.OnTouchClickListenerBase;
import corp.is3.eventikaproject.services.Services;
import corp.is3.eventikaproject.structures.UserInfo;

public class ControllerProfileSetting extends BasicController implements SettingController {

    private final int FIELD_INTEREST = R.id.select_field_interest;
    private final int AVATAR = R.id.avatar_setting;
    private final int LAST_NAME = R.id.last_name;
    private final int FIRST_NAME = R.id.first_name;
    private final int VK_PROFILE = R.id.vk_profile;
    private final int FACE_BOOK_PROFILE = R.id.face_book_profile;
    private final int EMAIL = R.id.email_setting_layout;
    private final int NUMBER_PHONE = R.id.number_phone;

    private final int CITYS = R.id.select_citys;

    private ArrayList<View> selectors;

    private CircularImageView avatarImageView;
    private EditText lastName;
    private EditText firstName;
    private EditText vkProfile;
    private EditText faceBookProfile;
    private EditText email;
    private EditText numberPhone;

    private Resources r;

    private String[] listInterest;
    private String[] listCity;
    private String[] selectedInterest;
    private String[] selectedCity;

    private boolean changeAvatar = false;

    public ControllerProfileSetting(AppCompatActivity compatActivity, ViewGroup content) {
        super(compatActivity, content);
        selectors = new ArrayList<>();
        r = compatActivity.getResources();
        initFields();
        loadSetting();
        initListener();
        avatarOnClick();
    }

    @Deprecated
    public void setAvatar(Bitmap avatar) {
        float width = avatar.getWidth();
        float height = avatar.getHeight();
        if (width < height)
            avatar = Bitmap.createScaledBitmap(avatar, 200, (int) (height / width * 200f), false);
        else
            avatar = Bitmap.createScaledBitmap(avatar, (int) (width / height * 200f), 200, false);
        Drawable avaDrawable = new BitmapDrawable(avatar);
        this.avatarImageView.setImageDrawable(avaDrawable);
        changeAvatar = true;
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
        Drawable avatar = info.getAvatar();
        if (avatar != null)
            this.avatarImageView.setImageDrawable(avatar);
        lastName.setText(info.getLastName());
        firstName.setText(info.getFistName());
        vkProfile.setText(info.getSocialNetwork(UserInfo.VK));
        faceBookProfile.setText(info.getSocialNetwork(UserInfo.FACE_BOOK));
        email.setText(info.getEmail());
        numberPhone.setText(info.getNumberPhone());
    }

    @Override
    public boolean saveSetting() {
        boolean isExit = true;
        UserInfo info = Services.dataManager.getUserData().getInformationFromUser();
        info.setInterest(selectedInterest);
        info.setCitys(selectedCity);
        if (changeAvatar)
            info.setAvatar(avatarImageView.getDrawable());
        Resources r = getAppCompatActivity().getResources();
        if (lastName.getText().toString().length() == 0) {
            lastName.setError(r.getString(R.string.write_to_field));
            isExit = false;
        } else {
            info.setLastName(lastName.getText().toString());
        }
        if (firstName.getText().toString().length() == 0) {
            firstName.setError(r.getString(R.string.write_to_field));
            isExit = false;
        } else {
            info.setFistName(firstName.getText().toString());
        }
        info.setSocialNetwork(UserInfo.VK, vkProfile.getText().toString());
        info.setSocialNetwork(UserInfo.FACE_BOOK, faceBookProfile.getText().toString());
        info.setEmail(email.getText().toString());
        info.setNumberPhone(numberPhone.getText().toString());
        return isExit;
    }

    private void avatarOnClick() {
        avatarImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                getAppCompatActivity().startActivityForResult(photoPickerIntent, SettingActivity.SELECT_PHOTO);
            }
        });
    }

    private void initFields() {
        selectors.add(getContent().findViewById(FIELD_INTEREST));
        selectors.add(getContent().findViewById(CITYS));
        lastName = (EditText) getContent().findViewById(LAST_NAME);
        firstName = (EditText) getContent().findViewById(FIRST_NAME);
        vkProfile = (EditText) getContent().findViewById(VK_PROFILE);
        faceBookProfile = (EditText) getContent().findViewById(FACE_BOOK_PROFILE);
        email = (EditText) getContent().findViewById(EMAIL);
        numberPhone = (EditText) getContent().findViewById(NUMBER_PHONE);
        avatarImageView = (CircularImageView) getContent().findViewById(AVATAR);
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
