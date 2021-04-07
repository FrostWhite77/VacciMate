package com.katcdavi.vaccimate.modules;

import java.text.SimpleDateFormat;
import java.util.Date;

public class UserDataModule
{
    private String nationalId;
    private String username;
    private Date bdate;
    private boolean loggedIn;
    private String secret;
    private Gender gender;
    private String countryId;

    public UserDataModule(String nationalId, String username, Date date, String secret, Gender gender, String countryId) {
        this.nationalId = nationalId;
        this.username = username;
        this.bdate = date;
        this.loggedIn = false;
        this.secret = secret;
        this.gender = gender;
        this.countryId = countryId;
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

    public boolean isLoggedIn() {
        return this.loggedIn;
    }

    public void logIn() {
        this.loggedIn = true;
    }

    public String getSecret() {
        return this.secret;
    }

    public Gender getGender() {
        return this.gender;
    }

    public String getCountryId() {
        return this.countryId;
    }
}
