package com.hospital.manchester;

import com.hospital.manchester.enums.Speciality;
import com.hospital.manchester.manager.ShiftManager;
import com.hospital.manchester.producer.PatientArrival;
import com.hospital.manchester.queue.PatientQueue;

import java.util.HashMap;
import java.util.Map;

public class HospitalSimulation {
    public static void main(String[] args) {

        // 2. Map Enums to Queues for the Manager and Producer
        Map<Speciality, PatientQueue> queueMap = new HashMap<>();
        // 2. Loop through all specialities and create a queue for each
        for (Speciality spec : Speciality.values()) {
            queueMap.put(spec, new PatientQueue(spec));
        }

        // 3. Start Patient Arrivals (The Producer)
        PatientArrival arrivalTask = new PatientArrival(queueMap);
        Thread arrivalThread = new Thread(arrivalTask);
        arrivalThread.start();

        // 4. Initialize Shift Manager
        ShiftManager shiftManager = new ShiftManager(queueMap);

        // 5. Run Simulation for a set number of days (e.g., 2 days)
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