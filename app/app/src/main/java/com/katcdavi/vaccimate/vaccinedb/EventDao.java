package com.katcdavi.vaccimate.vaccinedb;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface EventDao {
    @Insert
    Long insertEvent(Event event);

    @Query("SELECT * FROM Event")
    List<Event> fetchAllEvents();

    @Query("SELECT * FROM Event WHERE id = :id")
    Event getEventById(int id);

    @Update
    void updateEvent(Event event);

    @Delete
    void deleteEvent(Event event);
}
