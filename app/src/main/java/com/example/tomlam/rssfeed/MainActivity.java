package com.example.tomlam.rssfeed;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import at.theengine.android.simple_rss2_android.RSSItem;
import at.theengine.android.simple_rss2_android.SimpleRss2Parser;
import at.theengine.android.simple_rss2_android.SimpleRss2ParserCallback;

public class MainActivity extends AppCompatActivity {

    ArrayList<RssItem> rssItems;
    List<RssFeed> rssFeeds;
    int feedCount;
    int retrievedFeedCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(fab.getContext(), AddFeedActivity.class);
                startActivity(intent);
            }
        });

        rssItems = new ArrayList<>();
        rssFeeds = new Database(this).getRssFeeds();
        feedCount = rssFeeds.size();
        retrievedFeedCount = 0;
        for (int i = 0; i < rssFeeds.size(); i++) {
            GetFeedItems(rssFeeds.get(i).rssFeedAddress);
        }
    }

    public void GetFeedItems(String feedAddress) {
        try {
            SimpleRss2Parser parser = new SimpleRss2Parser(feedAddress,                             //"http://rss.cnn.com/rss/cnn_topstories.rss",
                    new SimpleRss2ParserCallback() {
                        @Override
                        public void onFeedParsed(List<RSSItem> items) {
                            for (int i = 0; i < items.size(); i++) {
                                RssItem item = new RssItem();
                                item.setTitle(items.get(i).getTitle());
                                item.setDescription(items.get(i).getDescription());
                                item.setLink(items.get(i).getLink());
                                Log.d("ITEM RECEIVED", items.get(i).getTitle());
                                rssItems.add(item);
                            }
                            PopulateListView();
                        }

                        @Override
                        public void onError(Exception ex) {
                            PopulateListView();
                        }
                    });
            parser.parseAsync();
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            PopulateListView();
        }
    }

    public void PopulateListView() {
        retrievedFeedCount++;
        if (retrievedFeedCount == feedCount) {
            Log.d("FEEDS RETRIEVED", "got all feeds");
            ListView listView = findViewById(R.id.rssFeedItemListView);
            listView.setAdapter(new FeedItemAdapter(this, rssItems));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, EditRssFeedActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
