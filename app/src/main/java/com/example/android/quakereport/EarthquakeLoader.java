package com.example.android.quakereport;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * Created by akilan on 11/01/17.
 */

public class EarthquakeLoader extends AsyncTaskLoader<List<Earthquake>> {
    private String url;

    public EarthquakeLoader(Context context, String url) {
        super(context);
        this.url = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Earthquake> loadInBackground() {
        List<Earthquake> earthquakes = QueryUtils.fetchEarthquakeData(url);
        if (earthquakes != null && !earthquakes.isEmpty()) {
            return earthquakes;
        }
        return null;
    }
}
