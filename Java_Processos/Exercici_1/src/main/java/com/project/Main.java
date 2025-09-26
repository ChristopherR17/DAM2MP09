package com.project;

import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) {
        ConcurrentHashMap<String, Double> bankData = new ConcurrentHashMap<>();
        ExecutorService executor = Executors.newFixedThreadPool(3);
        CountDownLatch latch = new CountDownLatch(1);

        // Tasca 1: Iniciar
        Runnable initTask = () -> {
            bankData.put("saldo", 1000.0);
            System.out.println("Dades inicials introduïdes: saldo = " + bankData.get("saldo"));
            latch.countDown();
        };

        // Tasca 2: Modificar 
        Runnable modifyTask = () -> {
            try { latch.await(); } catch (InterruptedException e) {}
            bankData.computeIfPresent("saldo", (k, v) -> v * 1.05 - 10); 
            System.out.println("Dades modificades: saldo actualitzat");
        };

        // Tasca 3: Llegir 
        Callable<String> resultTask = () -> {
            try { latch.await(); } catch (InterruptedException e) {}
            Double saldo = bankData.get("saldo");
            return "Resultat final per al client: saldo = " + saldo;
        };

        executor.execute(initTask);
        executor.execute(modifyTask);
        Future<String> future = executor.submit(resultTask);

        try {
            String result = future.get(); 
            System.out.println(result);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        executor.shutdown();
    }
}