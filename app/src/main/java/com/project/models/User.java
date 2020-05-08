package com.project.models;

import com.google.firebase.Timestamp;
import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class User {
    String mail;
    String password;
    Timestamp creationDate;

    public User(String mail, String password, Timestamp timeStamp) {
        this.mail = mail;
        this.password = password;
        this.creationDate = timeStamp;
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

    @Override
    public String toString() {
        return "User{" +
                "mail='" + mail + '\'' +
                ", password='" + password + '\'' +
                ", creationDate=" + creationDate +
                '}';
    }
}
