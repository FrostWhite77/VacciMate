package com.katcdavi.vaccimate.vaccinedb;

import android.content.Context;
import android.os.AsyncTask;

import androidx.room.Room;


import java.util.Date;
import java.util.List;

public class VaccineRepository {
    private String DB_NAME = "vaccine_db";

    private VaccineDatabase vaccineDatabase;

    public VaccineRepository(Context context) {
        vaccineDatabase = Room.databaseBuilder(context, VaccineDatabase.class, DB_NAME).allowMainThreadQueries().fallbackToDestructiveMigration().build();
    }

    public void insertEvent(int categoryId, Date date, String substance, String note) {
        Event event = new Event();
        event.setCategoryId(categoryId);
        event.setDate(date);
        event.setSubstance(substance);
        event.setNote(note);

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                vaccineDatabase.eventAccess().insertEvent(event);
                return null;
            }
        }.execute();
    }

    public void updateEvent(final Event event) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                vaccineDatabase.eventAccess().updateEvent(event);
                return null;
            }
        }.execute();
    }

    public void deleteEvent(final int id) {
        final Event event = this.getEvent(id);
        if(event != null) {
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... voids) {
                    vaccineDatabase.eventAccess().deleteEvent(event);
                    return null;
                }
            }.execute();
        }
    }

    public void deleteEvent(final Event event) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                vaccineDatabase.eventAccess().deleteEvent(event);
                return null;
            }
        }.execute();
    }

    public Event getEvent(int id) {
        return vaccineDatabase.eventAccess().getEventById(id);
    }

    public List<Event> getEvents() {
        return vaccineDatabase.eventAccess().fetchAllEvents();
    }
}
