package com.example.jesusizquierdo.debatethis.Classes;

/**
 * Created by Jesus Izquierdo on 9/22/2017.
 */

public class ArticleInfoDiscussion {

    String url;
    String imageURL;
    String articleSummary;
    String articleTitle;
    String uniqueKey;

    public ArticleInfoDiscussion( String url, String imageURL, String articleSummary, String articleTitle,String uniqueKey) {
        this.url = url;
        this.imageURL = imageURL;
        this.articleSummary = articleSummary;
        this.articleTitle = articleTitle;
        this.uniqueKey = uniqueKey;
    }

    public ArticleInfoDiscussion() {
    }
}
