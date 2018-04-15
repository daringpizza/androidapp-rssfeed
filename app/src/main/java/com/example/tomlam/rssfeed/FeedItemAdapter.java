package com.example.tomlam.rssfeed;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class FeedItemAdapter extends ArrayAdapter<RssItem> {

    public FeedItemAdapter(Context context, ArrayList<RssItem> items) {
        super(context, 0, 0, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        RssItem item = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.rss_feed_item_row, parent, false);
        }

        TextView title = convertView.findViewById(R.id.rssFeedItemTitleTextView);
        TextView link = convertView.findViewById(R.id.rssFeedItemSourceTextView);
        TextView description = convertView.findViewById(R.id.rssFeedItemDescriptionTextView);


        title.setText(item.getTitle());
        description.setText(item.getDescription());
        link.setText(item.getLink().toString());

        return convertView;
    }
}
