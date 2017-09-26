package com.example.jesusizquierdo.debatethis.Classes;

import java.io.Serializable;

/**
 * Created by Jesus Izquierdo on 9/22/2017.
 */

public class ArticleInfoDiscussion implements Serializable {

    public String url;
    public String imageURL;
    public String articleSummary;
    public String articleTitle;
    public String uniqueKey;
    public String date;

    public ArticleInfoDiscussion( String url, String imageURL, String articleSummary, String articleTitle,String uniqueKey,String date) {
        this.url = url;
        this.imageURL = imageURL;
        this.articleSummary = articleSummary;
        this.articleTitle = articleTitle;
        this.uniqueKey = uniqueKey;
        this.date = date;
    }

    public ArticleInfoDiscussion() {
    }

    public String getUrl() {
        return url;
    }

    public String getImageURL() {
        return imageURL;
    }

    public String getArticleSummary() {
        return articleSummary;
    }

    public String getArticleTitle() {
        return articleTitle;
    }

    public String getUniqueKey() {
        return uniqueKey;
    }

    public String getDate() {
        return date;
    }
}
