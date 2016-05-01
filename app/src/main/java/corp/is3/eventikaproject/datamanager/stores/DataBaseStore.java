package corp.is3.eventikaproject.datamanager.stores;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import corp.is3.eventikaproject.dbm.DataBase;

// T - тип класса с котором будет вестить работа
public class DataBaseStore<T> implements Store<T> {

    private final String TABLE_NAME = "cache";
    private final Context CONTEXT;
    private final DataBase DB;

    private final long TIME_REMOVAL = 3 * 24 * 60 * 60 * 1000;

    private Class typeObject;

    private boolean isOneSession = false;

    public DataBaseStore(Context context, Class typeObject) {
        CONTEXT = context;
        DB = new DataBase(CONTEXT);
        this.typeObject = typeObject;
        removeOld();
    }

    // Надо сделать :)
    public void setPeriodStorage(int numberHours) {

    }

    public void isOneSession(boolean isOneSession) {
        this.isOneSession = isOneSession;
    }

    /**
     * запись в БД объекта "data" под ключем "key"
     */
    public boolean setData(String key, T data) {
        ContentValues values = toContentValues(key, data);
        long id = -1;
        if (values != null)
            try {
                SQLiteDatabase db = DB.getWritableDatabase();
                if (getData(key) == null)
                    id = db.insert(TABLE_NAME, null, values);
                else
                    db.update(TABLE_NAME, values, "key=?", new String[]{key});
            } catch (Exception e) {
                Log.e("DBS", e.toString());
                id = -1;
            }
        return id != -1;
    }

    /**
     * получение объекта из БД по ключу "key"
     */
    public T getData(String key) {
        if (key == null)
            return null;
        Cursor response = DB.getReadableDatabase().query(TABLE_NAME, null, "key = ?", new String[]{key}, null, null, null);
        T value = null;
        try {
            response.moveToFirst();
            int ind = response.getColumnIndex("value");
            value = jsonToJavaObject(response.getString(ind), typeObject);
        } catch (Exception e) {
            Log.e("DBS", e.toString());
        } finally {
            response.close();
        }
        return value;
    }

    private void removeOld() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                DB.getWritableDatabase().delete(TABLE_NAME, "currentTime < ?",
                        new String[]{String.valueOf(System.currentTimeMillis() - TIME_REMOVAL)});
            }
        }).run();
    }

    private ContentValues toContentValues(String key, Object value) {
        if (key == null || value == null)
            return null;
        ContentValues map = new ContentValues();
        map.put("key", key);
        map.put("value", objectToJson(value));
        if (isOneSession)
            map.put("currentTime", System.currentTimeMillis() - TIME_REMOVAL);
        else
            map.put("currentTime", System.currentTimeMillis());
        return map;
    }

    private T jsonToJavaObject(String json, Class typeObject) {
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