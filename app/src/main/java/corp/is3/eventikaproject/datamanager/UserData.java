package corp.is3.eventikaproject.datamanager;

import android.content.Context;

import java.util.Arrays;

import corp.is3.eventikaproject.datamanager.stores.Store;
import corp.is3.eventikaproject.structures.UserInfo;

public class UserData implements BasicData {

    private final String KEY_FROM_SAVE = "userInfo";

    private UserInfo userInfo;
    private Store<UserInfo> store;

    protected UserData(Store<UserInfo> store) {
        this.store = store;
    }

    public UserInfo getInformationFromUser() {
        return userInfo;
    }

    @Override
    public void load() {
        userInfo = store.getData(KEY_FROM_SAVE);
        if(userInfo == null)
            userInfo = new UserInfo();
    }

    @Override
    public void save() {
        store.setData(KEY_FROM_SAVE, userInfo);
    }
}
