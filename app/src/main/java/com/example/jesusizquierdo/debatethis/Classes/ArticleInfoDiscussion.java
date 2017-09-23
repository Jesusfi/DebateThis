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

    public ArticleInfoDiscussion( String url, String imageURL, String articleSummary, String articleTitle,String uniqueKey) {
        this.url = url;
        this.imageURL = imageURL;
        this.articleSummary = articleSummary;
        this.articleTitle = articleTitle;
        this.uniqueKey = uniqueKey;
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
}
