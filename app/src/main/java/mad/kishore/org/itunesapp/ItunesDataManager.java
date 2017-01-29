package mad.kishore.org.itunesapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.List;

/**
 * Created by kishorekolluru on 10/24/16.
 */

public class ItunesDataManager {
    private Context mContext;
    private DatabaseOpenHelper openHelper;
    private SQLiteDatabase database;
private ItunesDao itunesDao;

    public ItunesDataManager(Context context) {
        Log.d("SQL", "Data Mnaager instantiated");
        this.mContext = context;
        openHelper = new DatabaseOpenHelper(mContext);
        database = openHelper.getWritableDatabase();
        itunesDao = new ItunesDao(database);
    }
    public void close(){
        if(database !=null)
        {
            database.close();
        }
    }
    public boolean saveItem(ItunesAppItem cities){
        return this.itunesDao.save(cities);
    }

    public boolean updateCities(ItunesAppItem cities){
        return itunesDao.update(cities);
    }

    public boolean deleteCities(ItunesAppItem cities){
        return this.itunesDao.delete(cities);
    }

    public ItunesAppItem getCities(String cityName){
        return this.itunesDao.get(cityName);
    }

    public List<ItunesAppItem> getAllItunesItems(){
        return this.itunesDao.getAll();
    }


}
