package com.katcdavi.vaccimate.modules.vaccinationProgram;

public class DiseaseCategory {
    private int id;
    private String groupName;

    public DiseaseCategory(int id, String groupName) {
        this.id = id;
        this.groupName = groupName;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.groupName;
    }

    @Override
    public String toString() {
        return this.getName();
    }
}
