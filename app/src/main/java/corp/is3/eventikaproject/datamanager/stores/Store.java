package corp.is3.eventikaproject.datamanager.stores;

/**
 * Created by Дмитрий on 01.05.2016.
 */
public interface Store<T> {

    public boolean setData(String key, T data);

    public T getData(String key);
}
