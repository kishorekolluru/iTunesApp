package mad.kishore.org.itunesapp;

import android.os.AsyncTask;
import android.os.HandlerThread;
import android.os.SystemClock;
import android.util.Log;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kishorekolluru on 10/24/16.
 */

public class ItunesDownloadTask extends AsyncTask<String, Void, ArrayList<ItunesAppItem>> {
    private ItunesDownloadActivityInterface activity;

    public ItunesDownloadTask(ItunesDownloadActivityInterface activity) {
        this.activity = activity;
    }

    @Override
    protected void onPostExecute(ArrayList<ItunesAppItem> itunesAppItems) {
        super.onPostExecute(itunesAppItems);
        activity.stopProgress();
        activity.giveItems(itunesAppItems);
    }

    @Override
    protected ArrayList<ItunesAppItem> doInBackground(String... params) {
        ArrayList<ItunesAppItem> itunesAppItems = new ArrayList<>();
        String url = params[0];
        try{
            HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
            con.connect();
            int statuscode = con.getResponseCode();
            if(statuscode== HttpURLConnection.HTTP_OK){
                BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                StringBuilder b = new StringBuilder();
                String line = br.readLine();
                while (line != null) {
                    b.append(line);
                    line = br.readLine();
                }
                itunesAppItems = ItunesAppUtil.parseItunesAppItemsJson(b);
            }
        } catch (IOException e) {
            Log.e("error", e.toString());
        } catch (JSONException e) {
            Log.e("error", e.toString());
        }
        for (ItunesAppItem i : itunesAppItems) {
            Log.d("debug",i.toString());
        }
        return itunesAppItems;
    }

    public static interface ItunesDownloadActivityInterface {
        public void giveItems(ArrayList<ItunesAppItem> items);

        public void stopProgress();
    }
}
