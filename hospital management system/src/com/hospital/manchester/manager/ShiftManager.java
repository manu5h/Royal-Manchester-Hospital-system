package com.hospital.manchester.manager;

import com.hospital.manchester.consumer.Consultant;
import com.hospital.manchester.enums.Speciality;
import com.hospital.manchester.queue.PatientQueue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ShiftManager {
    private final List<Consultant> activeConsultants = new ArrayList<>();
    private final List<Thread> activeThreads = new ArrayList<>();
    private final Map<Speciality, PatientQueue> queueMap;

    public ShiftManager(Map<Speciality, PatientQueue> queueMap) {
        this.queueMap = queueMap;
    }

    public void startShift(String shiftType, int shiftNumber) {
        System.out.println("================================================");
        System.out.println("\n--- STARTING " + shiftType + " SHIFT #" + shiftNumber + " ---");

        for (Speciality spec : Speciality.values()) {
            //Get the right queue for this speciality
            PatientQueue targetQueue = queueMap.get(spec);

            String drName = "Dr." + spec.name() + "-" + shiftNumber;

            Consultant consultant = new Consultant(drName, spec, shiftType, targetQueue);

            activeConsultants.add(consultant);
            Thread t = new Thread(consultant);
            activeThreads.add(t);
            t.start();
        }
    }

    public void endShift() {
        System.out.println("\n--- ENDING SHIFT: Handing over patients... ---");

        //Set the running flag to false for all doctors
        for (Consultant c : activeConsultants) {
            c.stopConsultant();
        }

        //Interrupt threads to wake them up if they are waiting
        for (Thread t : activeThreads) {
            t.interrupt();
        }

        //Wait for them to safely finish current treatment
        for (Thread t : activeThreads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        //Clear lists for the next shift
        activeConsultants.clear();
        activeThreads.clear();
    }
}