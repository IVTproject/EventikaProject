package corp.is3.eventikaproject.datamanager.stores;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import corp.is3.eventikaproject.R;

/**
 * Created by Дмитрий on 01.05.2016.
 */
public class SharedPreferencesStore<T> implements Store<T> {

    private final String NAME_FILE;
    private SharedPreferences mSettings;
    private Class typeObject;

    public SharedPreferencesStore(Context context, Class typeObject) {
        this.typeObject = typeObject;
        NAME_FILE = context.getResources().getString(R.string.app_name);
        mSettings = context.getSharedPreferences(NAME_FILE, Context.MODE_PRIVATE);
    }

    @Override
    public boolean setData(String key, T data) {
        try {
            if(mSettings.contains(key))
                mSettings.edit().remove(key);
            mSettings.edit().putString(key, objectToJson(data)).apply();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public T getData(String key) {
        try {
            if (mSettings.contains(key))
                return jsonToJavaObject(mSettings.getString(key, ""));
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    private T jsonToJavaObject(String json) {
        if ("null".equalsIgnoreCase(json))
            return null;
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        return (T) gson.fromJson(json, typeObject);
    }

    private String objectToJson(Object obj) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        return gson.toJson(obj);
    }

}
