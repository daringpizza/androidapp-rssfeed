package com.example.tomlam.rssfeed;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ChangeFeedActivity extends AppCompatActivity {
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_feed);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final EditText feedName = findViewById(R.id.changeFeedNameEditText);
        final EditText feedAddress = findViewById(R.id.changeFeedEditText);
        final TextView feedID = findViewById(R.id.changeFeedID);
        final Button saveButton = findViewById(R.id.changeFeedButton);

        id = getIntent().getIntExtra(Utils.CHANGE_RSS_FEED_ID, 0);
        RssFeed rssFeed = new Database(this).getRssFeedByID(id);

        if (rssFeed != null) {
            feedName.setText(rssFeed.rssFeedTitle);
            feedAddress.setText(rssFeed.rssFeedAddress);
            feedID.setText("" + rssFeed.id);
        }

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (feedAddress.getText().length() < 6) {
                    Toast.makeText(getApplicationContext(), "RSS Feed Address is too Short", Toast.LENGTH_LONG).show();
                    return;
                }

                Database db = new Database(ChangeFeedActivity.this);
                db.updateRssFeed(id, feedName.getText().toString(), feedAddress.getText().toString());
                Toast.makeText(getApplicationContext(), "RSS Feed Changed Successfully!", Toast.LENGTH_LONG).show();
            }
        });
    }
}
