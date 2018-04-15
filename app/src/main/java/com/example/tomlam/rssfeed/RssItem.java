package com.example.tomlam.rssfeed;

import java.net.URL;

public class RssItem {
    private String title;
    private String description;
    private URL link;

    public RssItem(String title, String description, URL link) {
        this.title = title;
        this.description = description;
        this.link = link;
    }

    public RssItem() {

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public URL getLink() {
        return link;
    }

    public void setLink(URL link) {
        this.link = link;
    }
}
