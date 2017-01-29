package mad.kishore.org.itunesapp;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by kishorekolluru on 10/24/16.
 */
public class ItunesTable {
    public static final String COLOUMN_NAME = "NAME";
    public static final String COLOUMN_PRICE = "PRICE";
    public static final String TABLE_NAME = "ITUNES_TABLE";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_ICON = "ICON";

    public static void onCreate(SQLiteDatabase db) {
        Log.d("SQL", "Creating table");
        StringBuilder builder = new StringBuilder();
        builder.append("CREATE TABLE ").append(TABLE_NAME).append("(")
                .append(COLUMN_ID).append(" integer primary key autoincrement,")
                .append(COLOUMN_NAME).append(" text not null, ")
                .append(COLOUMN_PRICE).append(" number not null, ")
                .append(COLUMN_ICON).append(" text")
                .append("); ");
        try {
            db.execSQL(builder.toString());
        } catch (SQLException e) {
            Log.d("SQL", "Exception occured: " + e.getMessage());
        }
    }

    public static void onUpgrade(SQLiteDatabase db) {
        Log.d("SQL", "OnUpgrade called");
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME+";");
        onCreate(db);
    }
}
