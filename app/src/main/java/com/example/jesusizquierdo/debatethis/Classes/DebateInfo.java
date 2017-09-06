package com.example.jesusizquierdo.debatethis.Classes;

/**
 * Created by Jesus Izquierdo on 7/21/2017.
 */
public class DebateInfo {
    String title;
    String uniqueID;
    String user;
    String topic;
    public DebateInfo(String title,String uniqueID,String topic){
        this.title = title;
        this.topic = topic;
        this.uniqueID = uniqueID;
    }
    public  DebateInfo(){}

    public String getTitle() {
        return title;
    }

    public String getUniqueID() {
        return uniqueID;
    }

    public String getUser() {
        return user;
    }

    public String getTopic() {
        return topic;
    }
}

