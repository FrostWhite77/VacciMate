package com.katcdavi.vaccimate.modules;


import com.katcdavi.vaccimate.modules.vaccinationProgram.VaccinationProgram;

public class DataStore {
    private static DataStore instance;

    public static DataStore getInstance() {
        if (DataStore.instance == null) {
            DataStore.instance = new DataStore();
        }

        return DataStore.instance;
    }

    private UserStore userStore;
    private VaccinationProgram program;

    private DataStore() {
        this.userStore = new UserStore();
    }

    public UserStore getUserStore() {
        return this.userStore;
    }

    public VaccinationProgram getProgram() {
        return this.program;
    }

    public void setProgram(VaccinationProgram program) {
        this.program = program;
    }
}
