package corp.is3.eventikaproject.datamanager;

import java.util.Arrays;

/*Хранилище загружаемой информации, т.е. того что получаем с сервера*/
public class LoadableData implements BasicData {

    private String[] listInterests;
    private String[] listCity;

    public LoadableData() {
        listInterests = new String[]{"1", "2", "3", "4", "5", "6"};
        listCity = new String[]{"1", "2", "3", "4", "5", "6"};
    }

    public String[] getListInterests() {
        return Arrays.copyOf(listInterests, listInterests.length);
    }

    public String[] getListCity() {
        return Arrays.copyOf(listCity, listCity.length);
    }

    @Override
    public void load() {

    }

    @Override
    public void save() {

    }
}
