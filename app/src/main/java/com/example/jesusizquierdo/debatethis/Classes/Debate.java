package com.example.jesusizquierdo.debatethis.Classes;

import android.graphics.Point;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jesus Izquierdo on 7/21/2017.
 */

public class Debate {

    String title;
    String uniqueID;
    List<Points> pros;
    List<Points> cons;

    public Debate(String title,String uniqueID) {
        this.title = title;
        this.uniqueID = uniqueID;
      //  pros = new ArrayList<>();
        cons = new ArrayList<>();
    }
    public Debate(){}

    public List<Points> getPros() {
        return pros;
    }

    public List<Points> getCons() {
        return cons;
    }

    public void setPros(List<Points> pros) {
        this.pros = pros;
    }

    public void setCons(List<Points> cons) {
        this.cons = cons;
    }

    public String getUniqueID() {

        return uniqueID;
    }

    public String getTitle() {
        return title;
    }
    public void addToCon(String pointHeader, String pointArg,String pointSource){
        cons.add(new Points(pointHeader,pointArg,pointSource));
    }
    public void addToPro(String pointHeader, String pointArg,String pointSource){
        pros.add(new Points(pointHeader,pointArg,pointSource));
    }
    public int getProSize(){
        return pros.size();
    }
    public int getConSize(){
        return  cons.size();
    }
}
