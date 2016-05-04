package corp.is3.eventikaproject.datamanager;

import android.graphics.drawable.Drawable;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import corp.is3.eventikaproject.datamanager.stores.Store;

/* Хранилище графической информации. Картинок проще говоря :)*/
public class DrawableData implements BasicData {

    private final static String KEY = "DRAWABLE_DATA";

    private Store<Drawable> storeImage;
    private Store<String[]> storeNames;
    private Map<String, Drawable> container;
    private Set<String> cacheDisk;
    private Queue<String> queue;
    private Integer count;

    public DrawableData(Store<Drawable> storeImage, Store<String[]> storeNames) {
        container = new HashMap<>();
        queue = new LinkedList<>();
        cacheDisk = new HashSet<>();
        this.storeImage = storeImage;
        this.storeNames = storeNames;
    }

    public Drawable addDrawable(String key, Drawable drawable, boolean cacheDisk) {
        if (container.containsKey(key))
            remove(key);
        if(count != null && !cacheDisk) {
            queue.add(key);
            removeOld();
        }
        if (cacheDisk) {
            this.cacheDisk.add(key);
        }
        return container.put(key, drawable);
    }

    public Drawable getDrawable(String key) {
        return container.get(key);
    }

    public Drawable remove(String key) {
        cacheDisk.remove(key);
        queue.remove(key);
        return container.remove(key);
    }

    public int size() {
        return container.size();
    }

    public boolean contains(String key) {
        return container.containsKey(key);
    }

    public void setLimit(Integer count) {
        this.count = count;
        removeOld();
    }

    private void removeOld() {
        if (count != null && count > 0) {
            while (count < queue.size())
                container.remove(queue.poll());
        }
    }

    @Override
    public void load() {
        String[] namesImages = storeNames.getData(KEY);
        if (namesImages != null) {
            for (int i = 0; i < namesImages.length; i++) {
                Drawable image = storeImage.getData(namesImages[i]);
                if (image == null)
                    continue;
                cacheDisk.add(namesImages[i]);
                container.put(namesImages[i], image);
            }
        }
    }

    @Override
    public void save() {
        String[] namesImages = new String[cacheDisk.size()];
        for (int i = 0; i < namesImages.length; i++) {
            namesImages[i] = cacheDisk.iterator().next();
            storeImage.setData(namesImages[i], container.get(namesImages[i]));
        }
        storeNames.setData(KEY, namesImages);
    }
}
