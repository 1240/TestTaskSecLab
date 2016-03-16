package com.l24o.testtaskseclab.loaders;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;


import java.io.IOException;
import java.util.Map;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;

/**
 * Created by l24o on 16.03.16.
 */
public class YahooLoader extends AsyncTaskLoader<Map<String, Stock>> {

    private final String[] data;

    public YahooLoader(Context context, String[] data) {
        super(context);
        this.data = data;
        forceLoad();
    }

    @Override
    public Map<String, Stock> loadInBackground() {
        try {
            return YahooFinance.get(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
