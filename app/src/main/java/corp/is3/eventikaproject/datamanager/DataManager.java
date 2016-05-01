package corp.is3.eventikaproject.datamanager;

import android.content.Context;

import java.util.ArrayList;

import corp.is3.eventikaproject.datamanager.stores.SharedPreferencesStore;
import corp.is3.eventikaproject.structures.EventInfo;
import corp.is3.eventikaproject.structures.UserInfo;

public class DataManager implements BasicData {

    private ArrayList<BasicData> dataObjects;

    private UserData userData;
    private LoadableData loadableData;

    public DataManager(Context context) {
        dataObjects = new ArrayList<>();
        userData = new UserData(new SharedPreferencesStore<UserInfo>(context, UserInfo.class));
        loadableData = new LoadableData();
        dataObjects.add(userData);
        dataObjects.add(loadableData);
    }

    public UserData getUserData() {
        return userData;
    }

    public LoadableData getLoadableData() {
        return loadableData;
    }

    @Override
    public void load() {
        for(BasicData bd : dataObjects)
          bd.load();
    }

    @Override
    public void save() {
        for(BasicData bd : dataObjects)
          bd.save();
    }
}
