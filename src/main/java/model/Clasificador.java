package model;

public class Clasificador {
    public static int clasificar(int valor) {
        //-------SEGUIMOS LAS REGLAS DE LA TABLA-----
        if (valor < 99) return 0;
        else if (valor <= 125) return 1;
        else return 2;
    }
}
