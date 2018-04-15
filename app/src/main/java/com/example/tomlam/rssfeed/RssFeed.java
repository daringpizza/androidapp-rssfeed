package com.example.tomlam.rssfeed;

public class RssFeed {

    public int id;
    public String rssFeedTitle;
    public String rssFeedAddress;

    public RssFeed(String title, String address) {
        rssFeedTitle = title;
        rssFeedAddress = address;
    }

    public RssFeed(int rssId, String title, String address) {
        rssFeedTitle = title;
        rssFeedAddress = address;
        id = rssId;
    }

}
