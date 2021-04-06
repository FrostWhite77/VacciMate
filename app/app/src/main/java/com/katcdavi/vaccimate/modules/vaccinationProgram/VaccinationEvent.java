package com.katcdavi.vaccimate.modules.vaccinationProgram;

import com.katcdavi.vaccimate.modules.Gender;

public class VaccinationEvent {
    private int id;
    private int recommendedAge;
    private Gender recommendedGender;
    private DiseaseCategory category;

    public VaccinationEvent(int id, int recommendedAge, Gender recommendedGender, DiseaseCategory category) {
        this.id = id;
        this.recommendedAge = recommendedAge;
        this.recommendedGender = recommendedGender;
        this.category = category;
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
}
