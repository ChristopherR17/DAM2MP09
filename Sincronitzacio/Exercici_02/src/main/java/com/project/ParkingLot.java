package com.project;

import java.util.concurrent.Semaphore;

public class ParkingLot {
    private final Semaphore semaphore;

    public ParkingLot(int capacitat) {
        this.semaphore = new Semaphore(capacitat);
    }

    public void entrarCotxe(int idCotxe) throws InterruptedException {
        if (!semaphore.tryAcquire()) {
            System.out.printf("Cotxe %d espera: aparcament ple.%n", idCotxe);
            semaphore.acquire();
        }
        System.out.printf("Cotxe %d ha ENTRAT a l'aparcament.%n", idCotxe);
    }

    public void sortirCotxe(int idCotxe) {
        System.out.printf("Cotxe %d ha SORTIT de l'aparcament.%n", idCotxe);
        semaphore.release();
    }
}