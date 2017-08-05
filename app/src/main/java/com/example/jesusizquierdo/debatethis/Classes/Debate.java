package com.example.jesusizquierdo.debatethis.Classes;

import android.graphics.Point;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jesus Izquierdo on 7/21/2017.
 */

public class Debate implements Serializable{

    String title;
    String uniqueID;

    public Debate(String title,String uniqueID) {
        this.title = title;
        this.uniqueID = uniqueID;
      //  pros = new ArrayList<>();
    }
    public Debate(){}

    public String getUniqueID() {

        return uniqueID;
    }

    public String getTitle() {
        return title;
    }

}
