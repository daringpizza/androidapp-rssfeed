package com.example.tomlam.rssfeed;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddFeedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_feed);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Button saveButton = findViewById(R.id.addFeedButton);
        final EditText feedAddress = findViewById(R.id.addFeedEditText);
        final EditText feedName = findViewById(R.id.addFeedNameEditText);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (feedAddress.getText().length() < 6) {
                    Toast.makeText(getApplicationContext(), "RSS Feed Address is too Short", Toast.LENGTH_LONG).show();
                    return;
                }

                Database db = new Database(AddFeedActivity.this);
                RssFeed feed = new RssFeed(feedName.getText().toString(), feedAddress.getText().toString());

                db.addRssFeed(feed);
                Toast.makeText(getApplicationContext(), "RSS Feed Added Successfully!", Toast.LENGTH_LONG).show();
                feedAddress.setText("");
                feedName.setText("");
            }
        });
    }

}
