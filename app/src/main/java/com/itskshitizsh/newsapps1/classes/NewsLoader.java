package com.itskshitizsh.newsapps1.classes;

import android.content.AsyncTaskLoader;
import android.content.Context;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class NewsLoader extends AsyncTaskLoader<List<News>> {

    private String REQUEST_URL;

    public NewsLoader(Context context) {
        super(context);
        REQUEST_URL =  "https://content.guardianapis.com/search?&show-tags=contributor&api-key=dcd9ad5e-c852-4c47-bc8f-eab7f3411f07";
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<News> loadInBackground() {
        List<News> newsItems = new ArrayList<>();
        if (REQUEST_URL == null) {
            return null;
        }
        try {
            newsItems = QueryUtils.fetchNewsData(REQUEST_URL);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return newsItems;
    }
}