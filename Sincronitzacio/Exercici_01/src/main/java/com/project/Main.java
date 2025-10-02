package com.project;

import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        final double[] dades = {10, 20, 30, 40, 50, 60, 70, 80, 90, 100};
        final int N = dades.length;

        final double[] suma = new double[1];
        final double[] mitjana = new double[1];
        final double[] desviacio = new double[1];

        CyclicBarrier barrier = new CyclicBarrier(3, () -> {
            System.out.printf("Resultats finals:%n");
            System.out.printf("Suma: %.2f%n", suma[0]);
            System.out.printf("Mitjana: %.2f%n", mitjana[0]);
            System.out.printf("Desviacio estandard: %.2f%n", desviacio[0]);
        });

        ExecutorService executor = Executors.newFixedThreadPool(3);

        Runnable calculaSuma = () -> {
            double total = 0;
            for (double d : dades) total += d;
            suma[0] = total;
            try {
                barrier.await();
            } catch (Exception e) {
                e.printStackTrace();
            }
        };

        Runnable calculaMitjana = () -> {
            double total = 0;
            for (double d : dades) total += d;
            mitjana[0] = total / N;
            try {
                barrier.await();
            } catch (Exception e) {
                e.printStackTrace();
            }
        };

        Runnable calculaDesviacio = () -> {
            double total = 0;
            for (double d : dades) total += d;
            double mean = total / N;
            double sumQuadrats = 0;
            for (double d : dades) sumQuadrats += Math.pow(d - mean, 2);
            desviacio[0] = Math.sqrt(sumQuadrats / N);
            try {
                barrier.await();
            } catch (Exception e) {
                e.printStackTrace();
            }
        };

        executor.submit(calculaSuma);
        executor.submit(calculaMitjana);
        executor.submit(calculaDesviacio);

        executor.shutdown();
        executor.awaitTermination(5, TimeUnit.SECONDS);
    }
}
