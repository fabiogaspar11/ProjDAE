package pt.ipleiria.estg.dei.ei.dae.prc;


import java.io.Serializable;

public class Data implements Serializable {
    private int year;
    private int month;
    private int day;

    public Data(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }


}