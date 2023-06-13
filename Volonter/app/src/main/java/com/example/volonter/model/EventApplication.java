package com.example.volonter.model;

public class EventApplication {

    private String name_event, fio, mail;

    public String getName_event() {
        return name_event;
    }

    public void setName_event(String name_event) {
        this.name_event = name_event;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public EventApplication(String name_event, String fio, String mail) {
        this.name_event = name_event;
        this.fio = fio;
        this.mail = mail;
    }

    public EventApplication(){}
}
