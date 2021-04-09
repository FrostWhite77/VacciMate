package com.katcdavi.vaccimate.modules;


public class DataStore {
    private static DataStore instance;

    public static DataStore getInstance() {
        if (DataStore.instance == null) {
            DataStore.instance = new DataStore();
        }

        return DataStore.instance;
    }

    private UserStore userStore;

    private DataStore() {
        this.userStore = new UserStore();
    }

    public UserStore getUserStore() {
        return this.userStore;
    }
}
