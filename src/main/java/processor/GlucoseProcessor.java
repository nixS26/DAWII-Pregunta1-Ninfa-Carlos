package processor;

import model.GlucoseClassification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.concurrent.Callable;

public class GlucoseProcessor implements Callable<int[]> {

    private static final Logger logger = LoggerFactory.getLogger(GlucoseProcessor.class);

    private final int[] glucoseLevels;
    private final String threadName;

    public GlucoseProcessor(int[] glucoseLevels, String threadName) {
        this.glucoseLevels = glucoseLevels.clone(); // Copia defensiva
        this.threadName = threadName;
    }

    @Override
    public int[] call() throws Exception {
        logger.info("Iniciando procesamiento en hilo: {}", threadName);
        logger.info("Datos a procesar: {}", Arrays.toString(glucoseLevels));

        int[] classifications = new int[glucoseLevels.length];

        for (int i = 0; i < glucoseLevels.length; i++) {
            GlucoseClassification classification = GlucoseClassification.classify(glucoseLevels[i]);
            classifications[i] = classification.getValue();
            Thread.sleep(10);
        }

        logger.info("Hilo {} completado. Resultados: {}", threadName, Arrays.toString(classifications));
        return classifications;
    }

    public int[] getGlucoseLevels() {
        return glucoseLevels.clone();
    }

    public String getThreadName() {
        return threadName;
    }
}