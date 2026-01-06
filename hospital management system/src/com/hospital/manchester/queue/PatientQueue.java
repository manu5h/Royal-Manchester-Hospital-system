package com.hospital.manchester.queue;

import com.hospital.manchester.enums.Speciality;
import com.hospital.manchester.model.Patient;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class PatientQueue {
    private final Speciality speciality;
    private final BlockingQueue<Patient> queue;

    public PatientQueue(Speciality speciality) {
        this.speciality = speciality;
        this.queue = new LinkedBlockingQueue<Patient>();
    }

    public void addPatient(Patient patient) throws InterruptedException {
        queue.put(patient);
        System.out.println("[QUEUE] " + patient + " added to " + speciality + " queue. Queue size: " + queue.size());
    }

    public Patient getPatient() throws InterruptedException {
        return queue.take();
    }

    public int getSize() {
        return queue.size();
    }
}
