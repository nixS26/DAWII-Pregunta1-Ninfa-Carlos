package main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.GlucoseService;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);
    public static void main(String[] args) {
        logger.info("Iniciando aplicación de clasificación de glucosa");

        try {
            GlucoseService glucoseService = new GlucoseService();
            glucoseService.processGlucoseClassification();

        } catch (Exception e) {
            logger.error("Error fatal en la aplicación: ", e);
            System.exit(1);
        }

        logger.info("Aplicación finalizada correctamente");
    }
}
