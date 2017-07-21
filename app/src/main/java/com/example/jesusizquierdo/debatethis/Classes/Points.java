package com.example.jesusizquierdo.debatethis.Classes;

/**
 * Created by Jesus Izquierdo on 7/21/2017.
 */

public class Points {
    String header;
    String argument;
    String source;

    public Points(String header, String argument, String source) {
        this.header = header;
        this.argument = argument;
        this.source = source;
    }
    public Points(){}

    public String getHeader() {
        return header;
    }

    public String getArgument() {
        return argument;
    }

    public String getSource() {
        return source;
    }
}
