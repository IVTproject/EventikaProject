package corp.is3.eventikaproject.dbm;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import corp.is3.eventikaproject.R;

public class DataBase extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;

    public DataBase(Context context) {
        super(context, context.getResources().getString(R.string.app_name), null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table cache (id integer primary key autoincrement, key text not null, " +
                "value text not null, currentTime integer, periodStore integer);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion != newVersion) {
            db.execSQL("DROP TABLE IF EXISTS cache");
            onCreate(db);
        }
    }
}