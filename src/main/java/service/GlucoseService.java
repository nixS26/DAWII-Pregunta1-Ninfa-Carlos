package service;

import model.GlucoseClassification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import processor.GlucoseProcessor;

import java.util.*;
import java.util.concurrent.*;

public class GlucoseService {

    private static final Logger logger = LoggerFactory.getLogger(GlucoseService.class);
    private static final int ARRAY_SIZE = 10;
    private static final int THREAD_COUNT = 3;
    private static final int MIN_GLUCOSE = 60;
    private static final int MAX_GLUCOSE = 200;

    private final ExecutorService executorService;
    private final Random random;

    public GlucoseService() {
        this.executorService = Executors.newFixedThreadPool(THREAD_COUNT);
        this.random = new Random();
    }

    public void processGlucoseClassification() {
        try {
            logger.info("=== Iniciando Sistema de Clasificación de Glucosa ===");

            List<int[]> glucoseArrays = generateRandomGlucoseArrays();
            displayGlucoseArrays(glucoseArrays);

            List<Future<int[]>> futures = submitProcessingTasks(glucoseArrays);

            List<int[]> results = collectResults(futures);
            displayResults(results);

            calculateAndDisplayStatistics(results);

        } catch (Exception e) {
            logger.error("Error durante el procesamiento: ", e);
        } finally {
            shutdown();
        }
    }

    private List<int[]> generateRandomGlucoseArrays() {
        List<int[]> arrays = new ArrayList<>();

        for (int i = 0; i < THREAD_COUNT; i++) {
            int[] glucoseArray = new int[ARRAY_SIZE];
            for (int j = 0; j < ARRAY_SIZE; j++) {
                glucoseArray[j] = random.nextInt(MAX_GLUCOSE - MIN_GLUCOSE + 1) + MIN_GLUCOSE;
            }
            arrays.add(glucoseArray);
        }

        return arrays;
    }

    private void displayGlucoseArrays(List<int[]> glucoseArrays) {
        logger.info("\n=== Datos de Glucosa Generados ===");
        for (int i = 0; i < glucoseArrays.size(); i++) {
            System.out.printf("Array %d: %s%n", i + 1, Arrays.toString(glucoseArrays.get(i)));
        }
        System.out.println();
    }

    private List<Future<int[]>> submitProcessingTasks(List<int[]> glucoseArrays) {
        List<Future<int[]>> futures = new ArrayList<>();

        for (int i = 0; i < glucoseArrays.size(); i++) {
            String threadName = "Thread-" + (i + 1);
            GlucoseProcessor processor = new GlucoseProcessor(glucoseArrays.get(i), threadName);
            Future<int[]> future = executorService.submit(processor);
            futures.add(future);
        }

        return futures;
    }

    private List<int[]> collectResults(List<Future<int[]>> futures) throws ExecutionException, InterruptedException {
        List<int[]> results = new ArrayList<>();

        logger.info("Esperando a que todos los hilos terminen...");

        for (int i = 0; i < futures.size(); i++) {
            int[] result = futures.get(i).get(); // Espera a que el hilo termine
            results.add(result);
            logger.info("Hilo {} completado", i + 1);
        }

        return results;
    }

    private void displayResults(List<int[]> results) {
        logger.info("\n=== Resultados de Clasificación ===");
        for (int i = 0; i < results.size(); i++) {
            System.out.printf("Resultado %d: %s%n", i + 1, Arrays.toString(results.get(i)));
        }
        System.out.println();
    }

    private void calculateAndDisplayStatistics(List<int[]> results) {
        List<Integer> allResults = new ArrayList<>();
        for (int[] result : results) {
            for (int classification : result) {
                allResults.add(classification);
            }
        }

        System.out.println("Todos los resultados juntos:");
        System.out.println(allResults);
        System.out.println();

        Map<GlucoseClassification, Integer> counts = new EnumMap<>(GlucoseClassification.class);
        for (GlucoseClassification classification : GlucoseClassification.values()) {
            counts.put(classification, 0);
        }

        for (int classification : allResults) {
            for (GlucoseClassification gc : GlucoseClassification.values()) {
                if (gc.getValue() == classification) {
                    counts.put(gc, counts.get(gc) + 1);
                    break;
                }
            }
        }

        int totalSamples = allResults.size();
        System.out.println("=== Clasificación de Resultados ===");

        for (GlucoseClassification classification : GlucoseClassification.values()) {
            int count = counts.get(classification);
            double percentage = (count * 100.0) / totalSamples;
            System.out.printf("%s: %.1f%% (%d/%d)%n",
                    classification.getDescription(),
                    percentage,
                    count,
                    totalSamples);
        }

        logger.info("Procesamiento completado exitosamente");
    }

    private void shutdown() {
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(5, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
        }
        logger.info("ExecutorService cerrado correctamente");
    }
}
