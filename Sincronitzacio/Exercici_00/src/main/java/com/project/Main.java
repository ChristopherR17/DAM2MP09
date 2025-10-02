package com.project;

import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        final int NUM_MICROSERVEIS = 3;
        ExecutorService executor = Executors.newFixedThreadPool(NUM_MICROSERVEIS);

        // Array per guardar els resultats parcials
        final String[] resultats = new String[NUM_MICROSERVEIS];

        // Barrera per sincronitzar els microserveis
        CyclicBarrier barrier = new CyclicBarrier(NUM_MICROSERVEIS, () -> {
            // Aquesta tasca s'executa quan tots han arribat a la barrera
            String resultatFinal = String.join(" + ", resultats);
            System.out.println("Resultat final combinat: " + resultatFinal);
        });

        Runnable microservei1 = () -> {
            try {
                System.out.println("Microservei 1 processant dades...");
                Thread.sleep(1000);
                resultats[0] = "Resultat1";
                System.out.println("Microservei 1 completat.");
                barrier.await();
            } catch (Exception e) {
                e.printStackTrace();
            }
        };

        Runnable microservei2 = () -> {
            try {
                System.out.println("Microservei 2 processant dades...");
                Thread.sleep(1200);
                resultats[1] = "Resultat2";
                System.out.println("Microservei 2 completat.");
                barrier.await();
            } catch (Exception e) {
                e.printStackTrace();
            }
        };

        Runnable microservei3 = () -> {
            try {
                System.out.println("Microservei 3 processant dades...");
                Thread.sleep(900);
                resultats[2] = "Resultat3";
                System.out.println("Microservei 3 completat.");
                barrier.await();
            } catch (Exception e) {
                e.printStackTrace();
            }
        };

        executor.submit(microservei1);
        executor.submit(microservei2);
        executor.submit(microservei3);

        executor.shutdown();
        executor.awaitTermination(5, TimeUnit.SECONDS);
    }
}
