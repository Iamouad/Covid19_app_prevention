package com.project.models;

import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.firestore.ServerTimestamp;
import com.google.type.Date;

@IgnoreExtraProperties
public class User {
    String mail;
    String password;
    @ServerTimestamp
    Date creationDate;

    public User(String mail, String password, Date timeStamp) {
        this.mail = mail;
        this.password = password;
        this.creationDate = timeStamp;
    }

    public User() {
    }

    public Date getTimeStamp() {
        return creationDate;
    }

    public void setTimeStamp(Date timeStamp) {
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
}
