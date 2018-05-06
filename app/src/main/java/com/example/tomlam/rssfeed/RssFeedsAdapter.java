package com.example.tomlam.rssfeed;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

public class RssFeedsAdapter extends ArrayAdapter<RssFeed> {
    List<RssFeed> mFeeds;
    Context mParentActivity;
    private Intent mRefreshListEventIntent = new Intent(Utils.EDIT_RSS_FEEDS_REFRESH_INTENT);

    public RssFeedsAdapter(Context context, List<RssFeed> feeds) {

        super(context, 0, feeds);
        mFeeds = feeds;
        mParentActivity = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final RssFeed feed = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.rss_edit_feed_item_row, parent, false);
        }

        TextView feedAddress = convertView.findViewById(R.id.rssEditFeedAddressTextView);
        TextView feedTitle = convertView.findViewById(R.id.rssEditFeedTitleTextView);
        ImageButton changeFeedButton = convertView.findViewById(R.id.changeRSSFeedButton);
        ImageButton deleteFeedButton = convertView.findViewById(R.id.deleteRSSFeedButton);

        feedAddress.setText(feed.rssFeedAddress);
        feedTitle.setText(feed.rssFeedTitle);
        final int positionToRemove = position;

        deleteFeedButton.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(mParentActivity);
                dialog.setTitle("Remove Feed");
                dialog.setMessage("Are you sure you want to remove this feed?");

                dialog.setPositiveButton("Yes", new AlertDialog.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        RssFeed selectedFeed = mFeeds.get(positionToRemove);
                        new Database(mParentActivity).deleteRssFeed(selectedFeed);
                        LocalBroadcastManager.getInstance(mParentActivity).sendBroadcast(mRefreshListEventIntent);
                    }
                });
                dialog.setNegativeButton("No", null);
                dialog.show();
            }
        });

        changeFeedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RssFeed selectedFeed = mFeeds.get(positionToRemove);
                Intent intent = new Intent(mParentActivity, ChangeFeedActivity.class);
                intent.putExtra(Utils.CHANGE_RSS_FEED_ID, selectedFeed.id);
                mParentActivity.startActivity(intent);
            }
        });
        return convertView;
    }
}
