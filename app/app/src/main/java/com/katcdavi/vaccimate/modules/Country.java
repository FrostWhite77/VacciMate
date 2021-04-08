package com.katcdavi.vaccimate.modules;


import org.json.JSONException;
import org.json.JSONObject;

public class Country {
    private String id;
    private String name;
    private String vaccinationProgramFile;

    private Country(String id, String name, String vaccinationProgramFile) {
        this.id = id;
        this.name = name;
        this.vaccinationProgramFile = vaccinationProgramFile;
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getFile() {
        return this.vaccinationProgramFile;
    }

    @Override
    public String toString() {
        return this.name;
    }

    public static Country fromJson(JSONObject src) {
        try {
            String id = src.getString("id");
            String name = src.getString("title");
            String file = src.getString("file");

            return new Country(id, name, file);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
