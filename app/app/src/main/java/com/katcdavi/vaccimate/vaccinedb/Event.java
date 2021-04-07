package com.katcdavi.vaccimate.vaccinedb;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.katcdavi.vaccimate.userdb.DateConverter;

import java.io.Serializable;
import java.util.Date;

@Entity
@TypeConverters(DateConverter.class)
public class Event implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int categoryId;
    private Date date;
    private String substance;
    private String note;

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCategoryId() {
        return this.categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getSubstance() {
        return this.substance;
    }

    public void setSubstance(String substance) {
        this.substance = substance;
    }

    public String getNote() {
        return this.note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
