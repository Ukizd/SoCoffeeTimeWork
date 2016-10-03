package com.example.patryk.kamiltestfirebase;

/**
 * Created by Kamil on 17.08.2016.
 */
public class Dzien {
    public String dzien;
    public String godzinyOd;
    public String godzinyDo;
    public String liczbaGodzin;

    public Dzien(String godzinyOd, String godzinyDo, String liczbaGodzin) {
        this.godzinyOd = godzinyOd;
        this.godzinyDo = godzinyDo;
        this.liczbaGodzin = liczbaGodzin;
    }

    public String getDzien() {

        return dzien;
    }

    public void setDzien(String dzien) {
        this.dzien = dzien;
    }


}
