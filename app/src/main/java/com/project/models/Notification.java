package com.project.models;

import java.util.ArrayList;

public class Notification {
    String title;
    String body;
    ArrayList<String> infectors;
    boolean notified;
    public Notification(String title, String body, ArrayList<String> infectors, boolean notified) {
        this.title = title;
        this.body = body;
        this.infectors = infectors;
        this.notified = notified;
    }

    public Notification(){

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public ArrayList<String> getInfectors() {
        return infectors;
    }

    public void setInfectors(ArrayList<String> infectors) {
        this.infectors = infectors;
    }

    public boolean isNotified() {
        return notified;
    }

    public void setNotified(boolean notified) {
        this.notified = notified;
    }

    @Override
    public String toString() {
        return "Notification{" +
                "title='" + title + '\'' +
                ", body='" + body + '\'' +
                ", infectors=" + infectors.size() +
                ", notified=" + notified +
                '}';
    }
}
