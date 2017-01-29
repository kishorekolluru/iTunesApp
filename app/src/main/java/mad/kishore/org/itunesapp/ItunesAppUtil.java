package mad.kishore.org.itunesapp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kishorekolluru on 10/24/16.
 */
public class ItunesAppUtil {


    public static ArrayList<ItunesAppItem> parseItunesAppItemsJson(StringBuilder b) throws JSONException {
        JSONObject main = new JSONObject(b.toString());
        ArrayList<ItunesAppItem> itunesItems = new ArrayList<>();
        JSONArray jsonItem = main.getJSONObject("feed").getJSONArray("entry");
        for( int i =0; i< jsonItem.length() ; i++) {
            ItunesAppItem item = new ItunesAppItem();
            JSONObject obj = jsonItem.getJSONObject(i);
            item.setName(obj.getJSONObject("im:name").getString("label"));
            item.setIcon(getImage(obj));
            item.setPrice(getPrice(obj));
            itunesItems.add(item);
        }
        return itunesItems;
    }

    private static double getPrice(JSONObject obj) throws JSONException {
        return obj.getJSONObject("im:price").getJSONObject("attributes").getDouble("amount");
    }

    private static String getImage(JSONObject obj) throws JSONException {
        JSONArray array = obj.getJSONArray("im:image");
        for(int j =0; j<array.length(); j++) {
            if (array.getJSONObject(j).getJSONObject("attributes").getInt("height") < 60) {
                return array.getJSONObject(j).getString("label");
            }
        }
return "";
    }
}
