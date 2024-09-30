package org.lasers;

public class Grilla {
    private Celda[][] celdas;
    private int filas;
    private int columnas;

    public Grilla(int filas, int columnas) {
        this.filas = filas;
        this.columnas = columnas;
        this.celdas = new Celda[filas][columnas];
        inicializarCeldas();
    }

    private void inicializarCeldas() {          //inicializa una grilla con celdas sin piso ni bloque
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                celdas[i][j] = new Celda();
            }
        }
    }

    public void colocarBloque(int fila, int columna, Bloque bloque) {
        if (esPosicionValida(fila, columna) && celdas[fila][columna].puedeColocarBloque()) {        //si esta dentro de la grilla y es una celda con piso y vacia
            celdas[fila][columna].colocarBloqueEnCelda(bloque);
        }
    }

    public void colocarPiso(int fila, int columna) {
        if (esPosicionValida(fila, columna)) {
            celdas[fila][columna].asignarPiso();
        }
    }

    private boolean esPosicionValida(int fila, int columna) {
        return fila >= 0 && fila < filas && columna >= 0 && columna < columnas;
    }

    public Celda[][] getCeldas() {
        return celdas;
    }
}

