package com.katcdavi.vaccimate.vaccinedb;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Event.class}, version = 1, exportSchema = false)
public abstract class VaccineDatabase extends RoomDatabase {
    public abstract EventDao eventAccess();
}
