package corp.is3.eventikaproject.controllers.settingactivity;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
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
import corp.is3.eventikaproject.datamanager.LoadableData;
import corp.is3.eventikaproject.dialogframes.DialogSelector;
import corp.is3.eventikaproject.dialogframes.SelectItemsListener;
import corp.is3.eventikaproject.listeners.OnTouchClickListenerBase;
import corp.is3.eventikaproject.services.Services;
import corp.is3.eventikaproject.structures.UserInfo;

public class ControllerProfileSetting extends BasicController implements SettingController {

    /* id полей и объектов находящихся в xml врестке странице*/
    private final int FIELD_INTEREST = R.id.select_field_interest;
    private final int CITYS = R.id.select_citys;
    private final int AVATAR = R.id.avatar_setting;
    private final int LAST_NAME = R.id.last_name;
    private final int FIRST_NAME = R.id.first_name;
    private final int VK_PROFILE = R.id.vk_profile;
    private final int FACE_BOOK_PROFILE = R.id.face_book_profile;
    private final int EMAIL = R.id.email_setting_layout;
    private final int NUMBER_PHONE = R.id.number_phone;

    /* Список объектов по нажатию на которые
    нужно открыть диалоговое окно с выбром неких данных*/
    private ArrayList<View> selectors;

    /* Поля находящиеся в xml верстке, находятся по id-кам расположеным выше*/
    private CircularImageView avatarImageView;
    private EditText lastName;
    private EditText firstName;
    private EditText vkProfile;
    private EditText faceBookProfile;
    private EditText email;
    private EditText numberPhone;

    private Resources r;

    /*Списки дынных которые нужно вывести в диалоговых окнах выбора*/
    private String[] listInterest;
    private String[] listCity;
    /*Пункты необходимые пометить как выбраные*/
    private String[] selectedInterest;
    private String[] selectedCity;

    /*Переменая говорящая о том была ли изменена аватарка*/
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

    public void setAvatar(Bitmap avatar) {
        if (avatar == null)
            return;
        float width = avatar.getWidth();
        float height = avatar.getHeight();
        if (width < height)
            avatar = Bitmap.createScaledBitmap(avatar, 200, (int) (height / width * 200f), false);
        else
            avatar = Bitmap.createScaledBitmap(avatar, (int) (width / height * 200f), 200, false);
        this.avatarImageView.setImageBitmap(avatar);

        changeAvatar = true;
    }

    @Override
    public void refresh() {
        loadSetting();
    }

    @Override
    public void loadSetting() {
        UserInfo info = Services.dataManager.getUserData().getInformationFromUser();
        LoadableData ld = Services.dataManager.getLoadableData();
        listInterest = ld.getListInterests();
        listCity = ld.getListCity();
        selectedInterest = info.getInterest();
        selectedCity = info.getCitys();
        Services.dataManager.getUserData().loadAvatarImageView(avatarImageView);
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
        String writeField = r.getString(R.string.write_to_field);
        if (lastName.getText().toString().length() == 0) {
            lastName.setError(writeField);
            isExit = false;
        } else {
            info.setLastName(lastName.getText().toString());
        }
        if (firstName.getText().toString().length() == 0) {
            firstName.setError(writeField);
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

    /*В методе происходит инициализация всех необходимых слушателей. Поже будет переписан*/
    @Deprecated
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

    /* Вызывается при нажатии на кнопку открытия диалогового окна.
     id - на какой именно элемент нажали*/
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

    /* Показывает диалоговое окно. title - заголовок, items - список значений,
    selectedItems - элементы которые должны быть помеченые как выбранные, listener - слушатель выбора элементов*/
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

    /* Создание массива выбранных элементов на основе массива флагов - mask, пришедшим в слушателе при закрытии диалогового окна*/
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
