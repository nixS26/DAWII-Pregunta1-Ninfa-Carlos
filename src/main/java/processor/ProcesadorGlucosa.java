package processor;

import model.Clasificador;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.ResultadosGlobales;

import java.util.Arrays;
import java.util.Random;


public class ProcesadorGlucosa implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(ProcesadorGlucosa.class);

    @Override
    public void run() {
        Random rand = new Random();
        int[] niveles = new int[10];
        int[] clasificados = new int[10];

        for (int i = 0; i < 10; i++) {
            niveles[i] = 70 + rand.nextInt(121); // entre 70 y 190
            clasificados[i] = Clasificador.clasificar(niveles[i]);
            ResultadosGlobales.agregarResultado(clasificados[i]);
        }

        String nombreHilo = Thread.currentThread().getName();
        logger.info("[" + nombreHilo + "] Niveles:       " + Arrays.toString(niveles));
        logger.info("[" + nombreHilo + "] Clasificación: " + Arrays.toString(clasificados));
    }
}
