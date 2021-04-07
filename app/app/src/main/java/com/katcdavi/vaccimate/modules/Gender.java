package com.katcdavi.vaccimate.modules;

import java.util.ArrayList;
import java.util.List;

public class Gender {
    private GenderEnum gender;

    private Gender(GenderEnum gender) {
        this.gender = gender;
    }

    public GenderEnum getGender() {
        return this.gender;
    }

    @Override
    public String toString() {
        return this.gender.label;
    }

    public static Gender all() { return new Gender(GenderEnum.ALL); }

    public static Gender male() {
        return new Gender(GenderEnum.MALE);
    }

    public static Gender female() {
        return new Gender(GenderEnum.FEMALE);
    }

    public static Gender other() {
        return new Gender(GenderEnum.OTHER);
    }

    public static Gender fromString(String val) {
        val = val.toUpperCase();

        if (val.equals(GenderEnum.MALE.label)) {
            return male();
        } else if (val.equals(GenderEnum.FEMALE.label)) {
            return female();
        } else if (val.equals(GenderEnum.ALL.label)) {
            return all();
        } else {
            return other();
        }
    }
}
