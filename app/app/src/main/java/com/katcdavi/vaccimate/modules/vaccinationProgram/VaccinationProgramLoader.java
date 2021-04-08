package com.katcdavi.vaccimate.modules.vaccinationProgram;

import com.katcdavi.vaccimate.modules.Gender;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class VaccinationProgramLoader {
    public static VaccinationProgram loadFromFile(InputStream stream) {
        VaccinationProgram program = new VaccinationProgram();
        String json = VaccinationProgramLoader.readFileToString(stream);

        try {
            JSONObject obj = new JSONObject(json);

            JSONArray jsonCategories = obj.getJSONArray("categories");
            for (int i = 0; i < jsonCategories.length(); ++i) {
                int id = Integer.parseInt(jsonCategories.getJSONObject(i).getString("id"));
                String name = jsonCategories.getJSONObject(i).getString("name");

                program.addCategory(new DiseaseCategory(id, name));
            }

            JSONArray jsonEvents = obj.getJSONArray("events");
            for (int i = 0; i < jsonEvents.length(); ++i) {
                int id = Integer.parseInt(jsonEvents.getJSONObject(i).getString("id"));
                int reccAge = Integer.parseInt(jsonEvents.getJSONObject(i).getString("reccAge"));
                String reccGender = jsonEvents.getJSONObject(i).getString("reccGender");
                int categoryId = Integer.parseInt(jsonEvents.getJSONObject(i).getString("category"));
                String note = jsonEvents.getJSONObject(i).getString("note");

                program.addEvent(new VaccinationEvent(id, reccAge, Gender.fromString(reccGender), program.getCategoryById(categoryId), note));
            }

            return program;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String readFileToString(InputStream stream) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            StringBuilder builder = new StringBuilder();
            String line = null;

            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }

            return builder.toString();
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}
