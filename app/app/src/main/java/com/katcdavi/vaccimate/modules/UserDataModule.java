package com.katcdavi.vaccimate.modules;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UserDataModule
{
    private String nationalId;
    private String username;
    private Date bdate;

    public UserDataModule(String nationalId, String username, Date date) {
        this.nationalId = nationalId;
        this.username = username;
        this.bdate = date;
    }

    public String getNationalId() {
        return this.nationalId;
    }

    public String getUsername() {
        return this.username;
    }

    public Date getBdate() {
        return this.bdate;
    }

    public String getBdateStr() {
        return new SimpleDateFormat("dd.MM.yyyy").format(this.bdate);
    }

    public String compactString() {
        return this.username + " born on " + this.getBdateStr() + " (" + this.nationalId + ")";
    }
}
