package com.example.tomlam.rssfeed;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import java.util.List;

public class EditRssFeedActivity extends AppCompatActivity {
    List<RssFeed> feeds;
    ListView listView;
    BroadcastReceiver mRssListViewRefreshReceiver;
    RssFeedsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_rss_feed);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mRssListViewRefreshReceiver = createRssListViewRefreshReceiverHandler();
        final FloatingActionButton addFeedButton = findViewById(R.id.addFeedButton);
        addFeedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(addFeedButton.getContext(), AddFeedActivity.class);
                startActivity(intent);
            }
        });

        LocalBroadcastManager.getInstance(this).registerReceiver(
                mRssListViewRefreshReceiver, new IntentFilter(Utils.EDIT_RSS_FEEDS_REFRESH_INTENT)
        );

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        feeds = new Database(this).getRssFeeds();
        adapter = new RssFeedsAdapter(this, feeds);

        listView = findViewById(R.id.editRssListView);
        listView.setAdapter(adapter);
    }

    private BroadcastReceiver createRssListViewRefreshReceiverHandler() {
        return new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                feeds = new Database(EditRssFeedActivity.this).getRssFeeds();
                adapter = new RssFeedsAdapter(EditRssFeedActivity.this, feeds);
                listView.setAdapter(adapter);
            }
        };
    }
}
