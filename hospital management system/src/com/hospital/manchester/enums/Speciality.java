package com.hospital.manchester.enums;

import java.util.Random;

public enum Speciality {
    CARDIOLOGIST,
    SURGEON,
    PAEDIATRICIAN;

    private static Random random = new Random();

    public static Speciality getRandomSpeciality() {
        Speciality[] values = Speciality.values();
        return values[random.nextInt(values.length)];
    }
}