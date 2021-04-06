package com.katcdavi.vaccimate.modules.vaccinationProgram;

import java.util.HashMap;
import java.util.Map;

public class VaccinationProgram {
    private Map<Integer, DiseaseCategory> categories;
    private Map<Integer, VaccinationEvent> events;

    public VaccinationProgram() {
        this.categories = new HashMap<>();
        this.events = new HashMap<>();
    }

    public void addCategory(DiseaseCategory category) {
        this.categories.put(category.getId(), category);
    }

    public void addEvent(VaccinationEvent event) {
        this.events.put(event.getId(), event);
    }

    public DiseaseCategory getCategoryById(int categoryId) {
        if (this.categories.containsKey(categoryId)) {
            return this.categories.get(categoryId);
        }
        return null;
    }

    public int getCategoriesSize() {
        return this.categories.size();
    }

    public int getEventsSize() {
        return this.events.size();
    }
}
