package com.project;

import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        final int CAPACITAT = 3; // Capacitat de l'aparcament
        final int NUM_COTXES = 8;

        ParkingLot parkingLot = new ParkingLot(CAPACITAT);
        ExecutorService executor = Executors.newFixedThreadPool(NUM_COTXES);

        for (int i = 1; i <= NUM_COTXES; i++) {
            final int idCotxe = i;
            executor.submit(() -> {
                try {
                    parkingLot.entrarCotxe(idCotxe);
                    // Simula el temps que el cotxe està aparcat
                    Thread.sleep(1000 + (int)(Math.random() * 2000));
                    parkingLot.sortirCotxe(idCotxe);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }

        executor.shutdown();
        executor.awaitTermination(20, TimeUnit.SECONDS);
    }
}
