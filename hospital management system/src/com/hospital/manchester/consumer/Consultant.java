package com.hospital.manchester.consumer;

import com.hospital.manchester.enums.Speciality;
import com.hospital.manchester.model.Patient;
import com.hospital.manchester.queue.PatientQueue;

import java.util.Random;

public class Consultant implements Runnable {
    private final String name;
    private final Speciality speciality;
    private final String shift;
    private final PatientQueue patientQueue;
    private volatile boolean running;
    private int patientsServed;
    private final Random random;

    public Consultant(String name, Speciality speciality, String shift, PatientQueue patientQueue) {
        this.name = name;
        this.speciality = speciality;
        this.shift = shift;
        this.patientQueue = patientQueue;
        this.running = true;
        this.patientsServed = 0;
        this.random = new Random();
    }

    @Override
    public void run() {
        System.out.println("[CONSULTANT] " + name + " (" + speciality + ") started " + shift + " shift.");
        while (running) {
            try {
                // Get patient from queue (blocks if empty)
                Patient patient = patientQueue.getPatient();

                System.out.println("[TREATING] " + name + " is treating " + patient);

                // Simulate treatment time (1000ms to 3000ms)
                int treatmentTime = 1000 + random.nextInt(2000);
                Thread.sleep(treatmentTime);

                patientsServed++;
                System.out.println("[DONE] " + name + " finished treating " + patient + ". Served: " + patientsServed);

            } catch (InterruptedException e) {
                System.out.println("[CONSULTANT] " + name + " shift ended. Patients served: " + patientsServed);
                break;
            }
        }
    }

    public void stopConsultant() {
        this.running = false;
    }


}
