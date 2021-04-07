package com.katcdavi.vaccimate.userdb;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.katcdavi.vaccimate.vaccinedb.EventDao;

@Database(entities = {User.class}, version = 3, exportSchema = false)
public abstract class UserDatabase extends RoomDatabase {
    public abstract UserDao userAccess();
}
