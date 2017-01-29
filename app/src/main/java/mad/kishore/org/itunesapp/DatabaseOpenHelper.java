package mad.kishore.org.itunesapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by kishorekolluru on 10/24/16.
 */

public class DatabaseOpenHelper extends SQLiteOpenHelper {
    static final String DB_NAME = "itunes_app.db";
    static final int DB_VERSION = 1;
    public DatabaseOpenHelper(Context mContext) {
        super(mContext,DB_NAME,null,DB_VERSION);
        Log.d("SQL", "Open Helper instantiated");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        ItunesTable.onCreate(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        ItunesTable.onUpgrade(db);
    }
}
