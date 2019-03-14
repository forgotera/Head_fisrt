package com.example.mura.workaut;

public class Workaut {
    private String name;
    private String descr;

    public static final Workaut[] workauts = {
            new Workaut("The limb Loosener","5 Handstand push-ups\n10 1-legged squats\n15 Pull-ups"),
            new Workaut("Core Agony","sdfasdgasdfhadfhsdrfgdfgsdfgdsfgsdfgsd"),
            new Workaut("The Wimp Special","124123513457356833456734572345734"),
            new Workaut("Strenght and Lenght","qwerttytuioupyuioyutrywe")
    };

    public Workaut(String name, String descr) {
        this.name = name;
        this.descr = descr;
    }

    public String getName() {
        return name;
    }

    public String getDescr() {
        return descr;
    }

    public String toString() {
        return this.name;
    }
}
