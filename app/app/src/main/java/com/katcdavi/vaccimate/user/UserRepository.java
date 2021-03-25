package com.katcdavi.vaccimate.user;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.room.Room;

import java.util.List;

public class UserRepository {

    private String DB_NAME = "user_db";

    private UserDatabase userDatabase;

    public UserRepository(Context context) {
        userDatabase = Room.databaseBuilder(context, UserDatabase.class, DB_NAME).build();
    }

    public void insertUser(String nationalId, String username) {
        User user = new User();
        user.setNationalId(nationalId);
        user.setUsername(username);

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

    public void deleteTask(final int id) {
        final LiveData<User> task = getTask(id);
        if(task != null) {
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... voids) {
                    userDatabase.userAccess().deleteUser(task.getValue());
                    return null;
                }
            }.execute();
        }
    }

    public void deleteTask(final User user) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                userDatabase.userAccess().deleteUser(user);
                return null;
            }
        }.execute();
    }

    public LiveData<User> getTask(int id) {
        return userDatabase.userAccess().getUserById(id);
    }

    public LiveData<List<User>> getTasks() {
        return userDatabase.userAccess().fetchAllUsers();
    }
}
