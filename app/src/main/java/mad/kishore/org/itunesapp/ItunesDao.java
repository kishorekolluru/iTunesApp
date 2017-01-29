package mad.kishore.org.itunesapp;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kishorekolluru on 10/24/16.
 */
public class ItunesDao {
    private SQLiteDatabase database;

    public ItunesDao(SQLiteDatabase database) {
        this.database = database;
    }

    public boolean save(ItunesAppItem city){
        ContentValues values = new ContentValues();
        values.put(ItunesTable.COLOUMN_NAME,city.getName());
        values.put(ItunesTable.COLOUMN_PRICE,city.getPrice());
        values.put(ItunesTable.COLUMN_ICON,city.getIcon());
        return database.insert(ItunesTable.TABLE_NAME, null, values)>0;
    }

    public boolean update(ItunesAppItem city){
        ContentValues values = new ContentValues();
        values.put(ItunesTable.COLOUMN_NAME,city.getName());
        values.put(ItunesTable.COLOUMN_PRICE,city.getPrice());
        return database.update(ItunesTable.TABLE_NAME,values,ItunesTable.COLOUMN_NAME+"=?",new String[]{city.getName()+""})>0;
    }

    public boolean delete(ItunesAppItem city){
        return database.delete(ItunesTable.TABLE_NAME,ItunesTable.COLUMN_ID +"=?", new String[]{city.getName()+""})>0;
    }

    public ItunesAppItem get(String appName){
        ItunesAppItem cities = null;

        Cursor c = database.query(true, ItunesTable.TABLE_NAME, new String[]{ItunesTable.COLOUMN_NAME, ItunesTable.COLOUMN_PRICE
                , ItunesTable.COLUMN_ICON}, ItunesTable.COLOUMN_NAME + "=?", new String[]{appName}, null, null, null, null);

        if (c != null && c.moveToFirst()) {
            ItunesAppItem city = buildCityFromCursor(c);
            if(!c.isClosed()){
                c.close();
            }
        }
        return cities;
    }

    public List<ItunesAppItem> getAll(){
        List<ItunesAppItem> citiesList = new ArrayList<ItunesAppItem>();
        Cursor c=database.query(ItunesTable.TABLE_NAME,new String[]{ItunesTable.COLOUMN_NAME, ItunesTable.COLOUMN_PRICE,ItunesTable.COLUMN_ICON
                        , ItunesTable.COLUMN_ID}
                ,null,null,null,null,null);
        if (c != null && c.moveToFirst()) {
            do{
                ItunesAppItem itunesAppItem = buildCityFromCursor(c);
                citiesList.add(itunesAppItem);

            }while(c.moveToNext());
            if (!c.isClosed()) {
                c.close();
            }
        }
        return citiesList;
    }

    private ItunesAppItem buildCityFromCursor(Cursor c){
        ItunesAppItem appItem = null;
        if(c!=null){
            appItem = new ItunesAppItem();
            appItem.setName(c.getString(c.getColumnIndex(ItunesTable.COLOUMN_NAME)));
            appItem.setPrice(c.getDouble(c.getColumnIndex(ItunesTable.COLOUMN_PRICE)));
            appItem.setIcon(c.getString(c.getColumnIndex(ItunesTable.COLUMN_ICON)));
        }
        return appItem;
    }

}
