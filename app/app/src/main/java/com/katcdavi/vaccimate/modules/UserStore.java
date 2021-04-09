package com.katcdavi.vaccimate.modules;

import com.katcdavi.vaccimate.MainActivity;
import com.katcdavi.vaccimate.userdb.User;
import com.katcdavi.vaccimate.userdb.UserDatabase;
import com.katcdavi.vaccimate.userdb.UserRepository;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class UserStore {
    private User user;
    private boolean loggedIn;

    public UserStore(User user) {
        this.user = user;
        this.loggedIn = false;
    }

    public UserStore() {
        this.user = this.retrieveUser();
    }

    public boolean isAvailable() {
        return this.user != null;
    }

    public void setUser(User user) {
        this.user = user;
        this.loggedIn = false;
    }

    public boolean logIn(String pin) {
        if (!this.isAvailable()) {
            return false;
        }

        String secret = CryptoModule.pinToSecret(this.user.getNationalId(), this.user.getUsername(), this.user.getBirthDate(), pin);
        if (this.user.getSecret().equals(secret)) {
            this.loggedIn = true;
            return true;
        }

        return false;
    }

    public boolean isLoggedIn() {
        return this.loggedIn;
    }

    public void rebuild() {
        this.user = this.retrieveUser();
        this.loggedIn = false;
    }

    private User retrieveUser() {
        UserRepository userRepository = UserRepository.getInstance();
        List<User> users = userRepository.getUsers();
        if (users.size() > 0) {
            return users.get(0);
        }

        return null;
    }

    // wrappers
    public String getNationalId() {
        if (this.isAvailable()) {
            return this.user.getNationalId();
        }
        return null;
    }

    public String getUsername() {
        if (this.isAvailable()) {
            return this.user.getUsername();
        }
        return null;
    }

    public Date getBirthDate() {
        if (this.isAvailable()) {
            return this.user.getBirthDate();
        }
        return null;
    }

    public String getBdateStr() {
        if (this.isAvailable()) {
            return new SimpleDateFormat("dd.MM.yyyy").format(this.getBirthDate());
        }
        return null;
    }

    public Country getCountry() {
        if (this.isAvailable()) {
            return this.user.getCountry();
        }
        return null;
    }

    public Gender getGender() {
        if (this.isAvailable()) {
            return this.user.getGender();
        }
        return null;
    }
}
