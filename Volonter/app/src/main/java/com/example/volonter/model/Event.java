package com.example.volonter.model;

public class Event {


    public Event(String name_events, String data, String organizer, String place, String direction) {
        this.name_events = name_events;
        this.data = data;
        this.organizer = organizer;
        this.place = place;
        this.direction = direction;
    }

    public String getName_events() {
        return name_events;
    }

    public void setName_events(String name_events) {
        this.name_events = name_events;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getOrganizer() {
        return organizer;
    }

    public void setOrganizer(String organizer) {
        this.organizer = organizer;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    private String name_events, data, organizer, place, direction;

    public Event() {};
}
