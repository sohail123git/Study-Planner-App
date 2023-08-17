package com.example.test;

public class EventModel {
    private int id;
    private String title,date,time,description;
    private int eventType;

    public EventModel(int id, String title, String date, String time, String description, int eventType) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.time = time;
        this.description = description;
        this.eventType = eventType;
    }

    public EventModel() {
    }

    @Override
    public String toString() {
        return "EventModel{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", description='" + description + '\'' +
                ", eventType=" + eventType +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getEventType() {
        return eventType;
    }

    public void setEventType(int eventType) {
        this.eventType = eventType;
    }
}
