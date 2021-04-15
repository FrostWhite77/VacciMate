package com.katcdavi.vaccimate.modules.vaccinationProgram;

import com.katcdavi.vaccimate.modules.Gender;

public class VaccinationEvent {
    private int id;
    private int recommendedAge;
    private Gender recommendedGender;
    private DiseaseCategory category;
    private String note;

    public VaccinationEvent(int id, int recommendedAge, Gender recommendedGender, DiseaseCategory category, String note) {
        this.id = id;
        this.recommendedAge = recommendedAge;
        this.recommendedGender = recommendedGender;
        this.category = category;
        this.note = note;
    }

    public int getId() {
        return this.id;
    }

    public int getRecommendedAge() {
        return this.recommendedAge;
    }

    public Gender getRecommendedGender() {
        return this.recommendedGender;
    }

    public DiseaseCategory getCategory() {
        return this.category;
    }

    public String getNote() {
        return this.note;
    }

    @Override
    public String toString() {
        if (this.id == -1) {
            return "-";
        }
        return "[" + this.category.getName() + "] " + this.note;
    }
}
