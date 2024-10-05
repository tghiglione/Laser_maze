package org.lasers;

public class Objetivo {
    private final Posicion posicion;
    private boolean esAlcanzado;

    public Objetivo(Posicion posicion){
        this.posicion = posicion;
        this.esAlcanzado = false;
    }

    public void verificarImpacto(Laser laser) {
        if (!esAlcanzado && laser.getPosicionActual().equals(posicion)) {
            esAlcanzado = true;
        }
    }

    public boolean estaAlcanzado(){
        return esAlcanzado;
    }

    public Posicion getPosicionObjetivo() {
        return posicion;
    }
}
