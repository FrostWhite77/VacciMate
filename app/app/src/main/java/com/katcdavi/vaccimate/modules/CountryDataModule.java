package com.katcdavi.vaccimate.modules;

import android.content.Context;

import com.katcdavi.vaccimate.MainActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CountryDataModule {
    // Singleton
    private static CountryDataModule instance = null;
    public static CountryDataModule getInstance() {
        if (CountryDataModule.instance == null) {
            CountryDataModule.instance = new CountryDataModule();
        }

        return CountryDataModule.instance;
    }
    // Singleton

    private Map<String, Country> countries;

    private CountryDataModule() {
        this.load();
    }

    private void load() {
        try {
            Context context = MainActivity.getContext();
            String fileContent = IOModule.readFileToString(context.getAssets().open("supported_countries.json"));
            JSONObject json = new JSONObject(fileContent);
            JSONArray jsonCountries = json.getJSONArray("countries");

            this.countries = new HashMap<>();
            for (int i = 0; i < jsonCountries.length(); ++i) {
                Country country = Country.fromJson(jsonCountries.getJSONObject(i));
                this.countries.put(country.getId(), country);
            }
        } catch (Exception e) {
            this.countries = new HashMap<>();
        }
    }

    public List<Country> getCountries() {
        return new ArrayList<>(this.countries.values());
    }

    public Country getCountryById(String id) {
        if (this.countries.containsKey(id)) {
            return this.countries.get(id);
        }
        return null;
    }
}
