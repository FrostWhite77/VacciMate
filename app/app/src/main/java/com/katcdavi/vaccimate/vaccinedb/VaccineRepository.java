package com.katcdavi.vaccimate.vaccinedb;

import android.content.Context;
import android.os.AsyncTask;

import androidx.room.Room;


import com.katcdavi.vaccimate.MainActivity;

import java.util.Date;
import java.util.List;

public class VaccineRepository {
    private static VaccineRepository instance;

    public static VaccineRepository getInstance() {
        if (VaccineRepository.instance == null) {
            VaccineRepository.instance = new VaccineRepository();
        }
        return VaccineRepository.instance;
    }


    private String DB_NAME = "vaccine_db";

    private VaccineDatabase vaccineDatabase;

    private VaccineRepository() {
        vaccineDatabase = Room.databaseBuilder(MainActivity.getContext(), VaccineDatabase.class, DB_NAME).allowMainThreadQueries().fallbackToDestructiveMigration().build();
    }

    public void insertEvent(int associatedProgramId, boolean isAssociated, int categoryId, Date date, String substance, String note) {
        Event event = new Event();
        event.setAssociatedProgramId(associatedProgramId);
        event.setIsAssociated(isAssociated);
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
