package com.katcdavi.vaccimate.vaccinedb;

import androidx.room.TypeConverter;

import com.katcdavi.vaccimate.modules.Gender;


public class GenderConverter {
    @TypeConverter
    public static Gender toGender(String genderStr) {
        return genderStr == null ? null : Gender.fromString(genderStr);
    }

    @TypeConverter
    public static String fromGender(Gender gender) {
        return gender == null ? null : gender.toString();
    }
}
