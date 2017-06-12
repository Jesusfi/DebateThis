package com.example.jesusizquierdo.debatethis.Classes;

/**
 * Created by Jesus Izquierdo on 5/15/2017.
 */

public class NewsSource {
    String name;
    String APIrequest;

    public NewsSource(String name, String APIrequest) {
        this.name = name;
        this.APIrequest = APIrequest;
    }

    public String getName() {
        return name;
    }

    public String getAPIrequest() {
        return APIrequest;
    }
}
