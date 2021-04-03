package com.katcdavi.vaccimate.user;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.room.Room;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class UserRepository {

    private String DB_NAME = "user_db";

    private UserDatabase userDatabase;

    public UserRepository(Context context) {
        userDatabase = Room.databaseBuilder(context, UserDatabase.class, DB_NAME).allowMainThreadQueries().fallbackToDestructiveMigration().build();
    }

    public void insertUser(String nationalId, String username, Date birthDate) throws ExecutionException, InterruptedException {
        User user = new User();
        user.setNationalId(nationalId);
        user.setUsername(username);
        user.setBirthDate(birthDate);

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                userDatabase.userAccess().insertUser(user);
                return null;
            }
        }.execute();
    }

    public void updateUser(final User user) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                userDatabase.userAccess().updateUser(user);
                return null;
            }
        }.execute();
    }

    public void deleteUser(final int id) {
        final User user = this.getUser(id);
        if(user != null) {
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... voids) {
                    userDatabase.userAccess().deleteUser(user);
                    return null;
                }
            }.execute();
        }
    }

    public void deleteUser(final User user) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                userDatabase.userAccess().deleteUser(user);
                return null;
            }
        }.execute();
    }

    public User getUser(int id) {
        return userDatabase.userAccess().getUserById(id);
    }

    public List<User> getUsers() {
        return userDatabase.userAccess().fetchAllUsers();
    }
}
