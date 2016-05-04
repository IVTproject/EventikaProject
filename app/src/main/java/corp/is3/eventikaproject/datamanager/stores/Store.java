package corp.is3.eventikaproject.datamanager.stores;


/* Интерфейс реализуют все хранилища*/
public interface Store<T> {

    public boolean setData(String key, T data);

    public T getData(String key);

    public boolean remove(String key);
}
