package mad.kishore.org.itunesapp;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static android.R.id.list;

public class MainActivity extends AppCompatActivity implements ItunesDownloadTask.ItunesDownloadActivityInterface {
    ProgressDialog pdialog;
    ListView lview;
    RecyclerView rview;
    TextView toggleStatus;
    ImageView imageRefresh;
    Switch toggle;
    ItunesDataManager dm;

    List<ItunesAppItem> filteredItems;
    ArrayList<ItunesAppItem> appItems;
    ItunesRecyclerAdapter recAdapter;
    ItunesListAdapter listAdapter;
    boolean isAscending = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pdialog = new ProgressDialog(this);
        pdialog.setMessage("Loading...");
        dm = new ItunesDataManager(this);
        new ItunesDownloadTask(this).execute("https://itunes.apple.com/us/rss/toppaidapplications/limit=25/json");

        lview = (ListView) findViewById(R.id.lview);
        rview = (RecyclerView) findViewById(R.id.recyclerView);
        toggleStatus = (TextView) findViewById(R.id.tvToggleStatus);
        imageRefresh = (ImageView) findViewById(R.id.imageRefresh);
        toggle = (Switch) findViewById(R.id.switchOrder);

        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.d("debug", "switch enables");
                if (isChecked) {
                    isAscending = true;
                }else {
                    isAscending = false;
                }
            }
        });
        lview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                ItunesAppItem item = dm.getCities(appItems.get(position).getName());
                if(item==null){
                    addItemToSql(appItems.get(position));
                    listAdapter.remove(position);
                    listAdapter.notifyDataSetChanged();
                    refreshFilteredList();
                }
                return true;
            }
        });

        filteredItems = new ArrayList<>();
        LinearLayoutManager mgr = new LinearLayoutManager(this);
        mgr.setOrientation(LinearLayoutManager.HORIZONTAL);
        recAdapter = new ItunesRecyclerAdapter(this, filteredItems);
        rview.setLayoutManager(mgr);
        rview.setAdapter(recAdapter);
        refreshFilteredList();

    }



    private void addItemToSql(ItunesAppItem itunesAppItem) {
        dm.saveItem(itunesAppItem);
    }



    @Override
    public void giveItems(ArrayList<ItunesAppItem> items) {
        appItems = items;
        displayItems();
    }

    List<ItunesAppItem> listItems;
    private void displayItems() {
        listItems = filterListItems();
        listAdapter = new ItunesListAdapter(this, R.layout.list_item, appItems);
        listAdapter.setNotifyOnChange(true);
        lview.setAdapter(listAdapter);


    }

    private List<ItunesAppItem> filterListItems() {
        List<ItunesAppItem> items = dm.getAllItunesItems();
        List<ItunesAppItem> newILis = new ArrayList<>(appItems);
        boolean flag = false;
        for(int i =0; i< appItems.size(); i++) {
            flag = false;
            for(int j=0; j<items.size(); j++) {
                if(appItems.get(i).getName().equals(items.get(j).getName())){
                    flag = true;
                }
            }
            if (flag == false) {
                newILis.add(appItems.get(i));
            }
        }
        return newILis;
    }

    private void refreshFilteredList() {
        for(int i =0; i< filteredItems.size(); i++){
            filteredItems.remove(i);
        }
        filteredItems.addAll(dm.getAllItunesItems());
        recAdapter.notifyDataSetChanged();

    }

    @Override
    public void stopProgress() {
        pdialog.dismiss();

    }

}
