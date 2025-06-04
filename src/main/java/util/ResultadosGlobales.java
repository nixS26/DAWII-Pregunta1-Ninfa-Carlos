package util;

import java.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ResultadosGlobales {
    private static final Logger logger = LoggerFactory.getLogger(ResultadosGlobales.class);
    private static final List<Integer> resultados = Collections.synchronizedList(new ArrayList<>());

    public static void agregarResultado(int valor) {
        resultados.add(valor);
    }

    public static void mostrarEstadisticas() {
        int normal = 0, prediabetes = 0, diabetes = 0;

        for (int r : resultados) {
            switch (r) {
                case 0 -> normal++;
                case 1 -> prediabetes++;
                case 2 -> diabetes++;
            }
        }

        int total = resultados.size();
        if (total == 0) {
            logger.info("No hay resultados para mostrar.");
            return;
        }

        logger.info("\nClasificación de resultados:");
        logger.info(String.format("Normal: %.1f%%", normal * 100.0 / total));
        logger.info(String.format("Prediabetes: %.1f%%", prediabetes * 100.0 / total));
        logger.info(String.format("Diabetes: %.1f%%", diabetes * 100.0 / total));
    }
}