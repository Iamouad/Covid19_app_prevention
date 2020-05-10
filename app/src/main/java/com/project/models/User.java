package com.project.models;

import com.google.firebase.Timestamp;
import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class User {
    String mail;
    String password;
    Timestamp creationDate;
    boolean suspect;
    boolean confirmed;
    boolean tested;


    public User(String mail, String password, Timestamp timeStamp) {
        this.mail = mail;
        this.password = password;
        this.creationDate = timeStamp;
        this.suspect=false;
        this.tested=false;
        this.confirmed=false;
    }

    public User() {
    }

    public Timestamp getTimeStamp() {
        return creationDate;
    }

    public void setTimeStamp(Timestamp timeStamp) {
        this.creationDate = timeStamp;
    }



    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isSuspect() {
        return suspect;
    }

    public void setSuscpect(boolean suspect) {
        this.suspect = suspect;
    }

    public boolean isTested() {
        return tested;
    }

    public void setTested(boolean tested) {
        this.tested = tested;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }


    @Override
    public String toString() {
        return "User{" +
                "mail='" + mail + '\'' +
                ", password='" + password + '\'' +
                ", creationDate=" + creationDate +
                '}';
    }
}
