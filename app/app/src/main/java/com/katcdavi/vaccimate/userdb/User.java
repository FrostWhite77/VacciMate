package com.katcdavi.vaccimate.userdb;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.katcdavi.vaccimate.modules.Gender;
import com.katcdavi.vaccimate.vaccinedb.GenderConverter;

import java.io.Serializable;
import java.util.Date;

@Entity
@TypeConverters({DateConverter.class, GenderConverter.class})
public class User implements Serializable
{
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String nationalId;
    private String username;
    private Date birthDate;
    private String secret;
    private Gender gender;
    private String countryId;

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNationalId() {
        return this.nationalId;
    }

    public void setNationalId(String nationalId) {
        this.nationalId = nationalId;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getBirthDate() {
        return this.birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getSecret() {
        return this.secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public Gender getGender() {
        return this.gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getCountryId() {
        return this.countryId;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }
}
