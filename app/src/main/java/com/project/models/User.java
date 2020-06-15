package com.project.models;

import com.google.firebase.Timestamp;
import com.google.firebase.database.IgnoreExtraProperties;
import com.project.util.Util;

@IgnoreExtraProperties
public class User {
    String mail;
    String password;
    Timestamp creationDate;
    boolean suspect;
    boolean confirmed;
    boolean tested;
    String macAddress;


    public User(String mail, String password, Timestamp creationDate) {
        this.mail = mail;
        this.password = password;
        this.creationDate = creationDate;
        this.suspect=false;
        this.tested=false;
        this.confirmed=false;
        this.macAddress = Util.getMacAddr();
    }

    public User() {
    }

    public void setSuspect(boolean suspect) {
        this.suspect = suspect;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
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
