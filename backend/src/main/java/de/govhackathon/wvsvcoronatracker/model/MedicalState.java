package de.govhackathon.wvsvcoronatracker.model;

import lombok.Getter;

@Getter
public enum MedicalState {
    UNKNOWN("medical_state.unknown","unknown"),
    INFECTED("medical_state.infected","infected"),
    TREATMENT("medical_state.treatment","treatment"),
    CURED("medical_state.cured","cured"),
    ;

    private String i18n;
    private String name;

    MedicalState(String i18n, String name) {
        this.i18n = i18n;
        this.name = name;
    }

    public static MedicalState getByI18nKey(String i18n) {
        for (MedicalState item : MedicalState.values()) {
            if (item.getI18n().equals(i18n)) {
                return item;
            }
        }
        throw new RuntimeException("Medical State not found: " + i18n);
    }

    public String getI18n() {
        return i18n;
    }

    public void setI18n(String i18n) {
        this.i18n = i18n;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
