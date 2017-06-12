package com.example.jesusizquierdo.debatethis.Classes;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Jesus Izquierdo on 5/14/2017.
 */

public class News implements Parcelable {

    private String status;
    private String source;
    private String sortBy;
    private List<Articles> articles = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public List<Articles> getArticles() {
        return articles;
    }

    public void setArticles(List<Articles> articles) {
        this.articles = articles;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.status);
        dest.writeString(this.source);
        dest.writeString(this.sortBy);
        dest.writeTypedList(this.articles);
    }

    public News() {
    }

    protected News(Parcel in) {
        this.status = in.readString();
        this.source = in.readString();
        this.sortBy = in.readString();
        this.articles = in.createTypedArrayList(Articles.CREATOR);
    }

    public static final Parcelable.Creator<News> CREATOR = new Parcelable.Creator<News>() {
        @Override
        public News createFromParcel(Parcel source) {
            return new News(source);
        }

        @Override
        public News[] newArray(int size) {
            return new News[size];
        }
    };
}

