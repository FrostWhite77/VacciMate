package com.katcdavi.vaccimate.userdb;

import android.content.Context;
import android.os.AsyncTask;

import androidx.room.Room;

import com.katcdavi.vaccimate.MainActivity;
import com.katcdavi.vaccimate.modules.Country;
import com.katcdavi.vaccimate.modules.Gender;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class UserRepository {
    private static UserRepository instance;
    public static UserRepository getInstance() {
        if (UserRepository.instance == null) {
            UserRepository.instance = new UserRepository();
        }

        return UserRepository.instance;
    }

    private String DB_NAME = "user_db";
    private UserDatabase userDatabase;

    private UserRepository() {
        userDatabase = Room.databaseBuilder(MainActivity.getContext(), UserDatabase.class, DB_NAME).allowMainThreadQueries().fallbackToDestructiveMigration().build();
    }

    public void insertUser(String nationalId, String username, Date birthDate, String secret, Country country, Gender gender) throws ExecutionException, InterruptedException {
        User user = new User();
        user.setNationalId(nationalId);
        user.setUsername(username);
        user.setBirthDate(birthDate);
        user.setSecret(secret);
        user.setCountry(country);
        user.setGender(gender);

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                userDatabase.userAccess().insertUser(user);
                return null;
            }
        }.execute();
    }

    public void insertUser(User user) {
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
