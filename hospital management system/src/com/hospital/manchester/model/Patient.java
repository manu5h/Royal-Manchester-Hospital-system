package com.hospital.manchester.model;

import com.hospital.manchester.enums.Speciality;

public class Patient {
    private final int patientId;
    private final Speciality speciality;

    public Patient (int patientId, Speciality speciality){
        this.patientId = patientId;
        this.speciality = speciality;
    }

    public String toString() {
        return "Patient-" + patientId + " (" + speciality + ")";
    }
}
