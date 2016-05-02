package corp.is3.eventikaproject.datamanager;

import android.content.Context;

import java.util.ArrayList;

import corp.is3.eventikaproject.datamanager.stores.DataBaseStore;
import corp.is3.eventikaproject.datamanager.stores.FilesImageStore;
import corp.is3.eventikaproject.structures.UserInfo;

public class DataManager implements BasicData {

    private ArrayList<BasicData> dataObjects;

    private UserData userData;
    private DrawableData drawableData;
    private LoadableData loadableData;

    public DataManager(Context context) {
        dataObjects = new ArrayList<>();
        userData = new UserData(new DataBaseStore<UserInfo>(context, UserInfo.class));
        drawableData = new DrawableData(new FilesImageStore(context), new DataBaseStore<String[]>(context, String[].class));
        loadableData = new LoadableData();
        dataObjects.add(userData);
        dataObjects.add(loadableData);
        dataObjects.add(drawableData);
    }

    public UserData getUserData() {
        return userData;
    }

    public LoadableData getLoadableData() {
        return loadableData;
    }

    public DrawableData getDrawableData() {
        return drawableData;
    }

    @Override
    public void load() {
        for (BasicData bd : dataObjects)
            bd.load();
    }

    @Override
    public void save() {
        for (BasicData bd : dataObjects)
            bd.save();
    }
}
