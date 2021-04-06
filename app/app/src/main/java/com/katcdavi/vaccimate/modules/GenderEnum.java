package com.katcdavi.vaccimate.modules;

public enum GenderEnum {
    ALL("ALL"),
    MALE("MALE"),
    FEMALE("FEMALE"),
    OTHER("OTHER");

    public final String label;

    private GenderEnum(String label) {
        this.label = label;
    }
}
