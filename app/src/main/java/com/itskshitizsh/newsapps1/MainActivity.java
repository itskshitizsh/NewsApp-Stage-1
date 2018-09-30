package com.itskshitizsh.newsapps1;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.itskshitizsh.newsapps1.classes.News;
import com.itskshitizsh.newsapps1.classes.NewsAdapter;
import com.itskshitizsh.newsapps1.classes.NewsLoader;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<News>> {

    private SwipeRefreshLayout swipeRefreshLayout;
    private ListView newsListView;
    private LinearLayout progressLayout;
    private LoaderManager loaderManager;
    private NewsAdapter mAdapter;
    private ProgressBar progressBar;
    private ArrayList<News> newsList;
    boolean doubleBackToExitPressedOnce = false;


    @Override
    protected void onResume() {
        super.onResume();
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progressBar);
        swipeRefreshLayout = findViewById(R.id.internetNotAvailable);
        newsListView = findViewById(R.id.listView);
        progressLayout = findViewById(R.id.progressView);
        newsListView.setVisibility(View.VISIBLE);
        
        newsList = new ArrayList<>();
        
        if(checkNetwork()) {
            progressLayout.setVisibility(View.GONE);
            mAdapter = new NewsAdapter(this, new ArrayList<News>());
            newsListView.setAdapter(mAdapter);
            newsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    News currentNews = mAdapter.getItem(position);
                    Uri newsUri = Uri.parse(currentNews.getNewsUrl());
                    Intent intent = new Intent(Intent.ACTION_VIEW,newsUri);
                    startActivity(intent);
                    ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                    NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                    if (networkInfo != null && networkInfo.isConnected()) {
                        Toast.makeText(getApplicationContext(),"Opening News",Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(getApplicationContext(),"Opening News, Warning\nCheck Internet Connection",Toast.LENGTH_SHORT).show();
                        }
                }
            });
            newsListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    try {
                        News currentNews = mAdapter.getItem(position);
                        Uri newsUri = Uri.parse(currentNews.getNewsAuthorUrl());
                        Intent intent = new Intent(Intent.ACTION_VIEW, newsUri);
                        startActivity(intent);
                        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                        if (networkInfo != null && networkInfo.isConnected()) {
                            Toast.makeText(getApplicationContext(), "Opening Author Profile", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getApplicationContext(),"Opening Author Profile\nWarning: Check Internet Connection",Toast.LENGTH_SHORT).show();
                        }
                        return true;
                    }catch (Exception e)
                    {
                        Toast.makeText(getApplicationContext(),"Error while opening Author Profile:\n"+e.getMessage(), Toast.LENGTH_SHORT).show();
                        return false;
                    }
                }
            });
        }
    }


    @Override
    public Loader<List<News>> onCreateLoader(int id, Bundle args) {
        return new NewsLoader(this);
    }

    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> news) {

        if (news == null || news.isEmpty()) {
            TextView textView = findViewById(R.id.hint);
            textView.setText("No news found!\n.Unknown Error\nTry Again\nCheck Internet Connection them Reopen the App.");
        }else{
            newsListView.setVisibility(View.VISIBLE);
            newsList = new ArrayList<>(news);
            UpdateView(newsList);
            Toast.makeText(getApplicationContext(),"Updated!",Toast.LENGTH_SHORT).show();
            progressLayout.setVisibility(View.GONE);
        }
    }

    private void UpdateView(ArrayList<News> newsList) {
        progressLayout.setVisibility(View.GONE);
        mAdapter = new NewsAdapter(getApplicationContext(),newsList);
        newsListView.setAdapter(mAdapter);
    }

    @Override
    public void onLoaderReset(Loader<List<News>> loader) {
        mAdapter.clear();
    }

    private boolean checkNetwork() {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            swipeRefreshLayout.setVisibility(View.GONE);
            newsListView.setVisibility(View.GONE);
            progressLayout.setVisibility(View.VISIBLE);
            Toast.makeText(getApplicationContext(),"Connected",Toast.LENGTH_SHORT).show();
            loaderManager = getLoaderManager();
            loaderManager.initLoader(1, null, this);
            return true;
        } else {
            progressLayout.setVisibility(View.GONE);
            swipeRefreshLayout.setVisibility(View.VISIBLE);
            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    },2000);
                    checkNetwork();
                }
            });
            return false;
        }
    }
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this,"Please Click BACK Again To EXIT", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }
}
