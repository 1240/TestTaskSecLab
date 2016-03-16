package com.l24o.testtaskseclab;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;

import com.l24o.testtaskseclab.loaders.YahooLoader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import yahoofinance.Stock;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Map<String, Stock>> {

    public static final String GOOGLE = "GOOG";
    private YahooRecyclerViewAdapter adapter;
    private Map<String, Stock> values = new HashMap<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.list);
        adapter = new YahooRecyclerViewAdapter(new ArrayList<Stock>(values.values()));
        recyclerView.setAdapter(adapter);
        getSupportLoaderManager().initLoader(R.id.loader_id, null, this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_main, menu);
        final MenuItem myActionMenuItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) myActionMenuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (!searchView.isIconified()) {
                    searchView.setIconified(true);
                }
                myActionMenuItem.collapseActionView();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapter.setData(sort(s));
                adapter.notifyDataSetChanged();
                return false;
            }
        });
        return true;
    }

    @Override
    public Loader<Map<String, Stock>> onCreateLoader(int id, Bundle args) {
        switch (id) {
            case R.id.loader_id: {
                return new YahooLoader(this, new String[]{"GOOG", "AAPL", "INTC", "TWTR", "FB", "NFLX", "AMZN", "LNKD", "YHOO"});
            }
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Map<String, Stock>> loader, Map<String, Stock> data) {
        int id = loader.getId();
        if (id == R.id.loader_id) {
            this.values = recreate(data);
            adapter.setData(new ArrayList<Stock>(this.values.values()));
            adapter.notifyDataSetChanged();
            getSupportLoaderManager().destroyLoader(R.id.loader_id);
        }
    }

    @Override
    public void onLoaderReset(Loader<Map<String, Stock>> loader) {

    }

    private Map<String, Stock> recreate(Map<String, Stock> data) {
        Map<String, Stock> result = new HashMap<>();
        for (String key : data.keySet()) {
            Stock stock = data.get(key);
            result.put(stock.getName(), stock);
        }
        return result;
    }

    private List<Stock> sort(String name) {
        if (name.isEmpty())
            return new ArrayList<>(values.values());
        for (String key : values.keySet()) {
            if (key.toUpperCase().contains(name.toUpperCase()))
                return Collections.singletonList(values.get(key));
        }
        return null;
    }

}
