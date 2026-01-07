package com.hospital.manchester;

import com.hospital.manchester.enums.Speciality;
import com.hospital.manchester.manager.ShiftManager;
import com.hospital.manchester.producer.PatientArrival;
import com.hospital.manchester.queue.PatientQueue;

import java.util.HashMap;
import java.util.Map;

public class HospitalSimulation {
    public static void main(String[] args) {

        Map<Speciality, PatientQueue> queueMap = new HashMap<>();
        for (Speciality spec : Speciality.values()) {
            queueMap.put(spec, new PatientQueue(spec));
        }

        //Start Patient Arrivals
        PatientArrival arrivalTask = new PatientArrival(queueMap);
        Thread arrivalThread = new Thread(arrivalTask);
        arrivalThread.start();

        ShiftManager shiftManager = new ShiftManager(queueMap);

        try {
            for (int day = 1; day <= 1; day++) {
                System.out.println("\n--- DAY " + day + " AT MANCHESTER HOSPITAL ---");

                // DAY SHIFT (Simulated 12 seconds)
                shiftManager.startShift("Day", day);
                Thread.sleep(12000);
                shiftManager.endShift();

                // NIGHT SHIFT (Simulated 12 seconds)
                shiftManager.startShift("Night", day);
                Thread.sleep(12000);
                shiftManager.endShift();
            }
        } catch (InterruptedException e) {
            System.out.println("Simulation interrupted.");
        }

        System.out.println("\nSimulation complete. Closing Hospital.");
        System.exit(0);
    }
}