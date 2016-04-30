package corp.is3.eventikaproject.datamanager;

import java.util.ArrayList;

public class DataManager implements BasicData {

    private ArrayList<BasicData> dataObjects;

    private UserData userData;
    private LoadableData loadableData;

    public DataManager() {
        dataObjects = new ArrayList<>();
        userData = new UserData();
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
