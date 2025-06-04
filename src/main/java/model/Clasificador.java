package model;

public class Clasificador {
    public static int clasificar(int valor) {
        if (valor < 99) return 0;           // Normal
        else if (valor <= 125) return 1;    // Prediabetes
        else return 2;                      // Diabetes
    }
}
