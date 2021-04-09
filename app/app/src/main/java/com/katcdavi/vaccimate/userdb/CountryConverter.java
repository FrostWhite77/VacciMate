package com.katcdavi.vaccimate.userdb;

import androidx.room.TypeConverter;

import com.katcdavi.vaccimate.modules.Country;
import com.katcdavi.vaccimate.modules.CountryDataModule;

public class CountryConverter {
    @TypeConverter
    public static Country toCountry(String countryId) {
        return countryId == null ? null : CountryDataModule.getInstance().getCountryById(countryId);
    }

    @TypeConverter
    public static String fromCountry(Country country) {
        return country == null ? null : country.getId();
    }
}
