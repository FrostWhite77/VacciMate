package com.katcdavi.vaccimate.modules.vaccinationProgram;

import com.katcdavi.vaccimate.vaccinedb.Event;
import com.katcdavi.vaccimate.vaccinedb.VaccineRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    public List<VaccinationEvent> getEvents() {
        return new ArrayList<>(this.events.values());
    }

    public List<VaccinationEvent> getUnassociatedEvents() {
        List<VaccinationEvent> events = this.getEvents();
        VaccineRepository vr = VaccineRepository.getInstance();

        for (Event e : vr.getEvents()) {
            if (e.getIsAssociated()) {
                for (VaccinationEvent ve : events) {
                    if (ve.getId() == e.getAssociatedProgramId()) {
                        events.remove(ve);
                        break;
                    }
                }
            }
        }

        return events;
    }

    public List<DiseaseCategory> getCategories() {
        return new ArrayList<>(this.categories.values());
    }
}
