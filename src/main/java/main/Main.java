package main;

import processor.ProcesadorGlucosa;
import util.ResultadosGlobales;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        Thread h1 = new Thread(new ProcesadorGlucosa(), "Hilo-1");
        Thread h2 = new Thread(new ProcesadorGlucosa(), "Hilo-2");
        Thread h3 = new Thread(new ProcesadorGlucosa(), "Hilo-3");

        h1.start();
        h2.start();
        h3.start();

        h1.join();
        h2.join();
        h3.join();

        ResultadosGlobales.mostrarEstadisticas();
    }
}
