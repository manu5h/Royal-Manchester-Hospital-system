package com.hospital.manchester.producer;

import com.hospital.manchester.enums.Speciality;
import com.hospital.manchester.model.Patient;
import com.hospital.manchester.queue.PatientQueue;

import java.util.Map;
import java.util.Random;

public class PatientArrival implements Runnable {
    private final Map<Speciality, PatientQueue> queueMap;
    private volatile boolean running;
    private final Random random;

    public PatientArrival(Map<Speciality, PatientQueue> queueMap) {
        this.queueMap = queueMap;
        this.running = true;
        this.random = new Random();
    }

    @Override
    public void run() {
        int id = 1;
        while (running) {
            try {
                Speciality speciality = Speciality.getRandomSpeciality();
                Patient patient = new Patient(id++, speciality);

                // Use the map to find the correct queue instantly
                PatientQueue targetQueue = queueMap.get(speciality);
                if (targetQueue != null) {
                    targetQueue.addPatient(patient);
                }

                // Random delay between arrivals
                Thread.sleep(500 + random.nextInt(1000));

            } catch (InterruptedException e) {
                running = false; // Stop the loop if interrupted
            }
        }
    }

}
