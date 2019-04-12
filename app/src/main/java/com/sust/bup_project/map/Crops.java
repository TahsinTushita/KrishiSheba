package com.sust.bup_project.map;

public class Crops {

    String place;

    public Crops(String place, String name) {
        this.place = place;
        this.name = name;
    }

    String name;

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public Crops() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
